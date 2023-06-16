package com.anushka.search_services5.consumer;


import com.anushka.search_services5.Repository.EventConsumeRepository;
import com.anushka.search_services5.entity.Event;
import com.anushka.search_services5.entity.EventConsume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RabbitMQJsonConsumer {
    @Autowired
    EventConsumeRepository eventConsumeRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumeJsonMessage(Optional<List<Event>> resp) {
        LOGGER.info(String.format("Received JSON message -> %s", resp.toString()));
        EventConsume eventConsume = new EventConsume();
        resp.get().stream().forEach(event -> {
            eventConsume.setDate(event.getDate());
            eventConsume.setEventId(event.getEventId());
            eventConsume.setCapacity(event.getCapacity());
            eventConsume.setTitle(event.getTitle());
            eventConsume.setDescription(event.getDescription());
            eventConsume.setLocation(event.getLocationId());
            eventConsume.setTags(event.getTags());
            eventConsume.setOrganizerId(event.getOrganizerId());
            this.eventConsumeRepository.save(eventConsume);

        });
    }
}
