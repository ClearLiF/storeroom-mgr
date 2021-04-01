package com.wxy.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxy.config.response.CommonCode;
import com.wxy.config.response.QueryResponseResult;
import com.wxy.config.response.ResponseResult;
import com.wxy.dao.SrRoleDao;
import com.wxy.handler.SrAdminAspect;
import com.wxy.handler.SrAdminLogAspect;
import com.wxy.model.SrRole;
import com.wxy.model.enums.DeleteMarkEnum;
import com.wxy.model.request.*;
import com.wxy.model.response.AdminCode;
import com.wxy.dao.SrAdminDao;
import com.wxy.dao.SrAdminRoleDao;
import com.wxy.model.SrAdmin;
import com.wxy.model.SrAdminRole;
import com.wxy.model.response.SrAdminVO;
import com.wxy.model.response.SrRoleVO;
import com.wxy.model.response.SrSubAdminVO;
import com.wxy.utils.JwtUtil;
import com.wxy.utils.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : AdminServiceImpl
 * @packageName : com.wxy.service
 * @description : 一般类
 * @date : 2021-03-20 0:50
 **/
@Service
@Slf4j
public class AdminService implements UserDetailsService {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Resource
    private SrAdminDao srAdminDao;

    @Resource
    private SrAdminRoleDao srAdminRoleDao;


    @Resource
    private SrRoleDao srRoleDao;

    /**
     * 登录主方法 用户security 检验用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //从数据库查询用户信息
        SrAdmin srAdmin = srAdminDao.selectByUserName(userName);
        //设置用户权限
        List<GrantedAuthority> collect = srAdmin.getSrRoleList().stream()
                .map(t -> new SimpleGrantedAuthority(t.getRoleName()))
                .collect(Collectors.toList());
        srAdmin.setGrantedAuthorityList(collect);
        return srAdmin;
    }

    /**
     * 根据id查询管理员信息
     *
     * @param userId 用户id
     * @return com.wxy.model.SrAdmin
     * @author wxy
     * @date 2021/3/20 10:46
     */
    public SrAdmin getAdminById(Long userId) {
        return srAdminDao.getUserInfoById(userId);
    }

    /**
     * 根据id获取管理员权限
     *
     * @param userId
     * @return
     */
    public SrRole getAdminRoleById(Long userId) {
        SrAdmin adminById = getAdminById(userId);
        return adminById.getSrRoleList().get(0);
    }

    /**
     * 初始化一个用户 测试使用
     *
     * @param srAdminDTO 用户信息
     * @return
     */
    @Transactional
    public ResponseResult initAdmin(SrAdminDTO srAdminDTO) {

        //先查询是否有重复的管理员
        SrAdmin searchAdmin = srAdminDao.selectOne(new LambdaQueryWrapper<SrAdmin>()
                .eq(SrAdmin::getUsername, srAdminDTO.getUsername()));
        if (searchAdmin != null) {
            return new ResponseResult(AdminCode.USER_REPEAT);
        }
        SrAdmin srAdmin = new SrAdmin();
        //拷贝基础信息
        BeanUtils.copyProperties(srAdminDTO, srAdmin);
        //设置用户名密码
        srAdmin.setPassword(bCryptPasswordEncoder.encode(srAdminDTO.getPassword()));
        //设置时间
        srAdmin.setCreateTime(System.currentTimeMillis());
        srAdmin.setUpdateTime(System.currentTimeMillis());
        srAdmin.setDeleteMark(DeleteMarkEnum.NO_DELETE);
        srAdmin.setLastLoginTime(System.currentTimeMillis());
        //保存用户信息
        srAdminDao.insert(srAdmin);
        //设置权限
        SrAdminRole srAdminRole = new SrAdminRole();
        srAdminRole.setAdminId(srAdmin.getId());
        srAdminRole.setRoleId(srAdmin.getRole());
        srAdminRole.setCreateTime(System.currentTimeMillis());
        srAdminRole.setUpdateTime(System.currentTimeMillis());
        srAdminRoleDao.insert(srAdminRole);
        //设置仓库管理员
        //todo 设置仓库管理员

        return ResponseResult.success();
    }

    /**
     * 本人修改个人信息
     *
     * @param adminModifyDTO admin
     * @param userId         用户id
     * @return
     */
    public ResponseResult modifyAdminInfo(SrAdminModifyDTO adminModifyDTO, Long userId) {

        //拷贝基础信息准备修改
        SrAdmin srAdmin = new SrAdmin();
        BeanUtils.copyProperties(adminModifyDTO, srAdmin);
        //设置密码加密
        if (srAdmin.getPassword()!=null){
            srAdmin.setPassword(bCryptPasswordEncoder.encode(srAdmin.getPassword()));
        }

        //设置用户id
        srAdmin.setId(userId);
        //设置更新时间
        srAdmin.setUpdateTime(System.currentTimeMillis());
        int update = srAdminDao.updateById(srAdmin);
        return update == 1 ? ResponseResult.success() : ResponseResult.fail();
    }

