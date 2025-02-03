package com.eidiko.springboot;

import com.eidiko.springboot.entity.WikimediaData;
import com.eidiko.springboot.repository.WikimediaDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaDatabaseConsumer {
    private final WikimediaDataRepository wikimediaDataRepository ;

    public KafkaDatabaseConsumer(WikimediaDataRepository wikimediaDataRepository) {
        this.wikimediaDataRepository = wikimediaDataRepository;
    }

    @KafkaListener(topics = "wikimedia_recentchange",
                   groupId = "myGroup")
    public void consume(String eventMessage){
        log.info("Event message received {}" ,eventMessage);
        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(eventMessage);
       wikimediaDataRepository.save(wikimediaData);
    }
}
