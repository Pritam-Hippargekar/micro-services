package com.ayushman.WebFluxApi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.time.Duration;

@SpringBootApplication
public class WebFluxApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WebFluxApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Mono<String> mono = Mono.just("John");
//		Mono<Object> monoEmpty = Mono.empty();
//		Mono<Object> monoError = Mono.error(new Exception());



//		Flux<Integer> flux = Flux.just(1, 2, 3, 4);
		Flux<Long> flux = Flux.interval(Duration.ofSeconds(10));
		flux.log().subscribe(System.out::println,(err)->System.out.println(err.getMessage()));
	}
}
