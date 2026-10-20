
package com.devinhy.Buyad_Platform.repository;

import com.devinhy.Buyad_Platform.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScreenRepository
        extends JpaRepository<Screen, Long> {

    List<Screen> findByUserId(Long userId);
    Optional<Screen> findByIdAndUserId(
            Long screenId,
            Long userId
    );

    List<Screen> findByAvailableTrue();
}
