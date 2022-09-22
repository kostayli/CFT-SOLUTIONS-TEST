package com.example.cft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cft.model.Product;

import java.util.List;

public interface IRepositoryProduct extends JpaRepository<Product, Long> {
    List<Product> findAllByIdType(long id);
}

