package com.KAI.meetWildBackend.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.KAI.meetWildBackend.entity.ERole;
import com.KAI.meetWildBackend.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(ERole name);
	
}
