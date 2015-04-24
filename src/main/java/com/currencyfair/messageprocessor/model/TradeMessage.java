package com.currencyfair.messageprocessor.model;


import org.springframework.data.annotation.Id;

import java.util.Date;

public class TradeMessage {

    @Id
    private String id;
    private String userId;
    private CurrencyEnum currencyFrom;
    private CurrencyEnum currencyTo;
    private Double amountSell;
    private Double amountBuy;
    private Double rate;
    private Date timePlaced;
    private CountryEnum originatingCountry;


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

    public Date getTimePlaced() {
        return timePlaced;
    }

    public void setTimePlaced(Date timePlaced) {
        this.timePlaced = timePlaced;
    }

    public CountryEnum getOriginatingCountry() {
        return originatingCountry;
    }

    public void setOriginatingCountry(CountryEnum originatingCountry) {
        this.originatingCountry = originatingCountry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TradeMessage{" +
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeMessage that = (TradeMessage) o;

        if (amountBuy != null ? !amountBuy.equals(that.amountBuy) : that.amountBuy != null) return false;
        if (amountSell != null ? !amountSell.equals(that.amountSell) : that.amountSell != null) return false;
        if (currencyFrom != that.currencyFrom) return false;
        if (currencyTo != that.currencyTo) return false;
        if (originatingCountry != that.originatingCountry) return false;
        if (rate != null ? !rate.equals(that.rate) : that.rate != null) return false;
        if (timePlaced != null ? !timePlaced.equals(that.timePlaced) : that.timePlaced != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (currencyFrom != null ? currencyFrom.hashCode() : 0);
        result = 31 * result + (currencyTo != null ? currencyTo.hashCode() : 0);
        result = 31 * result + (amountSell != null ? amountSell.hashCode() : 0);
        result = 31 * result + (amountBuy != null ? amountBuy.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + (timePlaced != null ? timePlaced.hashCode() : 0);
        result = 31 * result + (originatingCountry != null ? originatingCountry.hashCode() : 0);
        return result;
    }
}
