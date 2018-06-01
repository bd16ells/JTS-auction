package com.example.auctionapp.domain.auction.event.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class Sender {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private RabbitTemplate template;
    @Autowired
    private Queue queue;

    public void send(Object data){
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        this.template.convertAndSend(queue.getName(), data);
        logger.info(" [x] rabbitMQ message sent!");
    }
}
