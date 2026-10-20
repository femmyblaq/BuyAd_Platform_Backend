package com.devinhy.Buyad_Platform.repository;

import com.devinhy.Buyad_Platform.model.EmailVerificationToken;
import com.devinhy.Buyad_Platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
    Optional<EmailVerificationToken> findByToken(String token);

    void deleteByToken(String token);

    void deleteByUser(User user);
}
