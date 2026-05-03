package com.turalabdullayev.document_flow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turalabdullayev.document_flow.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
	List<Document> findByStatus(String status);

}
