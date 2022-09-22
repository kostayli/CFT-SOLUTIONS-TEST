package com.example.cft.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.example.cft.model.TypeProduct;
import com.example.cft.repository.IRepositoryType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.example.cft.repository.IRepositoryProduct;
import com.example.cft.utils.DiscriptionEncoder;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
@Validated
@Api(description = "Функционал удаления, добавления, редактирвоания и получения продуктов",
        tags = {"Товары"})

public class ProductController {

    @Autowired
    public IRepositoryProduct IRepositoryProduct;
    @Autowired
    public IRepositoryType IRepositoryType;


    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProduct() {
        try {
            List<Product> products = IRepositoryProduct.findAll();
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(value = "Просмотр всех товаров")
    @GetMapping("/swagger/product")
    public ResponseEntity<List<Product>> getAllProductApi() {
        try {
            List<Product> products = IRepositoryProduct.findAll();

            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}")

    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {

        Optional<Product> testData = IRepositoryProduct.findById(id);
        if (testData.isPresent()) {
            return new ResponseEntity<>(testData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Просмотр товара по идентификатору")
    @GetMapping("/swagger/product/{id}")
    public ResponseEntity<Product> getProductByIdApi(@PathVariable("id") long id)
    {
        Optional<Product> testData = IRepositoryProduct.findById(id);
        if (testData.isPresent()) {
            return new ResponseEntity<>(testData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @ApiOperation(value = "Просмотр всех существующих товаров по типу")
    @GetMapping("/swagger/product/type/{type}")
    public ResponseEntity<List<Product>> getProductByTypeApi(@PathVariable("type") String type) {

        Optional<TypeProduct> testData = IRepositoryType.findByNameType(type);
        TypeProduct resultType = testData.get();
        List<Product> products = IRepositoryProduct.findAllByIdType(resultType.getId());
        if (testData.isPresent()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = {"/product/", "/product"})
    public ResponseEntity<Product> createProduct(@RequestParam Map<String,String> allRequestParam) {
        Optional<TypeProduct> testData = IRepositoryType.findByNameType(allRequestParam.get("nametype"));
        TypeProduct resultType = testData.get();
        try {
            Product _product = IRepositoryProduct
                    .save(new Product(resultType.getId(), allRequestParam.get("manufacturer"),
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
    public ResponseEntity<Product> createProductApi(String nameType, String manufacturer, float cost,
                                                    int countStock, String additionDescription) {
        Optional<TypeProduct> testData = IRepositoryType.findByNameType(nameType);
        TypeProduct resultType = testData.get();
        String checkText=resultType.getDescriptionFeatures().replaceAll("\\s+","");
        try {
            if(checkText.contains("variants")){
                DiscriptionEncoder descriptionEncoder = new DiscriptionEncoder(resultType.getDescriptionFeatures());
                String[] text = descriptionEncoder.splitParam();
                String param = descriptionEncoder.getValueParam(text[0]);
                String [] massiveParam = descriptionEncoder.getMassiveParam(param);
                for(int i=0;i<massiveParam.length;i++){
                    if((additionDescription.contains(massiveParam[i])) && (additionDescription.length()==massiveParam[i].length())){
                        Product _product = IRepositoryProduct
                                .save(new Product(resultType.getId(), manufacturer, cost, countStock, additionDescription));
                        return new ResponseEntity<>(_product, HttpStatus.CREATED);
                    }
                    else continue;
                }
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else if(checkText.contains("type")){
                DiscriptionEncoder descriptionEncoder = new DiscriptionEncoder(resultType.getDescriptionFeatures());
                String[] text = descriptionEncoder.splitParam();
                String param = descriptionEncoder.getValueParam(text[0]);
                if(param.contains("float")){
                    try{
                        float number;
                        number = Float.parseFloat(additionDescription);
                        Product _product = IRepositoryProduct
                                .save(new Product(resultType.getId(), manufacturer, cost, countStock, additionDescription));
                        return new ResponseEntity<>(_product, HttpStatus.CREATED);
                    }catch (Exception e){
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
                else if(param.contains("integer")||param.contains("int")){
                    try{
                        int number;
                        number = Integer.parseInt(additionDescription);
                        Product _product = IRepositoryProduct
                                .save(new Product(resultType.getId(), manufacturer, cost, countStock, additionDescription));
                        return new ResponseEntity<>(_product, HttpStatus.CREATED);
                    }catch (Exception e){
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                }
                else if(param.contains("string")){
                    Product resultProduct = IRepositoryProduct
                            .save(new Product(resultType.getId(), manufacturer, cost, countStock, additionDescription));
                    return new ResponseEntity<>(resultProduct, HttpStatus.CREATED);
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
        Optional<Product> TestData = IRepositoryProduct.findById(id);
        if (TestData.isPresent()) {
            Product _product = TestData.get();
            _product.setIdType(product.getIdType());
            _product.setCost(product.getCost());
            _product.setCountStock(product.getCountStock());
            _product.setManufacturer(product.getManufacturer());
            _product.setAdditionDescription(product.getAdditionDescription());
            return new ResponseEntity<>(IRepositoryProduct.save(_product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @ApiOperation(value = "Редактирование товара")
    @PutMapping("/swagger/product/{id}")
    public ResponseEntity<Product> updateProductApi(@PathVariable("id") long id, @RequestBody Product product) {
        Optional<Product> TestData = IRepositoryProduct.findById(id);

        if (TestData.isPresent()) {
            Product _product = TestData.get();
            _product.setIdType(product.getIdType());
            _product.setCost(product.getCost());
            _product.setCountStock(product.getCountStock());
            _product.setManufacturer(product.getManufacturer());
            _product.setAdditionDescription(product.getAdditionDescription());
            return new ResponseEntity<>(IRepositoryProduct.save(_product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
        try {
            IRepositoryProduct.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(value = "Удаление товара по идентификатору")
    @DeleteMapping("/swagger/product/{id}")
    public ResponseEntity<HttpStatus> deleteProductApi(@PathVariable("id") long id) {
        try {
            IRepositoryProduct.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product")
    public ResponseEntity<HttpStatus> deleteAllProduct() {
        try {
            IRepositoryProduct.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(value = "Удаление всех товаров")
    @DeleteMapping("/swagger/product")
    public ResponseEntity<HttpStatus> deleteAllProductApi() {
        try {
            IRepositoryProduct.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}