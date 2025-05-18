package fun.yamds.exception;

import cn.dev33.satoken.exception.SaTokenException;
import fun.yamds.constant.SatokenErrorCodeEnum;
import fun.yamds.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SatokenExceptionHandler {
    @ExceptionHandler(SaTokenException.class)
    public Result handlerSaTokenException(SaTokenException e) {

        // 根据不同异常细分状态码返回不同的提示
        SatokenErrorCodeEnum errorCode = SatokenErrorCodeEnum.getByCode(e.getCode());
        if (errorCode != null) {
            return Result.error().code(errorCode.getCode()).msg(errorCode.getMsg());
        }
        return Result.error().msg("服务器繁忙，请稍后重试...");
    }
}