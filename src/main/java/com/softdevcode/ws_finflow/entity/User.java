package com.softdevcode.ws_finflow.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "users")
public class User {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String password;
    @Setter
    @Getter
    private String username;


}