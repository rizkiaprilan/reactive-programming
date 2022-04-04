package com.example.projectreactor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class D_ExtractDataMonoApplication {

    public static void main(String[] args) {

        // Extract data from Mono in Java – blocking way
        Mono<String> fromSupplier = Mono.fromSupplier(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello!";
        });

        String dataFromMono = fromSupplier.block();
        System.out.println("Data from Mono (Blocking Way): " + dataFromMono);

        System.out.println("===========================");

        // Get the data from Mono in Java – non-blocking way
        Mono.just("data").subscribe(s -> System.out.println("Data from Mono (Non Blocking Way): " + s));

    }
}
