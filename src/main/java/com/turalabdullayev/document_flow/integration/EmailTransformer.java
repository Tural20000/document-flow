package com.turalabdullayev.document_flow.integration;

import org.springframework.integration.annotation.Transformer;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.turalabdullayev.document_flow.entity.Document;

@Component
public class EmailTransformer {
	@Transformer(inputChannel = "emailNotificationChannel", outputChannel = "smtpChannel")
	public SimpleMailMessage transformToMail(Document document) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(document.getApprover().getEmail());

		mail.setSubject("Tesdiq Gozleyen Sened: " + document.getTitle());
		mail.setText("Salam " + document.getApprover().getUsername() + ",\n\n" + document.getOwner().getUsername()
				+ " terefinden yeni bir sened yuklendi.\n" + "Sened adi: " + document.getTitle() + "\n"
				+ "Zehmet olmasa tesdiq ve ya imtina edin.");
		return mail;
	}

}
