package com.ayushman.WebFluxApi.router;

import com.ayushman.WebFluxApi.Handler.CityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class CityRouter {

    @Bean
    public RouterFunction<ServerResponse> routeCity(CityHandler cityHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), cityHandler::helloCity);
    }
}
