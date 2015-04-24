package com.currencyfair.messageprocessor;

import com.currencyfair.messageprocessor.model.api.TradeMessageResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

/*
Handles the exceptions that are thrown and constructs the corresponding response with the errors.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidRequestException.class, TypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected TradeMessageResponse handleInvalidRequest(InvalidRequestException e, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        TradeMessageResponse registrationResponse = new TradeMessageResponse();
        registrationResponse.setStatus(TradeMessageResponse.Status.INVALID_REQUEST);
        registrationResponse.setErrors(populateResponseErrors(e.getErrors()));
        return registrationResponse;
    }

    private static List<String[]> populateResponseErrors(Errors errors) {
        List responseErrors = new ArrayList<String[]>();
        for (FieldError error : errors.getFieldErrors()) {
            String[] errorsPair = {error.getField(), error.getDefaultMessage()};
            responseErrors.add(errorsPair);
        }
        return responseErrors;
    }

    @ExceptionHandler({IllegalStateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    protected TradeMessageResponse handleIllegalStateException(RuntimeException e, WebRequest request) {
        final String errorMsg = String.format("(ERROR) 500 - RegistrationHandler error occurred processing: %s", e.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        TradeMessageResponse tradeMessageResponse = new TradeMessageResponse();
        tradeMessageResponse.setStatus(TradeMessageResponse.Status.INTERNAL_SERVER_ERROR);
        return tradeMessageResponse;
    }
}