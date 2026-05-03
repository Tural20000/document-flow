package com.turalabdullayev.document_flow.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.turalabdullayev.document_flow.entity.Document;
import com.turalabdullayev.document_flow.entity.DocumentStatus;
import com.turalabdullayev.document_flow.entity.User;
import com.turalabdullayev.document_flow.integration.DocumentGateway;
import com.turalabdullayev.document_flow.repository.DocumentRepository;
import com.turalabdullayev.document_flow.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {

	private final DocumentRepository documentRepository;
	private final UserRepository userRepository;
	private final DocumentGateway documentGateway;

	@Value("${app.upload.dir}")
	private String uploadDir;

	public Document uploadDocument(MultipartFile file, String title, String ownerUsername, String approverEmail)
			throws IOException {

		Path root = Paths.get(uploadDir);
		if (!Files.exists(root)) {
			Files.createDirectories(root);
		}

		String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		Path filePath = root.resolve(fileName);

		Files.copy(file.getInputStream(), filePath);

		User owner = userRepository.findByUsername(ownerUsername).orElseThrow();
		User approver = userRepository.findByEmail(approverEmail).orElseThrow();

		Document document = Document.builder().title(title).filePath(filePath.toString())
				.status(DocumentStatus.SUBMITTED).owner(owner).approver(approver).createdAt(LocalDateTime.now())
				.build();

		Document savedDoc = documentRepository.save(document);
		log.info("Sənəd bazaya yazıldı, ID: {}", savedDoc.getId());

		documentGateway.initiateWorkflow(savedDoc);

		return savedDoc;
	}
}