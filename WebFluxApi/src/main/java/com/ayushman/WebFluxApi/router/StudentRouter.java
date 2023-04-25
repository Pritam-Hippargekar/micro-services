package com.ayushman.WebFluxApi.router;

import com.ayushman.WebFluxApi.Handler.CityHandler;
import com.ayushman.WebFluxApi.Handler.StudentService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class StudentRouter {

    @Bean
    public RouterFunction<ServerResponse> getStudentById(StudentService studentService) {
        return RouterFunctions.route(RequestPredicates.GET("/getStudentById")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), studentService::getStudentById);

    }

    @Bean
    public RouterFunction<ServerResponse> getAllStudent(StudentService studentService) {
        return RouterFunctions.route(RequestPredicates.GET("/getAllStudent")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), studentService::getAllStudent);

    }

}
