package com.example.projectreactor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.HttpServerErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@SpringBootApplication
public class N_RetryFailedOperationApplication {

    public static void main(String[] args) {
        System.out.println("============retry============");
//        Mono.just("data1")
//                .concatWith(Flux.error(new RuntimeException("Exception occurred.")))
//                .doOnError(ex -> System.out.println("LOG: Exception caught: " + ex))
//                .retry(3) //retry 3 times in case of an error
//                .log()
//                .subscribe();

        System.out.println("============retryWhen============");
        /**Let’s write a program that retries 3 times with a delay of 2 seconds between each attempt.**/
        Mono.just("data1")
                .concatWith(Flux.error(new RuntimeException("Exception occurred.")))
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .log()
                .subscribe();

        System.out.println("============backoff============");
        /**With Backoff, the delay will be increased progressively after each attempt.
         * In our example, it will be 2, 4, 8 seconds of delay.**/
        Mono.just("data1")
                .concatWith(Flux.error(new RuntimeException("Exception occurred.")))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
                .log()
                .subscribe();

        System.out.println("============backoff & filter============");
        /**We would want to retry only when we get a specific exception from the server in real-world applications.
         * Let’s set the code to retry only in case of the “InternalServerError” exception from the server.**/
        Mono.just("data1")
                .concatWith(Flux.error(new RuntimeException("Exception occurred.")))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                        .filter(throwable -> throwable instanceof HttpServerErrorException.InternalServerError))
                .log()
                .subscribe();
    }

}
