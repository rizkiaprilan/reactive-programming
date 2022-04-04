package com.example.projectreactor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class J_TransformMonoOrFluxApplication {

    public static void main(String[] args) {
        Flux.fromArray(new String[]{"Tom", "Melissa", "Steven", "Megan"})
                .filter(name -> name.length() > 5)
                .map(String::toUpperCase)
                .subscribe(System.out::println);
    }

}
