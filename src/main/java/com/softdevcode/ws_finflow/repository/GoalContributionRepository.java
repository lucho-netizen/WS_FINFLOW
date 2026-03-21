package com.softdevcode.ws_finflow.repository;

import com.softdevcode.ws_finflow.entity.GoalContribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalContributionRepository extends JpaRepository<GoalContribution, Long> {

    List<GoalContribution> findByGoalIdOrderByCreatedAtDesc(Long goalId);
}