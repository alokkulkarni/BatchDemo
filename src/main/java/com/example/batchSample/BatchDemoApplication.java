package com.example.batchSample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.config.EnableIntegrationManagement;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.UnknownHostException;

@EnableIntegration
@EnableIntegrationManagement
@EnableJpaRepositories
@EnableJpaAuditing
@EnableTransactionManagement
@EnableScheduling
@EnableBatchProcessing(modular = true)
@SpringBootApplication
public class BatchDemoApplication {

	private static final Logger log = LoggerFactory.getLogger(BatchDemoApplication.class);

	private static Environment env;

	public BatchDemoApplication(Environment env) {
		this.env = env;
	}

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication.run(BatchDemoApplication.class, args);
		log.info("Application Started successfully :- " + env.getProperty("host") + ":" + env.getProperty("port"));
	}
}
