package com.currencyfair.messageprocessor.service;

import com.currencyfair.messageprocessor.model.TradeMessage;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class TradeMessageProcessor implements ProcessorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeMessageProcessor.class);

    private final MessageSendingOperations<String> messagingTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    public TradeMessageProcessor(MessageSendingOperations<String> messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void addMessageToQueue(TradeMessage tradeMessage) {
        LOGGER.info("Adding message to queue:" + tradeMessage);
        String messageJson = "";
        ObjectMapper mapper = new ObjectMapper();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            mapper.writeValue(stream, tradeMessage);
            messageJson = stream.toString("UTF-8");
        } catch (IOException e) {
            throw new IllegalStateException();
        }

        rabbitTemplate.convertAndSend("processorQueue", messageJson);
        LOGGER.info("TradeMessage:" + tradeMessage + " was added to queue");

    }
}
