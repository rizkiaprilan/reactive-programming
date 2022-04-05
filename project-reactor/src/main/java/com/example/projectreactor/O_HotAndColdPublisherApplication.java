package com.example.projectreactor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@SpringBootApplication
public class O_HotAndColdPublisherApplication {

    public static void main(String[] args) throws InterruptedException {

        // COLD PUBLISHER

        /**Cold Publisher will not start emitting data until a Subscriber subscribes to it.
         * It creates a new data producer for each new subscription. **/
//        Flux<String> netFlux = Flux.fromStream(O_HotAndColdPublisherApplication::getVideo)
//                .delayElements(Duration.ofSeconds(2)); // each part will play for 2 seconds

        // ====================================
        // HOT PUBLISHER

        /**With Hot Publishers, there will be only one data producer.
         * All Subscribers listen to the data produced by the single data producer. The data is shared.**/
//        Flux<String> netFlux = Flux.fromStream(O_HotAndColdPublisherApplication::getVideo)
//                .delayElements(Duration.ofSeconds(2))
//                .share(); // turn the cold publisher into a hot publisher


        /**With the refCount() method, we can set how many Subscribers need to be subscribed for
         * the Hot Publisher to start emitting data.**/
//        Flux<String> netFlux = Flux.fromStream(O_HotAndColdPublisherApplication::getVideo)
//                .delayElements(Duration.ofSeconds(2))
//                .publish()
//                .refCount(2); // minSubscribers

        /**With the cache() method, the data will be stored into a cache as soon as the Publisher finishes emitting.
         * Then, the data will be replayed, extracted from the cache for any further Subscribers.**/
        Flux<String> netFlux = Flux.fromStream(O_HotAndColdPublisherApplication::getVideo)
                .delayElements(Duration.ofSeconds(2))
                .cache();


        

        subscriber(netFlux);
    }

    private static void subscriber(Flux<String> netFlux) throws InterruptedException {
        // First Subscriber
        netFlux.subscribe(part -> System.out.println("Subscriber 1: " + part));

        // wait 5 seconds before next Subscriber joins
        Thread.sleep(5000);

        // Seconds Subscriber
        netFlux.subscribe(part -> System.out.println("Subscriber 2: " + part));

        Thread.sleep(60000);
    }

    private static Stream<String> getVideo() {
        System.out.println("Request for the video streaming received.");
        return Stream.of("part 1", "part 2", "part 3", "part 4", "part 5");
    }

}

