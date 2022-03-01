package com.KAI.meetWildBackend.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.KAI.meetWildBackend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String nusername);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
}
