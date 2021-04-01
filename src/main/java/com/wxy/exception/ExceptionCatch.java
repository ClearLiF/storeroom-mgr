package com.wxy.exception;



import avro.shaded.com.google.common.collect.ImmutableMap;
import com.wxy.config.response.CommonCode;
import com.wxy.config.response.ResponseResult;
import com.wxy.config.response.ResultCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 统一异常捕获类
 *
 * @author blade
 *
 * ControllerAdvice 控制器增强
 **/
@ControllerAdvice
public class ExceptionCatch {
    /**
     * 定义map，配置异常类型所对应的错误代码
     *  ImmutableMap谷歌的map，只能读不能更改，是线程安全的
     */
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;
    /**
     * 定义map的builder对象，去构建ImmutableMap
     */
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    /**
     * 捕获CustomException此类异常
     * @param customException 自定义异常类型
     * @return
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException customException) {
        ResultCode resultCode = customException.getResultCode();
        return new ResponseResult(resultCode);
    }

    //捕获Exception此类异常

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception) {
        if (EXCEPTIONS == null) {
            //EXCEPTIONS构建成功
            EXCEPTIONS = builder.build();
        }
        exception.printStackTrace();
        //从EXCEPTIONS中找异常类型所对应的错误代码，如果找到了将错误代码响应给用户，如果找不到给用户响应99999异常
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        if (resultCode != null) {
            return new ResponseResult(resultCode);
        }
        else {
            System.out.println("*************报错信息************");

//          System.out.println(resultCode);
            exception.printStackTrace();
            System.out.println("*************报错信息************");
            //返回99999异常

            return new ResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    static {
        //定义异常类型所对应的错误代码
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
        builder.put(AccessDeniedException.class, CommonCode.PERMISSION_DENIED);

    }
}
