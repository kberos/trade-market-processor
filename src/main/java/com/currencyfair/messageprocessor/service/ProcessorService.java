package com.currencyfair.messageprocessor.service;

import com.currencyfair.messageprocessor.model.TradeMessage;
import org.springframework.stereotype.Service;

@Service
public interface ProcessorService {
    public void addMessageToQueue(TradeMessage tradeMessage);
}
