package com.currencyfair.messageprocessor.model.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kber0001 on 18/04/2015.
 */
public class TradeMessageResponse {
    public enum Status {
        SUCCESS,
        INVALID_REQUEST,
        INTERNAL_SERVER_ERROR
    }
    private Status status;                // [REQ]            registration status
    private List<String[]> errors = new ArrayList<String[]>();

    public Status getStatus() {
        return status;
    }

    public List<String[]> getErrors() {
        return errors;
    }

    public void setErrors(List<String[]> errors) {
        this.errors = errors;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
