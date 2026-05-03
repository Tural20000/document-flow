package com.turalabdullayev.document_flow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turalabdullayev.document_flow.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
	List<AuditLog> findByDocumentId(Long documentId);

}
