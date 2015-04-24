package com.currencyfair.messageprocessor.scheduler;

import java.util.List;

import com.currencyfair.messageprocessor.client.WebSocketClient;
import com.currencyfair.messageprocessor.model.AnalyticsVolumeMessage;
import com.currencyfair.messageprocessor.model.AnalyticsVolumeMessageWrapper;
import com.currencyfair.messageprocessor.storage.dao.TradeMessageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsVolumeProcessor implements
        ApplicationListener<BrokerAvailabilityEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsVolumeProcessor.class);

    @Autowired
    WebSocketClient webSocketClient;

    @Autowired
    TradeMessageDao tradeMessageDao;

    @Override
    public void onApplicationEvent(final BrokerAvailabilityEvent event) {
    }

    @Scheduled(fixedDelay = 1000)//Should be configurable in properties
    public void sendDataUpdates() {
        List<AnalyticsVolumeMessage> volumes = tradeMessageDao.getVolumes();
        LOGGER.info("VolumesList:" + volumes);
        AnalyticsVolumeMessageWrapper analyticsVolumeMessageWrapper = new AnalyticsVolumeMessageWrapper();
        analyticsVolumeMessageWrapper.setVolumes(volumes);
        webSocketClient.sendVolumeDataToClient(analyticsVolumeMessageWrapper);
    }
}