package com.currencyfair.messageprocessor.controller;

import com.currencyfair.messageprocessor.MVCTestUtil;
import com.currencyfair.messageprocessor.configuration.WebAppContext;
import com.currencyfair.messageprocessor.model.CountryEnum;
import com.currencyfair.messageprocessor.model.CurrencyEnum;
import com.currencyfair.messageprocessor.model.api.TradeMessageRequest;
import com.currencyfair.messageprocessor.model.api.TradeMessageResponse;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={WebAppContext.class})
public class ProcessorControllerTest extends TestCase {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;


    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void postRegistrationSuccess() throws Exception {

        TradeMessageRequest tradeMessageRequest = new TradeMessageRequest();
        tradeMessageRequest.setCurrencyTo(CurrencyEnum.GBP);
        tradeMessageRequest.setOriginatingCountry(CountryEnum.GB);
        tradeMessageRequest.setRate(0.56);
        tradeMessageRequest.setCurrencyFrom(CurrencyEnum.EUR);
        tradeMessageRequest.setAmountBuy(350.0);
        tradeMessageRequest.setAmountSell(200.0);
        tradeMessageRequest.setUserId("123");
        tradeMessageRequest.setTimePlaced(new Date());
        this.mockMvc.perform(post("/processor/message").contentType(MediaType.APPLICATION_JSON).content(MVCTestUtil.convertObjectToJsonBytes(tradeMessageRequest)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }


    @Test
    public void postRegistrationFailureUserIdMissing() throws Exception {

        TradeMessageRequest tradeMessageRequest = new TradeMessageRequest();
        tradeMessageRequest.setCurrencyTo(CurrencyEnum.GBP);
        tradeMessageRequest.setOriginatingCountry(CountryEnum.GB);
        tradeMessageRequest.setRate(0.56);
        tradeMessageRequest.setCurrencyFrom(CurrencyEnum.EUR);
        tradeMessageRequest.setAmountBuy(350.0);
        tradeMessageRequest.setAmountSell(200.0);
        tradeMessageRequest.setTimePlaced(new Date());
        this.mockMvc.perform(post("/processor/message").contentType(MediaType.APPLICATION_JSON).content(MVCTestUtil.convertObjectToJsonBytes(tradeMessageRequest)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("INVALID_REQUEST"));
    }
    //TODO add more invalid tests. Validate more the responses.
}