    /**
     * 更新用户的角色信息
     *
     * @param adminModifyRoleDTO 用户角色信息更改
     * @return
     */
    @SrAdminLogAspect(userId = "adminModifyRoleDTO#id", operateName = SrAdminAspect.AdminAction.MODIFY_ROLE)
    public ResponseResult modifyAdminRole(SrAdminModifyRoleDTO adminModifyRoleDTO) {
        //查询这个角色之前的角色信息
        SrAdminRole srAdminRole = srAdminRoleDao.selectOne(new LambdaQueryWrapper<SrAdminRole>()
                .eq(SrAdminRole::getAdminId, adminModifyRoleDTO.getId()));
        if (srAdminRole == null) {
            return new ResponseResult(AdminCode.AUTH_FAILURE);
        }
        //更新角色信息
        srAdminRoleDao.update(null, new LambdaUpdateWrapper<SrAdminRole>()
                .set(SrAdminRole::getRoleId, adminModifyRoleDTO.getRole())
                .set(SrAdminRole::getUpdateTime, System.currentTimeMillis())
                .eq(SrAdminRole::getAdminId, adminModifyRoleDTO.getId()));
        return ResponseResult.success();
    }

    /**
     * 获取所有权限信息
     *
     * @return
     */
    public QueryResponseResult<SrRoleVO> getAllAdminRole() {
        //从数据库中获取所有权限信息
        List<SrRole> srRoles = srRoleDao.selectList(new LambdaQueryWrapper<SrRole>());
        return StreamUtils.getPojoToVoToResponse(srRoles, SrRoleVO.class, 0);
    }

    /**
     * 根据类型查询管理员信息
     *
     * @param adminListDTO 管理员列表
     * @return
     */
    public QueryResponseResult<SrAdminVO> getAdminListByType(SearchAdminListDTO adminListDTO) {
        //设置分页
        PageHelper.startPage(adminListDTO.getPageNum(), adminListDTO.getPageSize());
        //根据类型查询管理员
        List<SrAdmin> srAdmins = srAdminDao.getAdminListByType(adminListDTO, adminListDTO.getType());
        PageInfo<SrAdmin> pageInfo = new PageInfo<>(srAdmins);
        //封装返回数据
        List<SrAdminVO> collect = srAdmins.stream().map(t -> {

            SrAdminVO srAdminVO = new SrAdminVO();
            BeanUtils.copyProperties(t, srAdminVO);
            srAdminVO.setSrRole(t.getSrRoleList().get(0));
            return srAdminVO;
        }).collect(Collectors.toList());
        return StreamUtils.getSimpleResponse(collect, pageInfo.getTotal());
    }

    /**
     * 获取所有仓库管理员 用于绑定仓库
     *
     * @return
     */
    public QueryResponseResult<SrSubAdminVO> getSubAdminList() {

        List<SrAdmin> srAdmins = srAdminDao.getSubAdminList();
        return StreamUtils.getPojoToVoToResponse(srAdmins, SrSubAdminVO.class, srAdmins.size());
    }

    /**
     * 获取用户id
     *
     * @param userId 用户id
     * @return
     */
    public QueryResponseResult<SrAdminVO> getAdminInfo(Long userId) {

        SrAdmin srAdmin = srAdminDao.getAdminInfo(userId);
        SrAdminVO srAdminVO = new SrAdminVO();
        BeanUtils.copyProperties(srAdmin, srAdminVO);
        srAdminVO.setSrRole(srAdmin.getSrRoleList().get(0));
        return new QueryResponseResult<>(CommonCode.SUCCESS, srAdminVO);
    }

    /**
     * 删除管理员
     *
     * @param adminId
     * @return
     */
    public ResponseResult deleteAdmin(Long adminId) {
        int update = srAdminDao.update(null,new LambdaUpdateWrapper<SrAdmin>().set(SrAdmin::getDeleteMark,
                DeleteMarkEnum.IS_DELETE.getValue()).eq(SrAdmin::getId,adminId));
        return update == 1 ? ResponseResult.success() : ResponseResult.fail();

    }

    /**
     * 修改下级管理员信息
     *
     * @param adminModifyDTO 管理员信息
     * @return
     */
    public ResponseResult modifySubAdminInfo(SrSubAdminModifyDTO adminModifyDTO) {
        //拷贝基础信息准备修改
        SrAdmin srAdmin = new SrAdmin();
        BeanUtils.copyProperties(adminModifyDTO, srAdmin);
        //设置密码加密
        if (srAdmin.getPassword()!=null){
            srAdmin.setPassword(bCryptPasswordEncoder.encode(srAdmin.getPassword()));
        }
        //设置更新时间
        srAdmin.setUpdateTime(System.currentTimeMillis());
        int update = srAdminDao.updateById(srAdmin);
        return update == 1 ? ResponseResult.success() : ResponseResult.fail();
    }
}
