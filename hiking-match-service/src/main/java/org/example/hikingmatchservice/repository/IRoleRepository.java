package org.example.hikingmatchservice.repository;

import org.example.hikingmatchservice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
