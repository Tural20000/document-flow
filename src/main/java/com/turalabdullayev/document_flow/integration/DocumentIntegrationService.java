package com.turalabdullayev.document_flow.integration;

import java.time.LocalDateTime;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.turalabdullayev.document_flow.entity.AuditLog;
import com.turalabdullayev.document_flow.entity.Document;
import com.turalabdullayev.document_flow.repository.AuditLogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentIntegrationService {
	private final AuditLogRepository auditLogRepository;

	@ServiceActivator(inputChannel = "documentSubmissionChannel", outputChannel = "emailNotificationChannel")
	public Message<Document> processInitialSubmission(Message<Document> message) {
		Document doc = message.getPayload();

		log.info("Yeni sened daxil oldu: {}. Audit log yazilir....", doc.getTitle());
		AuditLog logEntry = AuditLog.builder().documentId(doc.getId()).action("SUBMITTED")
				.details("Sened sisteme yuklendi ve workflow basladi.").performedBy(doc.getOwner().getUsername())
				.timestamp(LocalDateTime.now()).build();

		auditLogRepository.save(logEntry);

		return message;
	}

	@ServiceActivator(inputChannel = "statusUpdateChannel")
	public void processStatusUpdate(Message<Document> message) {
		Document doc = message.getPayload();
		log.info("Sened statusu deyisdi: ID {}, Yeni Status: {}", doc.getId(), doc.getStatus());

		AuditLog logEntry = AuditLog.builder().documentId(doc.getId()).action(doc.getStatus().name())
				.details("Sened statusu " + doc.getStatus().name() + " olaraq guncellendi.")
				.performedBy("SYSTEM_INTEGRATION").timestamp(LocalDateTime.now()).build();
		auditLogRepository.save(logEntry);
	}

}
