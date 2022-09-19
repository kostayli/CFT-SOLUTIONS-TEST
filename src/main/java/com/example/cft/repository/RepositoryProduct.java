package com.example.cft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cft.model.Product;

import java.util.List;
import java.util.Optional;

public interface RepositoryProduct extends JpaRepository<Product, Long> {
    List<Product> findAllByIdType(long id);
}

