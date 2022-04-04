package com.example.projectreactor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class F_SubscribeFluxReactiveApplication {

    public static void main(String[] args) {

        // Create a Flux
        Flux<String> flux = Flux.just("data1", "data2", "data3");

        // subscribe to a Mono
        flux.subscribe(
                data -> onNext(data), // onNext
                err -> onError(err),  // onError
                () -> onComplete() // onComplete
        );
    }

    private static <T> void onNext(T data) {
        System.out.println("onNext: Data received: " + data);
    }

    private static void onError(Throwable err) {
        System.out.println("onError: Exception occurred: " + err.getMessage());
    }

    private static void onComplete() {
        System.out.println("onComplete: Completed!");
    }
}
