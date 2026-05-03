package com.turalabdullayev.document_flow.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String filePath;
	@Enumerated(EnumType.STRING)
	private DocumentStatus status;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User owner;
	@ManyToOne
	@JoinColumn(name = "approver_id")
	private User approver;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
