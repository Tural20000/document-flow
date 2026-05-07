package com.turalabdullayev.document_flow.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import com.turalabdullayev.document_flow.entity.Document;

@MessagingGateway
public interface DocumentGateway {
	@Gateway(requestChannel = "documentSubmissionChannel")
	void initiateWorkflow(Document document);

	@Gateway(requestChannel = "statusUpdateChannel")
	void updateStatus(Document document);

}
