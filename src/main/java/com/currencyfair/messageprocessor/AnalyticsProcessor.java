package com.currencyfair.messageprocessor;

import com.currencyfair.messageprocessor.model.AnalyticsRateMessage;
import com.currencyfair.messageprocessor.model.AnalyticsVolumeMessage;
import com.currencyfair.messageprocessor.model.AnalyticsVolumeMessageWrapper;
import com.currencyfair.messageprocessor.model.TradeMessage;
import org.springframework.stereotype.Component;

/*
The processor of the TradeMessages. It is responsible for having the business logic that will transform the messages to meaningful
AnalyticsData that are easier (and cheaper) to be transferee and presented to the web client. It could be a series of filters in Spring Integration
that each one handles and transform the data and sends volumes trends and more meaningful financial data but for the sake of the test it is just a
transformation to a pair and discarding the not needed fields.
 */
@Component
public class AnalyticsProcessor {

    public AnalyticsRateMessage transformRateObject(TradeMessage tradeMessage) {
        AnalyticsRateMessage analyticsRateMessage = new AnalyticsRateMessage();
        analyticsRateMessage.setPair(tradeMessage.getCurrencyFrom() + "/" + tradeMessage.getCurrencyTo());
        analyticsRateMessage.setRate(tradeMessage.getRate());
        return analyticsRateMessage;
    }
}
