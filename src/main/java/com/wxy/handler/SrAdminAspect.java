package com.wxy.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.ImmutableMap;
import com.wxy.config.response.ResponseResult;
import com.wxy.dao.SrAdminDao;
import com.wxy.dao.SrAdminLogDao;
import com.wxy.exception.CustomException;
import com.wxy.model.SrAdmin;
import com.wxy.model.SrAdminLog;
import com.wxy.model.response.AdminCode;
import com.wxy.utils.IpUtils;
import com.wxy.utils.JwtUtil;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : wxy
 * @version : V1.0
 * @className : AdminAspect
 * @packageName : com.yinghuo.management.admin.config
 * @description : 管理员操作切面类
 * @date : 2020-10-06 10:40
 **/
@Aspect
@Component
@Slf4j
public class SrAdminAspect {
    @Resource
    private SrAdminDao srAdminDao;
    @Resource
    private SrAdminLogDao srAdminLogDao;

    /**
     * 设置切入点
     */
    @Pointcut("execution(* com.wxy.service.*.*(..))")
    public void webLog() {
    }

    @AfterReturning(returning = "rvt", value = "webLog()")
    public void after(JoinPoint joinPoint, Object rvt) {
        HttpServletRequest request;
        String methodName = joinPoint.getSignature().getName();
        //获取传入目标方法的参数对象
        Object[] arguments = joinPoint.getArgs();
        //获取操作的当前类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Method[] methods = targetClass.getMethods();
        Class<?>[] clazzs;
        //查找当前执行完毕的方法  并进行操作
        // log.info("当前正在识别 执行方法");
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                clazzs = method.getParameterTypes();
                //1.判断传入参数数目(区别方法的重载)2.判断方法上方是否有相关注解
                if (clazzs.length == arguments.length && method.isAnnotationPresent(SrAdminLogAspect.class)) {
                    request = getHttpServletRequest();
                    //获取当前操作用户信息
                    SrAdmin admin = JwtUtil.getAdmin(request);
                    //获取访问ip
                    //String remoteIp = request.getRemoteAddr();
                    //获取方法上的userId名称 用于值对应
                    String userId = method.getAnnotation(SrAdminLogAspect.class).userId();
                    AdminAction adminAction = method.getAnnotation(SrAdminLogAspect.class).operateName();

                    String ip = IpUtils.getIpByRequest(request);
                    //获取当前管理员信息
                    SrAdmin adminInfo = srAdminDao.selectOne(new LambdaQueryWrapper<SrAdmin>()
                            .select(SrAdmin::getUsername).eq(SrAdmin::getId, admin.getId()));
                    SrAdminLog srAdminLog = new SrAdminLog();
                    //设置管理员id
                    srAdminLog.setAdminId(admin.getId());
                    //设置被操作的用户id
                    Long opUserId = getUserId(joinPoint, userId);
                    //设置管理员名称
                    srAdminLog.setAdminId(admin.getId());
                    srAdminLog.setOperationModel(adminAction.getDesc());
                    srAdminLog.setAdminName(adminInfo.getUsername());
                    srAdminLog.setIp(ip);
                    if (!opUserId.equals(0L)) {
                        srAdminLog.setOperationUserid(opUserId);
                        srAdminLog.setOperationUsername(getOpUserName(opUserId, adminAction));
                    }
                    if (rvt instanceof ResponseResult) {
                        ResponseResult responseResult = (ResponseResult) rvt;
                        srAdminLog.setOperationResult(responseResult.getMessage());
                    }
                    //设置操作时间
                    srAdminLog.setCreateTime(System.currentTimeMillis());
                    //设置操作名称和操作的参数
                    srAdminLog.setLogDesc(JSON.toJSONString(getNameAndValue(joinPoint)));
                    srAdminLogDao.insert(srAdminLog);
                    break;
                }
            }
        }

    }

    /**
     * 获取方法传入参数
     *
     * @param joinPoint aop对象
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @description
     * @author ClearLi
     * @date 2020/10/6 21:58
     */
    public Map<String, Object> getNameAndValue(JoinPoint joinPoint) {
        Map<String, Object> param = new HashMap<>(16);
        Object[] paramValues = joinPoint.getArgs();
        //从切入点获取参数列表
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < paramNames.length; i++) {
            //判断类型 (暂时判断这三个类型)
            // if (paramValues[i] instanceof Integer || paramValues[i] instanceof String || paramValues[i] instanceof Long) {
            param.put(paramNames[i], paramValues[i]);
            // }
        }
        return param;
    }

    /**
     * 返回当前操作的id
     *
     * @param joinPoint joinPoint
     * @return java.lang.Long
     * @description
     * @author ClearLi
     * @date 2020/10/29 17:47
     */
    public Long getUserId(JoinPoint joinPoint, String userId) {
        Object[] paramValues = joinPoint.getArgs();
        //从切入点获取参数列表
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        log.info(userId);
        //如果传入的userId要在对象中取值
        if (userId.contains("#")) {
            String[] split = userId.split("#");
            for (int i = 0, paramNamesLength = paramNames.length; i < paramNamesLength; i++) {
                String paramName = paramNames[i];
                if (paramName.equals(split[0])) {
                    Long targetUserId = 0L;
                    try {
                        targetUserId = (Long) getFieldValueByName(split[1], paramValues[i]);
                        log.info(targetUserId.toString());
                    } catch (Exception e) {
                        log.error("日志写入失败");
                    }
                    return targetUserId;
                }
            }
        } else {
            for (int i = 0; i < paramNames.length; i++) {
                //判断类型 (暂时判断这三个类型)
//            if (paramValues[i] instanceof Integer || paramValues[i] instanceof String || paramValues[i] instanceof Long) {
//                param.put(paramNames[i], paramValues[i]);
//            }
                //返回操作的用户id
                if (paramNames[i].equals(userId)) {
                    return (Long) paramValues[i];
                }
            }
        }

        //return param;
        return 0L;
    }

    /**
     * 根据操作人id和操作类型 获取被操作人的信息
     *
     * @param userId      被操作的人的id
     * @param adminAction 操作类型
     * @return java.lang.String
     * @description
     * @author ClearLi
     * @date 2020/11/11 15:02
     */
    private String getOpUserName(Long userId, AdminAction adminAction) {
        //如果是申请管理员或者是审核管理员 则从管理员申请表中查找
        return srAdminDao.selectOne(new LambdaQueryWrapper<SrAdmin>().select(SrAdmin::getUsername)
                .eq(SrAdmin::getId, userId)).getUsername();
    }

    /**
     * 获取当前request
     *
     * @return javax.servlet.http.HttpServletRequest
     * @description
     * @author ClearLi
     * @date 2020/10/6 22:12
     */
    public HttpServletRequest getHttpServletRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        if (sra != null) {
            return sra.getRequest();
        } else {
            throw new CustomException(AdminCode.LOG_FAILURE);
        }
    }

    /* 根据属性名获取属性值
     * */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter);
            return method.invoke(o);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取属性名数组
     */
    private static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
//            System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * @author : CLEAR Li
     * @version : V1.0
     * @description :
     * @date : 2020-10-25 0:21
     **/
    @Getter
    public enum AdminAction {

        CHANGE_ADMIN("更换仓库管理员","1"),
        /**
         * 审核管理员申请
         */
        MODIFY_ROLE("修改管理员角色", "0");


        /**
         * redis目录结构
         */
        String desc;
        /**
         * 解释
         */
        String defaultValue;

        AdminAction(String key, String defaultValue) {
            this.desc = key;
            this.defaultValue = defaultValue;
        }

        private static final ImmutableMap<String, AdminAction> CACHE;

        static {
            final ImmutableMap.Builder<String, AdminAction> builder = ImmutableMap.builder();
            for (AdminAction userGradeEnum : values()) {
                builder.put(userGradeEnum.defaultValue, userGradeEnum);
            }
            CACHE = builder.build();
        }

        /**
         * 返回操作内容
         *
         * @param desc redis的路径
         * @return com.yinghuo.framework.domain.user.enums.UserGradeEnum
         * @description
         * @author ClearLi
         * @date 2020/10/23 12:02
         */
        public static AdminAction getCache(String desc) {
            return CACHE.get(desc);
        }

    }
}
