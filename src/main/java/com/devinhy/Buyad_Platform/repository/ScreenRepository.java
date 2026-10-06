package com.devinhy.Buyad_Platform.repository;

import com.devinhy.Buyad_Platform.model.Screen;
import com.devinhy.Buyad_Platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreenRepository extends JpaRepository<Screen, Long> {
    List<Screen> findByUser(User user);
    List<Screen> findByAvailableTrue();

}
