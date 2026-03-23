package org.example.hikingmatchservice.repository;

import org.example.hikingmatchservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String user);
}
