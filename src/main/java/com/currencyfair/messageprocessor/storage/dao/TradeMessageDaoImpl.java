package com.currencyfair.messageprocessor.storage.dao;

import com.currencyfair.messageprocessor.model.AnalyticsVolumeMessage;
import com.currencyfair.messageprocessor.model.TradeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class TradeMessageDaoImpl implements TradeMessageDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeMessageDaoImpl.class);

    private MongoOperations mongoOps;
    private static final String TRADE_MESSAGE_COLLECTION = "TradeMessage";

    public TradeMessageDaoImpl(MongoOperations mongoOps) {
        this.mongoOps = mongoOps;
    }

    @Override
    public void storeMessage(TradeMessage tradeMessage) {
        mongoOps.insert(tradeMessage, TRADE_MESSAGE_COLLECTION);
        LOGGER.debug("Added message:" + tradeMessage);
    }

    @Override
    public List<TradeMessage> getAllMessages() {
        List<TradeMessage> tradeMessages = mongoOps.findAll(TradeMessage.class, TRADE_MESSAGE_COLLECTION);
        LOGGER.info("Found Messages in DB:" + tradeMessages);
        return tradeMessages;
    }

    @Override
    public List<AnalyticsVolumeMessage> getVolumes() {
        Aggregation agg = newAggregation(
                match(Criteria.where("currencyFrom").is("EUR")),
                sort(Sort.Direction.ASC, "currencyTo"),
                group("currencyTo").sum("amountSell").as("volume")
        );
        AggregationResults<AnalyticsVolumeMessage> groupResults
                = mongoOps.aggregate(agg, TRADE_MESSAGE_COLLECTION, AnalyticsVolumeMessage.class);
        List<AnalyticsVolumeMessage> result = groupResults.getMappedResults();

        LOGGER.debug("GetVolumes returned from DB:" + result);
        return result;
    }
}
