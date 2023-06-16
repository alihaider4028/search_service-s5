package com.anushka.search_services5.publisher;

import com.anushka.search_services5.entity.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RabbitMQEventListProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQEventListProducer.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitMQEventListProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(Optional<List<Event>> resp){
        LOGGER.info(String.format("Json message sent -> %s", resp.toString()));
        rabbitTemplate.convertAndSend(exchange, routingJsonKey, resp.get());
    }


}
