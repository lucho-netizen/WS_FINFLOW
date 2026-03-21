package com.softdevcode.ws_finflow.controller;

import com.softdevcode.ws_finflow.dto.LoginRequest;
import com.softdevcode.ws_finflow.entity.User;
import com.softdevcode.ws_finflow.repository.UserRepository;
import com.softdevcode.ws_finflow.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Usuario no existe"));
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Contraseña incorrecta"));
        }

        // Generar token
        String token = jwtUtil.generateToken(user.getEmail());

        // 🔥 RESPUESTA CORRECTA (alineada con Angular)
        return ResponseEntity.ok(Map.of(
                "token", token,
                "user", Map.of(
                        "id", user.getId(),
                        "name", user.getUsername(), // 👈 aquí mapeas username → name
                        "email", user.getEmail()
                )
        ));
    }
}