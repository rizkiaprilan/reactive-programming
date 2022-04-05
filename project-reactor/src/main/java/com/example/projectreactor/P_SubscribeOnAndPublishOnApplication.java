package com.example.projectreactor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
public class P_SubscribeOnAndPublishOnApplication {

    /**
     * We have different Scheduler options that we can use:
     * <p>
     * 1. Schedulers.parallel() – It has a fixed pool of workers. The number of threads is equivalent to the number of CPU cores.
     * 2. Schedulers.boundElastic() – It has a bounded elastic thread pool of workers. The number of threads can grow based on the need. The number of threads can be much bigger than the number of CPU cores.
     * Used mainly for making blocking IO calls.
     * 3. Schedulers.single() –  Reuses the same thread for all callers.
     **/

    public static void main(String[] args) {

        /**
         * The subscribeOn() method applies to the subscription process.
         * We can place it anywhere in the reactive chain.
         * It accepts Scheduler and picks up the thread from the provided thread pool.
         * **/
        Flux.just("New York", "London", "Paris", "Amsterdam")
                .subscribeOn(Schedulers.boundedElastic())
                .map(String::toUpperCase)
                .filter(cityName -> cityName.length() <= 8)
                .map(cityName -> cityName.concat(" City"))
                .log().subscribe();

        System.out.println("========================");
        
        /**
         * Everything before the publishOn operator was executed by the main thread and
         * everything after by the boundedElastic-1 thread. That is because the publishOn acts as any other operator.
         * It takes signals from upstream and replays them downstream while executing the callback on a worker
         * from the associated Scheduler.
         * **/
        Flux.just("New York", "London", "Paris", "Amsterdam")
                .map(P_SubscribeOnAndPublishOnApplication::stringToUpperCase)
                .publishOn(Schedulers.boundedElastic())
                .map(P_SubscribeOnAndPublishOnApplication::concat)
                .subscribe();
    }

    private static String stringToUpperCase(String name) {
        System.out.println("stringToUpperCase: " + Thread.currentThread().getName());
        return name.toUpperCase();
    }

    private static String concat(String name) {
        System.out.println("concat: " + Thread.currentThread().getName());
        return name.concat(" City");
    }


}

