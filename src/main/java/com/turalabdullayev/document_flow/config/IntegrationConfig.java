package com.turalabdullayev.document_flow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class IntegrationConfig {
	@Bean
	public MessageChannel documentSubmissionChannel() {
		return new DirectChannel();
	}

	@Bean
	public MessageChannel emailNotificationChannel() {
		return new DirectChannel();

	}

	@Bean
	public MessageChannel statusUpdateChannel() {
		return new DirectChannel();
	}

}
