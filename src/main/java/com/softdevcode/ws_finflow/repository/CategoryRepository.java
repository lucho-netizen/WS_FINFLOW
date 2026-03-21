package com.softdevcode.ws_finflow.repository;

import com.softdevcode.ws_finflow.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByType(String type);
}