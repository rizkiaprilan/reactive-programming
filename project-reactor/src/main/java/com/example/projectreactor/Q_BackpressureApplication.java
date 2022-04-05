package com.example.projectreactor;

import org.reactivestreams.Subscription;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class Q_BackpressureApplication {

    public static void main(String[] args) {
        Flux<Integer> publisher = Flux.range(1, 100).log();

        publisher.subscribe(new BaseSubscriber<>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(5); // request has limit, cannot exceed 5 request
            }

            @Override
            protected void hookOnNext(Integer value) {
                if (value == 5) { // we know that the last element is 5
                    cancel();
                }
            }
        });
    }
}

