package com.example.spring6restmvc.repository;

import com.example.spring6restmvc.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
