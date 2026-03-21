package com.softdevcode.ws_finflow.controller;

import com.softdevcode.ws_finflow.dto.GoalRequest;
import com.softdevcode.ws_finflow.dto.GoalResponse;
import com.softdevcode.ws_finflow.entity.Goal;
import com.softdevcode.ws_finflow.entity.User;
import com.softdevcode.ws_finflow.repository.GoalRepository;
import com.softdevcode.ws_finflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/goals")
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @GetMapping
    public List<GoalResponse> listGoals() {
        User user = getCurrentUser();
        return goalRepository.findByUser(user)
                .stream()
                .map(g -> new GoalResponse(g.getId(), g.getTitle(), g.getTargetAmount(), g.getCurrentAmount(), g.getDeadline(), g.getDescription()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public GoalResponse createGoal(@RequestBody GoalRequest request) {
        User user = getCurrentUser();

        Goal goal = new Goal();
        goal.setUser(user);
        goal.setTitle(request.getTitle());
        goal.setTargetAmount(request.getTargetAmount());
        goal.setCurrentAmount(request.getCurrentAmount() != null ? request.getCurrentAmount() : 0);
        goal.setDeadline(request.getDeadline());
        goal.setDescription(request.getDescription());

        Goal saved = goalRepository.save(goal);

        return new GoalResponse(saved.getId(), saved.getTitle(), saved.getTargetAmount(), saved.getCurrentAmount(), saved.getDeadline(), saved.getDescription());
    }


}