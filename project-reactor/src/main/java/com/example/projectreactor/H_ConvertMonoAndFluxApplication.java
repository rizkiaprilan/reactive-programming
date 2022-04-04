package com.example.projectreactor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class H_ConvertMonoAndFluxApplication {

    public static void main(String[] args) {

        Mono<String> mono = Mono.just("data");
        Flux<String> fluxFromMono = Flux.from(mono);
        fluxFromMono.subscribe(x -> System.out.println("Result Flux from Mono: " + x));

        // one value
        Flux<String> flux1 = Flux.just("data1");
        Mono<String> monoFromFlux1 = flux1.next();
        monoFromFlux1.subscribe(data -> System.out.println("monoFromFlux1 data: " + data));

        // multiple values
        Flux<String> flux2 = Flux.just("data2", "data3", "data4");
        Mono<String> monoFromFlux2 = flux2.next();
        monoFromFlux2.subscribe(data -> System.out.println("monoFromFlux2 data: " + data));
    }

}
