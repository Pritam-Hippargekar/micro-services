package com.ayushman.WebFluxApi.customExceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalErrorWebExceptionHandler.class);


    public GlobalErrorWebExceptionHandler(GlobalErrorAttributes g, ApplicationContext applicationContext,
                                          ServerCodecConfigurer serverCodecConfigurer) {
        super(g, new WebProperties.Resources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }
//    public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
//                                          ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer
//    ) {
//        super(errorAttributes, resources, applicationContext);
//        super.setMessageWriters(serverCodecConfigurer.getWriters());
//        super.setMessageReaders(serverCodecConfigurer.getReaders());
//    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
        ErrorAttributeOptions options = ErrorAttributeOptions
                .defaults()
                .including(ErrorAttributeOptions.Include.MESSAGE)
                .including(ErrorAttributeOptions.Include.STACK_TRACE);
//        ErrorAttributeOptions.Include.EXCEPTION,
        final Map<String, Object> errorPropertiesMap = getErrorAttributes(request, options);

        //print stack trace before removing from response
        logger.error((String)errorPropertiesMap.get("trace"));
        errorPropertiesMap.remove("trace");

        if (getError(request) instanceof UnAuthorizedException) {
            logger.error("UnAuthorizedException occured > {}", request.requestPath());
            return ServerResponse.status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(errorPropertiesMap));
        }
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorPropertiesMap));
    }
}
