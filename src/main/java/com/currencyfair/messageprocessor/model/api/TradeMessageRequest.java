package com.currencyfair.messageprocessor.model.api;

import com.currencyfair.messageprocessor.MessageDateDeserializer;
import com.currencyfair.messageprocessor.model.CountryEnum;
import com.currencyfair.messageprocessor.model.CurrencyEnum;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class TradeMessageRequest {
    @NotNull
    String userId;
    @NotNull
    CurrencyEnum currencyFrom;
    @NotNull
    CurrencyEnum currencyTo;
    @NotNull
    Double amountSell;
    @NotNull
    Double amountBuy;
    @NotNull
    Double rate;

    @NotNull
    @JsonDeserialize(using = MessageDateDeserializer.class)
    Date timePlaced;

    CountryEnum originatingCountry;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CurrencyEnum getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(CurrencyEnum currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public CurrencyEnum getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(CurrencyEnum currencyTo) {
        this.currencyTo = currencyTo;
    }

    public Double getAmountSell() {
        return amountSell;
    }

    public void setAmountSell(Double amountSell) {
        this.amountSell = amountSell;
    }

    public Double getAmountBuy() {
        return amountBuy;
    }

    public void setAmountBuy(Double amountBuy) {
        this.amountBuy = amountBuy;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    //@JsonDeserialize(using = MessageDateDeserializer.class)
    public Date getTimePlaced() {
        return timePlaced;
    }

    //@JsonDeserialize(using = MessageDateDeserializer.class)
    public void setTimePlaced(Date timePlaced) {
        this.timePlaced = timePlaced;
    }

    public CountryEnum getOriginatingCountry() {
        return originatingCountry;
    }

    public void setOriginatingCountry(CountryEnum originatingCountry) {
        this.originatingCountry = originatingCountry;
    }

    @Override
    public String toString() {
        return "TradeMessageRequest{" +
                "userId='" + userId + '\'' +
                ", currencyFrom=" + currencyFrom +
                ", currencyTo=" + currencyTo +
                ", amountSell=" + amountSell +
                ", amountBuy=" + amountBuy +
                ", rate=" + rate +
                ", timePlaced=" + timePlaced +
                ", originatingCountry=" + originatingCountry +
                '}';
    }
}
