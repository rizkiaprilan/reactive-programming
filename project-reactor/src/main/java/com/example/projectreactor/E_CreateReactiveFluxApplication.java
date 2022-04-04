package com.example.projectreactor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.List;

@SpringBootApplication
public class E_CreateReactiveFluxApplication {

    public static void main(String[] args) {

        // create an empty Flux
        Flux flux1 = Flux.empty();

        // create a Flux that will hold only one value
        Flux<String> flux2 = Flux.just("data");

        // create a Flux that will hold multiple values
        Flux<String> flux3 = Flux.just("data1", "data2", "data3");

        List<String> cities = List.of("London", "Paris", "Rome", "Amsterdam");
        Flux<String> flux4 = Flux.fromIterable(cities);

        // create a Flux that will contains values 5...29
        Flux<Integer> flux5 = Flux.range(5, 25);

    }
}
