package com.example.cft.controller;


import java.util.List;
import java.util.Optional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cft.model.TypeProduct;
import com.example.cft.repository.RepositoryType;
import com.example.cft.utils.DiscriptionEncoder;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
@Api(description = "Функционал взаимодействия с типом товара",
        tags = {"Тип товаров"})
public class TypeController {
    @Autowired
    RepositoryType RepositoryType;

    @ApiOperation(value = "Просмотр всех типов")
    @GetMapping("/swagger/type")
    public ResponseEntity<List<TypeProduct>> getAllProductTypeApi() {
        try {
            List<TypeProduct> Products = RepositoryType.findAll();

            if (Products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(Products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(value = "Отображение информации о типе по идентификатору")
    @GetMapping("/swagger/type/{id}")
    public ResponseEntity<TypeProduct> getProductByIdApi(@PathVariable("id") long id) {
        Optional<TypeProduct> TestData = RepositoryType.findById(id);
        if (TestData.isPresent()) {
            return new ResponseEntity<>(TestData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @ApiOperation(value = "Создание нового типа с подкатегориями")
    @PostMapping("/swagger/type/variants")
    public ResponseEntity<TypeProduct> createTypeVariantsApi(String name_description, String name_type,
                                                    String variants) {
        DiscriptionEncoder temp = new DiscriptionEncoder(variants);
        String description_features = temp.setMassiveParam();
        try {
            TypeProduct _product = RepositoryType
                    .save(new TypeProduct(name_description,name_type,description_features));
            return new ResponseEntity<>(_product, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(value = "Создание нового типа с параметром определённого типа данных")
    @PostMapping("/swagger/type/type")
    public ResponseEntity<TypeProduct> createTypeApi(String name_description, String name_type,
                                                            String type) {
        DiscriptionEncoder temp = new DiscriptionEncoder(type);
        String description_features = temp.setValueParam();
        try {
            TypeProduct _product = RepositoryType
                    .save(new TypeProduct(name_description,name_type,description_features));
            return new ResponseEntity<>(_product, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(value = "Редактирование типа")
    @PutMapping("/swagger/type/{id}")
    public ResponseEntity<TypeProduct> updateProductApi(@PathVariable("id") long id, @RequestBody TypeProduct typeProdcut) {
        Optional<TypeProduct> TestData = RepositoryType.findById(id);

        if (TestData.isPresent()) {
            TypeProduct _typeProduct = TestData.get();
            _typeProduct.setId(typeProdcut.getId());
            _typeProduct.setNameType(typeProdcut.getNameType());
            _typeProduct.setDescriptionFeatures(typeProdcut.getDescriptionFeatures());

            return new ResponseEntity<>(RepositoryType.save(_typeProduct), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Удаление товара по идентификатору")
    @DeleteMapping("/swagger/type/{id}")
    public ResponseEntity<HttpStatus> deleteProductApi(@PathVariable("id") long id) {
        try {
            RepositoryType.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}