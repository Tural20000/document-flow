package com.turalabdullayev.document_flow.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.turalabdullayev.document_flow.entity.Document;
import com.turalabdullayev.document_flow.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {
	private final DocumentService documentService;

	@PostMapping("/upload")
	public ResponseEntity<Document> upload(@RequestParam("file") MultipartFile file,
			@RequestParam("title") String title, @RequestParam("approverEamil") String approverEmail,
			Authentication authentication) throws IOException {

		String username = authentication.getName();
		Document doc = documentService.uploadDocument(file, title, username, approverEmail);

		return ResponseEntity.ok(doc);

	}

	@PostMapping("/{id}/decide")
	public ResponseEntity<Document> decide(@PathVariable Long id, @RequestParam boolean approved,
			Authentication authentication) {

		String approverUsername = authentication.getName();
		Document updatedDoc = documentService.processDecision(id, approved, approverUsername);
		return ResponseEntity.ok(updatedDoc);
	}

}
