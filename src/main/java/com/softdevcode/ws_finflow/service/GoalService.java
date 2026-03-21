package com.softdevcode.ws_finflow.service;

import com.softdevcode.ws_finflow.dto.ContributionRequest;
import com.softdevcode.ws_finflow.dto.GoalRequest;
import com.softdevcode.ws_finflow.entity.Goal;
import com.softdevcode.ws_finflow.entity.GoalContribution;
import com.softdevcode.ws_finflow.repository.GoalContributionRepository;
import com.softdevcode.ws_finflow.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository             repository;
    private final GoalContributionRepository contributionRepository;

    public List<Goal> getAll(Long userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Goal getById(Long userId, Long id) {
        return repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meta no encontrada"));
    }

    @Transactional
    public Goal create(Long userId, GoalRequest request) {
        Goal goal = new Goal();
        goal.setUserId(userId);
        goal.setTitle(request.getTitle());
        goal.setTargetAmount(request.getTargetAmount());
        goal.setCurrentAmount(request.getCurrentAmount() != null ? request.getCurrentAmount() : 0.0);
        goal.setDeadline(request.getDeadline());
        goal.setDescription(request.getDescription());
        goal.setStatus("active");
        goal.setCreatedAt(LocalDateTime.now());
        return repository.save(goal);
    }

    @Transactional
    public Goal update(Long userId, Long id, GoalRequest request) {
        Goal goal = getById(userId, id);
        if (request.getTitle()         != null) goal.setTitle(request.getTitle());
        if (request.getTargetAmount()  != null) goal.setTargetAmount(request.getTargetAmount());
        if (request.getCurrentAmount() != null) goal.setCurrentAmount(request.getCurrentAmount());
        if (request.getDeadline()      != null) goal.setDeadline(request.getDeadline());
        if (request.getDescription()   != null) goal.setDescription(request.getDescription());
        if (request.getStatus()        != null) goal.setStatus(request.getStatus());
        return repository.save(goal);
    }

    @Transactional
    public Goal addContribution(Long userId, Long id, ContributionRequest request) {
        Goal goal = getById(userId, id);

        double newAmount = goal.getCurrentAmount() + request.getAmount();
        goal.setCurrentAmount(Math.min(newAmount, goal.getTargetAmount()));

        if (goal.getCurrentAmount() >= goal.getTargetAmount()) {
            goal.setStatus("completed");
        }

        GoalContribution contribution = new GoalContribution();
        contribution.setGoalId(id);
        contribution.setUserId(userId);
        contribution.setAmount(request.getAmount());
        contribution.setNote(request.getNote());
        contribution.setCreatedAt(LocalDateTime.now());
        contributionRepository.save(contribution);

        return repository.save(goal);
    }

    public List<GoalContribution> getContributions(Long userId, Long goalId) {
        getById(userId, goalId);
        return contributionRepository.findByGoalIdOrderByCreatedAtDesc(goalId);
    }

    @Transactional
    public Goal updateStatus(Long userId, Long id, String status) {
        Goal goal = getById(userId, id);
        goal.setStatus(status);
        return repository.save(goal);
    }

    @Transactional
    public void delete(Long userId, Long id) {
        Goal goal = getById(userId, id);
        repository.delete(goal);
    }
}