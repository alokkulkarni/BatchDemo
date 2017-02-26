package com.example.batchSample;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * Created by alokkulkarni on 24/02/17.
 */

@Component
public class ServiceComponent {

    @ServiceActivator(inputChannel = "jobChannel")
    public void getMessage(Message<String> message) {
        System.out.println("from Service Activator: - " + message.getPayload().toString() + " " + message.getHeaders().get("jobName") + " " + message.getHeaders().get("serviceName"));
    }
}
