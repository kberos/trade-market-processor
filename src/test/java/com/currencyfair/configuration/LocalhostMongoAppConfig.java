package com.currencyfair.configuration;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;

@Configuration
 @Profile("test")
 public class LocalhostMongoAppConfig {

    private static final String DATABASE_NAME = "processor";

    public @Bean
    Mongo mongo() throws UnknownHostException, MongoException {
        Mongo mongo = new Mongo("localhost");
        return mongo;
    }

    public @Bean
    MongoTemplate mongoTemplate() throws UnknownHostException, MongoException {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo(), DATABASE_NAME);
        return mongoTemplate;
    }

}
