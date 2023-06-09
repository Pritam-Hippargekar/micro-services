package com.ayushman.WebFluxApi.customExceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    private static final Logger logger = LoggerFactory.getLogger(GlobalErrorAttributes.class);
    private HttpStatus errorstatus = HttpStatus.BAD_REQUEST;
    private String erromessage = "please try after sometime";
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable error = super.getError(request);
//        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
        Map<String, Object> errorAttributes = new HashMap<>(8);
        errorAttributes.put("message", error.getMessage());
        errorAttributes.put("method", request.methodName());
        errorAttributes.put("path", request.path());

        MergedAnnotation<ResponseStatus> responseStatusAnnotation = MergedAnnotations
                .from(error.getClass(), MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(ResponseStatus.class);

        HttpStatus errorStatus = determineHttpStatus(error, responseStatusAnnotation);

        //必须设置, 否则会报错, 因为 DefaultErrorWebExceptionHandler 的 renderErrorResponse 方法会获取此属性, 重新实现 DefaultErrorWebExceptionHandler也可.
        errorAttributes.put("status", errorStatus.value());
        errorAttributes.put("code", errorStatus.value());

        //html view用
        errorAttributes.put("timestamp", new Date());
        //html view 用
        errorAttributes.put("requestId", request.exchange().getRequest().getId());

        errorAttributes.put("error", errorStatus.getReasonPhrase());
        errorAttributes.put("exception", error.getClass().getName());

        if (getError(request) instanceof UnAuthorizedException) {
            errorAttributes.put("status", HttpStatus.UNAUTHORIZED.value());
            errorAttributes.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        } else {
            errorAttributes.put("status", getErrorstatus());
            errorAttributes.put("message", getErromessage());
        }
        return errorAttributes;
    }

    //从DefaultErrorWebExceptionHandler中复制过来的
    private HttpStatus determineHttpStatus(Throwable error, MergedAnnotation<ResponseStatus> responseStatusAnnotation) {
        if (error instanceof ResponseStatusException) {
            return ((ResponseStatusException) error).getStatus();
        }
        return responseStatusAnnotation.getValue("code", HttpStatus.class).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpStatus getErrorstatus() {
        return errorstatus;
    }

    public void setErrorstatus(HttpStatus errorstatus) {
        this.errorstatus = errorstatus;
    }

    public String getErromessage() {
        return erromessage;
    }

    public void setErromessage(String erromessage) {
        this.erromessage = erromessage;
    }
}
