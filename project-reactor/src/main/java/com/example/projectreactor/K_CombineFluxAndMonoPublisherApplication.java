package com.example.projectreactor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@SpringBootApplication
public class K_CombineFluxAndMonoPublisherApplication {

    public static void main(String[] args) {
        Flux<String> firstFlux = Flux.fromArray(new String[]{"a", "b", "c"});
        Flux<String> secondFlux = Flux.fromArray(new String[]{"d", "e", "f"});
        Mono<String> mono = Mono.just("f");

        // combine two Flux Publishers with Concat
        Flux<String> combinedFlux = Flux.concat(firstFlux, secondFlux);
        System.out.print("Flux.concat: ");
        combinedFlux.subscribe(element -> System.out.print(element + " "));

        // combine two Flux Publishers with Zip
        System.out.printf("%nFlux.zip: ");
        Flux.zip(firstFlux, secondFlux, (first, second) -> first + second).subscribe(s -> System.out.printf(s + " "));

        // combine two Flux Publishers with ZipWith
        System.out.printf("%nfirstFlux.zipWith: ");
        firstFlux.zipWith(secondFlux, (firstNum, secondNum) -> firstNum + secondNum).subscribe(s -> System.out.printf(s + " "));

        StepVerifier.create(returnMerged())
                .expectNext("a")
                .expectNext("c")
                .expectNext("b")
                .expectNext("d")
                .expectComplete()
                .verify();
    }

    private static Flux<String> returnMerged() {
        Flux<String> firstFlux = Flux.fromArray(new String[]{"a", "b"})
                .delayElements(Duration.ofMillis(100));
        Flux<String> secondFlux = Flux.fromArray(new String[]{"c", "d"})
                .delayElements(Duration.ofMillis(125));

        return Flux.merge(firstFlux, secondFlux);
//        return Flux.mergeSequential(firstFlux, secondFlux);
//        return firstFlux.mergeWith(secondFlux);
    }
}
