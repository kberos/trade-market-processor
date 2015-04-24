package com.currencyfair.messageprocessor.listener;

import com.currencyfair.messageprocessor.client.WebSocketClient;
import com.currencyfair.messageprocessor.model.TradeMessage;
import com.currencyfair.messageprocessor.storage.dao.TradeMessageDao;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/*
Listens for the messages that are received from the queue. Saves the message in the DB and sends to the web socket client
for processing and finally to the client.
 */
@Component
public class TradeMessageListener implements MessageListener {
    private CountDownLatch latch = new CountDownLatch(1);

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeMessageListener.class);
    @Autowired
    WebSocketClient webSocketClient;

    @Autowired
    TradeMessageDao tradeMessageDao;

    @Override
    public void onMessage(Message message) {
        String messageBody = new String(message.getBody());

        LOGGER.info("Listener received TradeMessage:" + messageBody);
        TradeMessage tradeMessage;
        ObjectMapper mapper = new ObjectMapper();

        try {
            tradeMessage = mapper.readValue(messageBody, TradeMessage.class);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
        tradeMessageDao.storeMessage(tradeMessage);
        webSocketClient.sendRateDataToClient(tradeMessage);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
