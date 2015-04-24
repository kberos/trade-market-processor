package com.currencyfair.messageprocessor.model;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsVolumeMessageWrapper {
    private final String type;
    private static final String TYPE = "V";

    public AnalyticsVolumeMessageWrapper() {
        type = TYPE;
    }

    public String getType() {
        return type;
    }

    private List<AnalyticsVolumeMessage> volumes = new ArrayList<AnalyticsVolumeMessage>();

    public List<AnalyticsVolumeMessage> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<AnalyticsVolumeMessage> volumes) {
        this.volumes = volumes;
    }
}
