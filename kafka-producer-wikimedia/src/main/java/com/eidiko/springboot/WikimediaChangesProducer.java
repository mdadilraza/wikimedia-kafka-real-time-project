package com.eidiko.springboot;


import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class WikimediaChangesProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {
        String topic = "wikimedia_recentchange";


        BackgroundEventHandler eventHandler = new WikiMediaChangesHandler(kafkaTemplate, topic);


        EventSource.Builder eventSourceBuilder = new EventSource.Builder(
                ConnectStrategy.http(URI.create("https://stream.wikimedia.org/v2/stream/recentchange"))
                        .connectTimeout(5, TimeUnit.SECONDS) // Optional: set HTTP timeout
        );


        BackgroundEventSource eventSource = new BackgroundEventSource.Builder(eventHandler, eventSourceBuilder)
                .build();


        eventSource.start();


        TimeUnit.MINUTES.sleep(10);
    }
}
