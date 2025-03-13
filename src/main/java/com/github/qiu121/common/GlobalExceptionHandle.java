package com.github.qiu121.common;

import cn.dev33.satoken.exception.*;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @author <a href="mailto:qiu0089@foxmail.com">qiu121</a>
 * @version 1.0
 * @date 2023/3/14
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Object> handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage());
        // return new Result<>(HttpStatus.HTTP_BAD_REQUEST, "操作不允许,请重试");
        return new Result<>(1, "操作不允许,请重试");
    }

    @ExceptionHandler(SaTokenException.class)
    public SaResult handleSaException(SaTokenException e) {
        String message;
        if (e instanceof NotLoginException) {
            log.error("账号未登录: {}", e.getMessage());
            String type = ((NotLoginException) e).getType();
            if (type.equals(NotLoginException.TOKEN_TIMEOUT_MESSAGE)) {
                message = "账号登录已过期，请重新登录";
            } else {
                message = "请登录后再进行操作";
            }
        } else if (e instanceof NotPermissionException) {
            message = "当前用户无访问权限";
        } else if (e instanceof NotRoleException) {
            message = "当前访问无对应角色权限";
        } else if (e instanceof DisableServiceException) {
            message = "此账号已被禁止访问服务\uD83D\uDEAB";
        } else {
            message = "权限异常！";
        }
        return SaResult.error(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handleException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder
                    // .append(fieldError.getField())
                    // .append(":")
                    .append(fieldError.getDefaultMessage()).append(", ");
        }
        String msg = builder.toString();
        // return new Result<>(HttpStatus.HTTP_BAD_REQUEST, msg);
        return new Result<>(2, msg);

    }

    @ExceptionHandler(NullPointerException.class)
    public Result<Object> handleException(NullPointerException e) {
        log.error(e.getMessage());
        // return new Result<>(HttpStatus.HTTP_BAD_GATEWAY, "操作异常");
        return new Result<>(3, "操作异常");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result<Object> handleException(DuplicateKeyException e) {
        log.error(e.getMessage());
        // return new Result<>(HttpStatus.HTTP_BAD_GATEWAY, "记录已存在,不可重复添加");
        return new Result<>(4, "记录已存在,不可重复添加");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Result<?> handleException(DataIntegrityViolationException e) {
        log.error(e.getMessage());
        // return new Result<>(HttpStatus.HTTP_BAD_GATEWAY, "数据不合规范,请重新输入");
        return new Result<>(5, "数据不合规范,请重新输入");
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public Result<Object> handleException(MissingPathVariableException e) {
        log.error(e.getMessage());
        // return new Result<>(HttpStatus.HTTP_BAD_GATEWAY, "接口方法请求异常");
        return new Result<>(6, "接口方法请求异常");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<Object> handleException(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage());
        // return new Result<>(HttpStatus.HTTP_BAD_GATEWAY, "操作异常，数据类型不匹配");
        return new Result<>(7, "操作异常，数据类型不匹配");
    }

    @ExceptionHandler(Throwable.class)
    public Result<Object> handleException(Throwable e) {

        // int status = HttpStatus.HTTP_INTERNAL_ERROR;
        int status = 1;
        String message = "系统内部异常，请稍后再试";

        // if (e instanceof BusinessException) {
        //     // status = HttpStatus.HTTP_BAD_GATEWAY;
        //     status = 2;
        //     message = e.getMessage();
        // } else if (e instanceof NotFoundException) {
        //     // status = HttpStatus.HTTP_NOT_FOUND;
        //     status = 3;
        //     message = e.getMessage();
        // } else if (e instanceof DuplicateException) {
        //     // status = HttpStatus.HTTP_UNAVAILABLE;
        //     status = 4;
        //     message = e.getMessage();
        // }
        log.error("异常类型： {}", e.getMessage(), e);
        return new Result<>(status, message);
    }


}
