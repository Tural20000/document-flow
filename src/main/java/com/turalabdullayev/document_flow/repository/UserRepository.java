package com.turalabdullayev.document_flow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turalabdullayev.document_flow.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

}
