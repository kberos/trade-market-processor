package com.currencyfair.messageprocessor.model;


public class AnalyticsRateMessage {
    private final String type;
    private String pair;
    private Double rate;

    private static final String TYPE = "R";

    public AnalyticsRateMessage() {
        type = TYPE;
    }

    public String getType() {
        return type;
    }


    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }
}
