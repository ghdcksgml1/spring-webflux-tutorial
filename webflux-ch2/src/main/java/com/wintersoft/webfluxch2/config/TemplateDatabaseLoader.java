package com.wintersoft.webfluxch2.config;

import com.wintersoft.webfluxch2.item.Item;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

//@Component
//public class TemplateDatabaseLoader {
//
//    @Bean
//    CommandLineRunner initialize(MongoTemplate mongo) {
//        return args -> {
//            mongo.save(new Item("Alf alarm clock", 19.99));
//            mongo.save(new Item("Smurf TV tray", 24.99));
//        };
//    }
//}
