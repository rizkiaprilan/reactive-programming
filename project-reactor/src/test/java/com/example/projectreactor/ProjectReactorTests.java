package com.example.projectreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

class ProjectReactorTests {

    @Test
    void positiveExpectation() {
        //Create a Flux
        Flux<Integer> fluxToTest = Flux.just(1, 2, 3, 4, 5);

        // Create a test
        StepVerifier.create(fluxToTest)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .expectComplete() // we expect Flux to complete after sending the number 5 which is the last element
                .verify();
    }

    @Test
    void negativeExpectation() {
        //Create a Flux
        Flux<Integer> fluxToTest = Flux.just(1, 2, 3, 4, 5);

        // Create a test
        StepVerifier.create(fluxToTest)
                .expectNext(1)
                .expectNext(2)
                .expectNext(6)
                .expectNext(4)
                .expectNext(5)
                .expectComplete() // we expect Flux to complete after sending the number 5 which is the last element
                .verify();
    }

    @Test
    void expectNextMatches() {

        Flux<String> fluxToTest = Flux.just("Jessica", "John", "Tomas", "Melissa", "Steve", "Megan", "Monica", "Henry")
                .filter(name -> name.length() <= 5)
                .map(String::toUpperCase);

        StepVerifier.create(fluxToTest)
                .expectNext("JOHN")
                .expectNext("TOMAS")
                .expectNextMatches(name -> name.startsWith("ST"))
                .expectNext("MEGAN")
                .expectNext("HENRY")
                .expectComplete()
                .verify();
    }

    @Test
    void expectThrowableError() {
        Flux<Integer> fluxToTest = Flux.just(1, 2, 3, 4)
                .concatWith(Mono.error(new ArithmeticException("Error occurred!")));

        StepVerifier.create(fluxToTest)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectErrorMatches(throwable -> throwable instanceof ArithmeticException &&
                        throwable.getMessage().equals("Error occurred!"))
                .verify();
    }

    @Test
    void timeConsumingPublisher() {
        Mono<String> monoWithDelay = Mono.just("Reactive")
                .delayElement(Duration.ofSeconds(5));

        StepVerifier.create(monoWithDelay)
                .expectNext("Reactive")
                .expectComplete()
                .verify();
    }

}
