package com.example.projectreactor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class G_ExtractDataFluxApplication {

    public static void main(String[] args) {

        Flux<String> flux = Flux.fromArray(new String[]{"data1", "data2", "data3"});

        flux.doOnNext(System.out::println).subscribe();
    }

}
