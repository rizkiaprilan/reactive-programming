package com.example.projectreactor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class C_SubscribeMonoReactiveApplication {

    public static void main(String[] args) {
        // create a Mono
        Mono<String> mono = Mono.just("Hello Rizki");

        // subscribe to a Mono
        mono.subscribe(
                data -> System.out.println(data), // onNext
                err -> System.out.println(err),  // onError
                () -> System.out.println("Completed!") // onComplete
        );

        // =====================
        System.out.println("=====================");

        // create a Mono
        Mono<String> fromSupplier = Mono.fromSupplier(() -> {
            throw new RuntimeException("Exception occurred!");
        });

        // subscribe to a Mono
        fromSupplier.subscribe(
                data -> System.out.println("YOUR DATA: " + data), // onNext
                err -> System.out.println("SOMETHING ERROR: " + err),  // onError
                () -> System.out.println("Completed!") // onComplete
        );
    }
}
