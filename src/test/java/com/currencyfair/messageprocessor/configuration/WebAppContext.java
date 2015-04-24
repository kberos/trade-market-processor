package com.currencyfair.messageprocessor.configuration;

import com.currencyfair.messageprocessor.service.ProcessorService;
import com.currencyfair.messageprocessor.service.TradeMessageProcessor;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan({"com.currencyfair.messageprocessor"})

public class WebAppContext extends WebMvcConfigurerAdapter {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public ProcessorService processorService() {
        return Mockito.mock(ProcessorService.class);
    }

    @Bean
    LocalValidatorFactoryBean validator(){
        return new LocalValidatorFactoryBean();
    }

}