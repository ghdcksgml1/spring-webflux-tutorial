package com.wintersoft.webfluxch2;

import com.wintersoft.webfluxch2.item.Item;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemUnitTest {

    @Test
    void itemBasicsShouldWork() {
        Item sampleItem = new Item("Alf TV tray", "TV Tray", 19.99);

        assertThat(sampleItem.getName()).isEqualTo("Alf TV tray");
        assertThat(sampleItem.getDescription()).isEqualTo("TV Tray");
        assertThat(sampleItem.getPrice()).isEqualTo(19.99);
    }

    @Test
    void threadSleepIsABlockingCall() {
        Mono.delay(Duration.ofSeconds(1))
                .flatMap(tick -> {
                    try {
                        Thread.sleep(10);
                        return Mono.just(true);
                    } catch (InterruptedException e) {
                        return Mono.error(e);
                    }
                })
                .as(StepVerifier::create)
                .verifyComplete();
    }
}
