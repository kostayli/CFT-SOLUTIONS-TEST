package com.example.cft.repository;

import com.example.cft.model.TypeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositoryType extends JpaRepository<TypeProduct, Long> {


    Optional<TypeProduct> findByNameType(String nameType);
}
