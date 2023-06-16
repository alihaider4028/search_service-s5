package com.anushka.search_services5.consumer;


import com.anushka.search_services5.Repository.EventConsumeRepository;
import com.anushka.search_services5.entity.Event;
import com.anushka.search_services5.entity.EventConsume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RabbitMQJEventConsumer {
    @Autowired
    EventConsumeRepository eventConsumeRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJEventConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeJsonMessage(Event resp) {
        LOGGER.info(String.format("Received JSON message -> %s", resp.toString()));
        EventConsume eventConsume = new EventConsume();

            eventConsume.setDate(resp.getDate());
            eventConsume.setEventId(resp.getEventId());
            eventConsume.setCapacity(resp.getCapacity());
            eventConsume.setTitle(resp.getTitle());
            eventConsume.setDescription(resp.getDescription());
            eventConsume.setLocation(resp.getLocationId());
            eventConsume.setTags(resp.getTags());
            eventConsume.setOrganizerId(resp.getOrganizerId());
            this.eventConsumeRepository.save(eventConsume);


    }
}
