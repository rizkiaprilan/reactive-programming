package com.example.projectreactor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class L_CallbackProjectReactorApplication {

    public static void main(String[] args) {
        Flux.just("data1", "data2", "data3")
                .doOnSubscribe(subscription -> System.out.println("Subscription: " + subscription))
                .doOnNext(data -> System.out.println("Data: " + data))
                .doOnComplete(() -> System.out.println("Publisher sent Completion signal!"))
                .doOnError(err -> System.out.println("Error: " + err))
                .subscribe();

        // ============================

        Mono.fromSupplier(() -> {
            throw new RuntimeException(" an error occurred!"); // invoking the onError signal from the Publisher (Mono)
        }).doOnError(err -> System.out.println("Error: " + err.getMessage())).subscribe();

        // ============================

        // successful scenario
        Mono.just("data")
                .doFinally(signal -> System.out.println(signal + " signal received."))
                .subscribe();

        // failed scenario
        Mono mono = Mono.fromSupplier(() -> {
            throw new RuntimeException(" an error occurred!"); // invoking the onError signal from the Publisher (Mono)
        });

        mono.doFinally(signal -> System.out.println(signal + " signal received.")).subscribe();
    }

}
