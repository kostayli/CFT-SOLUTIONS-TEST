package com.example.cft.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.example.cft.model.TypeProduct;
import com.example.cft.repository.RepositoryType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cft.model.Product;
import com.example.cft.repository.RepositoryProduct;
import com.example.cft.utils.DiscriptionEncoder;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
@Validated
@Api(description = "Функционал удаления, добавления, редактирвоания и получения продуктов",
        tags = {"Товары"})

public class ProductController {

    @Autowired
    RepositoryProduct RepositoryProduct;
    @Autowired
    RepositoryType RepositoryType;


    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProduct(@RequestParam(required = false) String text) {
        try {
            List<Product> Products = RepositoryProduct.findAll();
            if (Products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(Products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(value = "Просмотр всех товаров")
    @GetMapping("/swagger/product")
    public ResponseEntity<List<Product>> getAllProductApi() {
        try {
            List<Product> Products = RepositoryProduct.findAll();

            if (Products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(Products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}")

    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {

        Optional<Product> TestData = RepositoryProduct.findById(id);
        if (TestData.isPresent()) {
            return new ResponseEntity<>(TestData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Просмотр товара по идентификатору")
    @GetMapping("/swagger/product/{id}")
    public ResponseEntity<Product> getProductByIdApi(@PathVariable("id") long id)
    {
        Optional<Product> TestData = RepositoryProduct.findById(id);
        if (TestData.isPresent()) {
            return new ResponseEntity<>(TestData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @ApiOperation(value = "Просмотр всех существующих товаров по типу")
    @GetMapping("/swagger/product/type/{type}")
    public ResponseEntity<List<Product>> getProductByTypeApi(@PathVariable("type") String type) {

        Optional<TypeProduct> TestData = RepositoryType.findByNameType(type);
        TypeProduct _type = TestData.get();

        List<Product> Products = RepositoryProduct.findAllByIdType(_type.getId());
        if (TestData.isPresent()) {
            return new ResponseEntity<>(Products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = {"/product/", "/product"})
    public ResponseEntity<Product> createProduct(@RequestParam Map<String,String> allRequestParam) {
        Optional<TypeProduct> TestData = RepositoryType.findByNameType(allRequestParam.get("nametype"));
        TypeProduct _type = TestData.get();
        try {
            Product _product = RepositoryProduct
                    .save(new Product(_type.getId(), allRequestParam.get("manufacturer"),
                            Integer.parseInt(allRequestParam.get("cost")),
                            Integer.parseInt(allRequestParam.get("countstock")),
                            allRequestParam.get("additiondescription")));
            return new ResponseEntity<>(_product, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Добавление нового товара")
    @PostMapping(value = {"swagger/product"})
    public ResponseEntity<Product> createProductApi(String nametype, String manufacturer, float cost,
                                                    int countstock, String additiondescription) {
        Optional<TypeProduct> TestData = RepositoryType.findByNameType(nametype);
        TypeProduct _type = TestData.get();
        String checkText=_type.getDescriptionFeatures().replaceAll("\\s+","");
        try {
            if(checkText.contains("variants")){
                DiscriptionEncoder descriptionEncoder = new DiscriptionEncoder(_type.getDescriptionFeatures());
                String[] text = descriptionEncoder.splitParam();
                String param = descriptionEncoder.getValueParam(text[0]);
                String [] massiveParam = descriptionEncoder.getMassiveParam(param);
                for(int i=0;i<massiveParam.length;i++){
                    if((additiondescription.contains(massiveParam[i])) && (additiondescription.length()==massiveParam[i].length())){
                        Product _product = RepositoryProduct
                                .save(new Product(_type.getId(), manufacturer, cost, countstock, additiondescription));
                        return new ResponseEntity<>(_product, HttpStatus.CREATED);
                    }
                    else continue;
                }
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else if(checkText.contains("type")){
                DiscriptionEncoder descriptionEncoder = new DiscriptionEncoder(_type.getDescriptionFeatures());
                String[] text = descriptionEncoder.splitParam();
                String param = descriptionEncoder.getValueParam(text[0]);
                if(param.contains("float")){
                    try{
                        float number;
                        number = Float.parseFloat(additiondescription);
                        Product _product = RepositoryProduct
                                .save(new Product(_type.getId(), manufacturer, cost, countstock, additiondescription));
                        return new ResponseEntity<>(_product, HttpStatus.CREATED);
                    }catch (Exception e){
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
                else if(param.contains("integer")||param.contains("int")){
                    try{
                        int number;
                        number = Integer.parseInt(additiondescription);
                        Product _product = RepositoryProduct
                                .save(new Product(_type.getId(), manufacturer, cost, countstock, additiondescription));
                        return new ResponseEntity<>(_product, HttpStatus.CREATED);
                    }catch (Exception e){
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                }
                else if(param.contains("string")){
                    Product _product = RepositoryProduct
                            .save(new Product(_type.getId(), manufacturer, cost, countstock, additiondescription));
                    return new ResponseEntity<>(_product, HttpStatus.CREATED);
                }
                else{
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            else{
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
        Optional<Product> TestData = RepositoryProduct.findById(id);
        if (TestData.isPresent()) {
            Product _product = TestData.get();
            _product.setIdType(product.getIdType());
            _product.setCost(product.getCost());
            _product.setCountStock(product.getCountStock());
            _product.setManufacturer(product.getManufacturer());
            _product.setAdditionDescription(product.getAdditionDescription());
            return new ResponseEntity<>(RepositoryProduct.save(_product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @ApiOperation(value = "Редактирование товара")
    @PutMapping("/swagger/product/{id}")
    public ResponseEntity<Product> updateProductApi(@PathVariable("id") long id, @RequestBody Product product) {
        Optional<Product> TestData = RepositoryProduct.findById(id);

        if (TestData.isPresent()) {
            Product _product = TestData.get();
            _product.setIdType(product.getIdType());
            _product.setCost(product.getCost());
            _product.setCountStock(product.getCountStock());
            _product.setManufacturer(product.getManufacturer());
            _product.setAdditionDescription(product.getAdditionDescription());
            return new ResponseEntity<>(RepositoryProduct.save(_product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
        try {
            RepositoryProduct.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(value = "Удаление товара по идентификатору")
    @DeleteMapping("/swagger/product/{id}")
    public ResponseEntity<HttpStatus> deleteProductApi(@PathVariable("id") long id) {
        try {
            RepositoryProduct.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product")
    public ResponseEntity<HttpStatus> deleteAllProduct() {
        try {
            RepositoryProduct.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(value = "Удаление всех товаров")
    @DeleteMapping("/swagger/product")
    public ResponseEntity<HttpStatus> deleteAllProductApi() {
        try {
            RepositoryProduct.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}