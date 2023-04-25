package com.ayushman.WebFluxApi.Handler;

import com.ayushman.WebFluxApi.customExceptions.StudentNotFoundException;
import com.ayushman.WebFluxApi.dto.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class StudentService {

    Map<Integer, Student> map = Map.of(
            1, new Student(1, "fn1", "ln1"),
            2, new Student(2, "fn2", "ln2"),
            3, new Student(3, "fn3", "ln3"),
            4, new Student(4, "fn4", "ln4"),
            5, new Student(5, "fn5", "ln5")
    );

    public Mono<ServerResponse> getStudentById(ServerRequest request) {
        return ServerResponse.ok().body(getStudentById(Integer.parseInt(request.queryParam("id").get())), Student.class);
    }


    public Mono<Student> getStudentById(int id){
        return  Mono.just(id).filter(map::containsKey)
                .map(map::get)
//                .onErrorResume(ex -> Mono.empty()); // switch it to empty in case of error
        .switchIfEmpty(Mono.error(()-> new StudentNotFoundException(id)));
    }

    public Mono<ServerResponse> getAllStudent(ServerRequest request) {
        return ServerResponse.ok().body(getAllStudent(), Student.class);
    }

    public Flux<Student> getAllStudent(){
//        .onErrorReturn(Collections.emptyList()); // in case of error, switch it to empty list
       return Flux.fromIterable(map.values())
               .doFirst(this::throwRandomError);
    }

    private void throwRandomError(){
        var random = ThreadLocalRandom.current().nextInt(0, 10);
        if(random > 5)
            throw new RuntimeException("some random error");
    }
}
