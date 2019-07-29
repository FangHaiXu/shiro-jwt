package com.fhx.exception;

import com.fhx.common.response.RestResponse;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.BeansException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 统一异常处理类
 *
 * @author fhx
 */
@ControllerAdvice
@ResponseBody
public class WebException {

    /**
     * 成功
     */
    private static final int STATUS_SUCCESS = 200;

    /**
     * 错误
     */
    private static final int STATUS_ERROR = 500;

    /**
     * 效验没通过
     */
    private static final int STATUS_ERROR_VALID = 10001;

    /**
     * 对象转换异常
     */
    private static final int STATUS_ERROR_BEANS = 10002;

    /**
     * 对象参数验证没通过异常
     *
     * @param e 异常
     * @return 自定义message
     */
    @ExceptionHandler(BindException.class)
    private RestResponse bindExceptionHandler(BindException e) {
        String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return RestResponse.error(message, e.getMessage(), STATUS_ERROR_VALID);
    }

    /**
     * 单属性验证未通过异常
     *
     * @param e 异常
     * @return 自定义message
     */
    @ExceptionHandler(ConstraintViolationException.class)
    private RestResponse constraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violationSet = e.getConstraintViolations();
        return violationSet.stream().map(ConstraintViolation::getMessageTemplate).findFirst().map(messageTemplate -> RestResponse.error(messageTemplate, e.getMessage(), STATUS_ERROR_VALID)).orElse(null);
    }

    /**
     * 单属性验证未通过异常
     *
     * @param e 异常
     * @return 自定义message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private RestResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return RestResponse.error(msg, e.getMessage(), STATUS_ERROR_VALID);
    }

    /**
     * shiro 账号不存在异常
     *
     * @param e 异常
     * @return 信息
     */
    @ExceptionHandler(UnknownAccountException.class)
    private RestResponse unknownAccountException(UnknownAccountException e) {
        String msg = "您输入的账号不存在";
        return RestResponse.error(msg, e.getMessage(), STATUS_ERROR_VALID);
    }

    /**
     * shiro 密码错误异常
     *
     * @param e 异常
     * @return 消息
     */
    @ExceptionHandler(IncorrectCredentialsException.class)
    private RestResponse incorrectCredentialsException(IncorrectCredentialsException e) {
        String msg = "密码错误,请重新输入";
        return RestResponse.error(msg, e.getMessage(), STATUS_ERROR_VALID);
    }

    /**
     * 对象转换失败异常
     * @param e 异常
     * @return  消息
     */
    @ExceptionHandler(BeansException.class)
    private RestResponse beansException(BeansException e){
        String msg = "对象转换失败";
        return RestResponse.error(msg,e.getMessage(),STATUS_ERROR_BEANS);
    }

    /**
     * 所有异常捕获
     *
     * @param e 异常
     * @return 自定义message
     */
    @ExceptionHandler(RuntimeException.class)
    private RestResponse bindExceptionHandler(RuntimeException e) {
        e.printStackTrace();
        String msg = "阿哦...程序出错了,请联系管理员!!";
        return RestResponse.error(msg, e.getMessage(), STATUS_ERROR_VALID);
    }
}
