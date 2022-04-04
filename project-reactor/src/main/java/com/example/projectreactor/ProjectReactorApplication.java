package com.example.projectreactor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ProjectReactorApplication {

    public static void main(String[] args) {
        List<Integer> itemsFromMono = new ArrayList<>();
        List<Integer> itemsFromFlux = new ArrayList<>();

        // Create a Mono
        Mono<Integer> mono = Mono.just(121);

        // Create a Flux
        Flux<Integer> flux = Flux.just(12, 14, 9, 11, 12, 14, 9, 11, 12, 14, 9, 11, 12, 14);

        // Subscribe to Mono
        mono.subscribe(itemsFromMono::add);

        // Subscribe to Flux
        flux.subscribe(itemsFromFlux::add);

        System.out.println(itemsFromMono);
        System.out.println(itemsFromFlux);
    }

}
