package com.currencyfair.messageprocessor.model;

public class AnalyticsVolumeMessage {

    private String id;
    private Double volume;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }
}
