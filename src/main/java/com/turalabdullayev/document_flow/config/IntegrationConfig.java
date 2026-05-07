package com.turalabdullayev.document_flow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.handler.advice.RequestHandlerRetryAdvice;
import org.springframework.integration.mail.MailSendingMessageHandler;
import org.springframework.mail.javamail.JavaMailSender;
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

	@Bean
	public MessageChannel smtpChannel() {
		return new DirectChannel();
	}

	@Bean
	@ServiceActivator(inputChannel = "smtpChannel", adviceChain = "retryAdvice")
	public MailSendingMessageHandler mailSendingMessageHandler(JavaMailSender mailSender) {
		return new MailSendingMessageHandler(mailSender);

	}

	@Bean
	public RequestHandlerRetryAdvice retryAdvice() {
		RequestHandlerRetryAdvice advice = new RequestHandlerRetryAdvice();
		org.springframework.retry.support.RetryTemplate retryTemplate = new org.springframework.retry.support.RetryTemplate();
		org.springframework.retry.policy.SimpleRetryPolicy retryPolicy = new org.springframework.retry.policy.SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(3);
		retryTemplate.setRetryPolicy(retryPolicy);
		advice.setRetryTemplate(retryTemplate);
		return advice;
	}

}
