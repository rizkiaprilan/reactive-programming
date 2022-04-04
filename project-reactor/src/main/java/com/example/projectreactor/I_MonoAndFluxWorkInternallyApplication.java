package com.example.projectreactor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.List;

@SpringBootApplication
public class I_MonoAndFluxWorkInternallyApplication {

    public static void main(String[] args) {
        Flux<String> cities = Flux.fromIterable(List.of("New York", "London", "Paris", "Toronto", "Rome"));
        cities.log().subscribe();
    }

}
