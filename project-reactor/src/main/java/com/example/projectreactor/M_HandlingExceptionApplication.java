package com.example.projectreactor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class M_HandlingExceptionApplication {

    public static void main(String[] args) {
//        Flux.just(2, 7, 10)
//                .concatWith(Flux.error(new RuntimeException("Exception occurred")))
//                .concatWith(Mono.just(12))
//                .log()
//                .subscribe();

        System.out.println("============onErrorReturn============");
        /**The onErrorReturn() will emit the specified callback value when the error occurs.
         * In this way, the code will recover from the exception.**/
        Flux.just(2, 7, 10)
                .concatWith(Flux.error(new RuntimeException("Exception occurred")))
                .concatWith(Mono.just(12))
                .onErrorReturn(72)
                .log()
                .subscribe();

        System.out.println("============onErrorResume============");

        /**The onErrorResume() method accepts the Function interface and produces a result,
         * which is in this case a Mono.**/
        Flux.just(2, 7, 10)
                .concatWith(Flux.error(new RuntimeException("Exception occurred")))
                .concatWith(Mono.just(12))
                .onErrorResume(err -> {
                    System.out.println("Error caught: " + err);
                    return Mono.just(12);
                })
                .log()
                .subscribe();

        System.out.println("============onErrorContinue============");

        /**The onErrorContinue() catches the exception, the element that caused the exception will be dropped,
         * and the Publisher will continue emitting the remaining elements.**/
        Flux.just(2, 7, 10, 8, 12, 22, 24)
                .map(element -> {
                    if (element == 8) {
                        throw new RuntimeException("Exception occurred!");
                    }
                    return element % 2 == 0 ? element + " => Even" : element + " => Odd";
                })
                .onErrorContinue((ex, element) -> {
                    System.out.println("Exception caught: " + ex);
                    System.out.println("The element that caused the exception is: " + element);
                })
                .log()
                .subscribe();

        System.out.println("============onErrorMap============");

        /**With the onErrorMap(), the code can’t recover from the exception.
         * This method only catches the exception and transforms it from one type to another.**/
        Flux.just(2, 7, 10, 8, 12, 22, 24)
                .map(element -> {
                    if (element == 8) {
                        throw new RuntimeException("Exception occurred!");
                    }
                    return element;
                })
                .onErrorMap(ex -> {
                    System.out.println("Exception caught: " + ex);
                    return new CustomException(ex.getMessage(), ex);
                })
                .log()
                .subscribe();


        System.out.println("============doOnError============");

        /**This operator is one of the doOn Callbacks in Project Reactor.
         * It doesn’t change the original sequence.**/
        Flux.just(1, 2, 3)
                .concatWith(Flux.error(new RuntimeException("Exception occurred.")))
                .doOnError(ex -> System.out.println("Exception caught: " + ex)) // catch and print the exception
                .log()
                .subscribe();
    }
}

class CustomException extends Exception {

    public CustomException(String message, Throwable exception) {
        super(message, exception);
    }
}
