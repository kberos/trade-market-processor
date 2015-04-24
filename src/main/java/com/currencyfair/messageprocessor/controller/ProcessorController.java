package com.currencyfair.messageprocessor.controller;

import com.currencyfair.messageprocessor.InvalidRequestException;
import com.currencyfair.messageprocessor.model.TradeMessage;
import com.currencyfair.messageprocessor.model.api.TradeMessageRequest;
import com.currencyfair.messageprocessor.model.api.TradeMessageResponse;
import com.currencyfair.messageprocessor.service.ProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/processor")
public class ProcessorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorController.class);

    @Autowired
    ProcessorService processorService;

    @ResponseBody
    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public TradeMessageResponse sendNotification(@RequestBody @Valid TradeMessageRequest request, BindingResult bindingResult) {
        LOGGER.debug("Received notification delivery request: {}", request);

        validateRequestThrowing(request, bindingResult);

        processorService.addMessageToQueue(convertRequestToInternalObject(request));

        LOGGER.debug("Serviced tradeMessage delivery request: {}", request);
        TradeMessageResponse tradeMessageResponse = new TradeMessageResponse();
        tradeMessageResponse.setStatus(TradeMessageResponse.Status.SUCCESS);

        return tradeMessageResponse;
    }

    private void validateRequestThrowing(final TradeMessageRequest messageRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new InvalidRequestException("Invalid notification request data", bindingResult);
    }


    private static TradeMessage convertRequestToInternalObject(TradeMessageRequest tradeMessageRequest) {
        TradeMessage tradeMessage = new TradeMessage();
        tradeMessage.setUserId(tradeMessageRequest.getUserId());
        tradeMessage.setAmountBuy(tradeMessageRequest.getAmountBuy());
        tradeMessage.setAmountSell(tradeMessageRequest.getAmountSell());
        tradeMessage.setCurrencyFrom(tradeMessageRequest.getCurrencyFrom());
        tradeMessage.setCurrencyTo(tradeMessageRequest.getCurrencyTo());
        tradeMessage.setOriginatingCountry(tradeMessageRequest.getOriginatingCountry());
        tradeMessage.setRate(tradeMessageRequest.getRate());
        tradeMessage.setTimePlaced(tradeMessageRequest.getTimePlaced());
        return tradeMessage;
    }
}
