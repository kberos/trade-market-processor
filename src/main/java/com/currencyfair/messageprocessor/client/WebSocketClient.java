package com.currencyfair.messageprocessor.client;

import com.currencyfair.messageprocessor.AnalyticsProcessor;
import com.currencyfair.messageprocessor.model.AnalyticsVolumeMessageWrapper;
import com.currencyfair.messageprocessor.model.TradeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Component;

/*
This class is responsible for sending the data to the client through the open web socket connection.
 */
@Component
public class WebSocketClient {
    private final MessageSendingOperations<String> messagingTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketClient.class);

    @Autowired
    AnalyticsProcessor analyticsProcessor;

    @Autowired
    public WebSocketClient(MessageSendingOperations<String> messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendRateDataToClient(TradeMessage tradeMessage) {
        messagingTemplate.convertAndSend(
                "/analyticsData", analyticsProcessor.transformRateObject(tradeMessage));

    }

    public void sendVolumeDataToClient(AnalyticsVolumeMessageWrapper volumeData) {
        messagingTemplate.convertAndSend(
                "/analyticsData", volumeData);

    }
}
