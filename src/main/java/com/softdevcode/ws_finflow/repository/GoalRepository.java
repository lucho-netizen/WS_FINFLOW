package com.softdevcode.ws_finflow.repository;

import com.softdevcode.ws_finflow.entity.Goal;
import com.softdevcode.ws_finflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByUser(User user);
}