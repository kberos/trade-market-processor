package com.currencyfair.messageprocessor.storage.dao;


import com.currencyfair.messageprocessor.model.AnalyticsVolumeMessage;
import com.currencyfair.messageprocessor.model.TradeMessage;

import java.util.List;

public interface TradeMessageDao {
    public void storeMessage(TradeMessage tradeMessage);

    public List<TradeMessage> getAllMessages();

    public List<AnalyticsVolumeMessage> getVolumes();
}
