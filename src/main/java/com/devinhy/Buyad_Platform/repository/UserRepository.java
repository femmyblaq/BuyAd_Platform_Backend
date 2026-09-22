package com.devinhy.Buyad_Platform.repository;

import com.devinhy.Buyad_Platform.model.Role;
import com.devinhy.Buyad_Platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByRole(Role role);
}
