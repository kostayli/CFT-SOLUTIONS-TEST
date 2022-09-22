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
import com.example.cft.repository.IRepositoryType;
import com.example.cft.utils.DiscriptionEncoder;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
@Api(description = "Функционал взаимодействия с типом товара",
        tags = {"Тип товаров"})
public class TypeController {
    @Autowired
    IRepositoryType IRepositoryType;

    @ApiOperation(value = "Просмотр всех типов")
    @GetMapping("/swagger/type")
    public ResponseEntity<List<TypeProduct>> getAllProductTypeApi() {
        try {
            List<TypeProduct> Products = IRepositoryType.findAll();

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
        Optional<TypeProduct> TestData = IRepositoryType.findById(id);
        if (TestData.isPresent()) {
            return new ResponseEntity<>(TestData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @ApiOperation(value = "Создание нового типа с подкатегориями")
    @PostMapping("/swagger/type/variants")
    public ResponseEntity<TypeProduct> createTypeVariantsApi(String nameDescription, String nameType,
                                                    String variants) {
        DiscriptionEncoder temp = new DiscriptionEncoder(variants);
        String descriptionFeatures = temp.getMassiveQuery();
        try {
            TypeProduct _product = IRepositoryType
                    .save(new TypeProduct(nameDescription,nameType,descriptionFeatures));
            return new ResponseEntity<>(_product, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(value = "Создание нового типа с параметром определённого типа данных")
    @PostMapping("/swagger/type/type")
    public ResponseEntity<TypeProduct> createTypeApi(String nameDescription, String nameType,
                                                            String type) {
        DiscriptionEncoder temp = new DiscriptionEncoder(type);
        String descriptionFeatures = temp.getTypeQuery();
        try {
            TypeProduct _product = IRepositoryType
                    .save(new TypeProduct(nameDescription,nameType,descriptionFeatures));
            return new ResponseEntity<>(_product, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(value = "Редактирование типа")
    @PutMapping("/swagger/type/{id}")
    public ResponseEntity<TypeProduct> updateProductApi(@PathVariable("id") long id, @RequestBody TypeProduct typeProdcut) {
        Optional<TypeProduct> testData = IRepositoryType.findById(id);
        if (testData.isPresent()) {
            TypeProduct _typeProduct = testData.get();
            _typeProduct.setId(typeProdcut.getId());
            _typeProduct.setNameType(typeProdcut.getNameType());
            _typeProduct.setDescriptionFeatures(typeProdcut.getDescriptionFeatures());
            return new ResponseEntity<>(IRepositoryType.save(_typeProduct), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Удаление типа по идентификатору")
    @DeleteMapping("/swagger/type/{id}")
    public ResponseEntity<HttpStatus> deleteProductApi(@PathVariable("id") long id) {
        try {
            IRepositoryType.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}