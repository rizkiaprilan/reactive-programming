package com.example.projectreactor;

import org.reactivestreams.Publisher;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class B_CreateReactiveMono {

    public static void main(String[] args) {
        Publisher<String> publisher = Mono.just("Hello");

        // will create an empty Mono
        Mono<String> mono = Mono.justOrEmpty(null);

        // Create a Mono from the Supplier interface
        Mono <String> fromSupplier = Mono.fromSupplier(() -> "Hello");

        // From Callable interface
        Mono<String> fromCallable = Mono.fromCallable(() -> "Hello");

        // Creating a Mono from Future
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "some value");
        Mono<String> fromFuture = Mono.fromFuture(completableFuture);
    }
}
