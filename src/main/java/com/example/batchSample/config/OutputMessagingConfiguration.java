package com.example.batchSample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

/**
 * Created by alokkulkarni on 30/09/16.
 */
//@EnableBinding(Source.class)
@Configuration
public class OutputMessagingConfiguration {

    @Bean
    public MessageChannel jobChannel() {
           return new DirectChannel();
//                   FixedSubscriberChannel(message -> System.out.println("from Output Messaging" + " " + message.getPayload().toString()));
    }
}
