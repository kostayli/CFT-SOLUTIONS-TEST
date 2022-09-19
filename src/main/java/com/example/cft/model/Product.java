package com.example.cft.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ApiModelProperty(name = "idType", value = "1", notes = "Идентификатор типа товара")
    @Column(name = "idType")
    private long idType;
    @ApiModelProperty(name = "manufacturer", value = "GPB", notes = "Название производителя товара")
    @Column(name = "manufacturer")
    private String manufacturer;
    @ApiModelProperty(name = "cost", value = "999", notes = "Стоимость товара")
    @Column(name = "cost")
    private float cost;
    @ApiModelProperty(name = "countStock", value = "9", notes = "Количество единиц продукции на складе")
    @Column(name = "countStock")
    private int countStock;
    @ApiModelProperty(name = "additionDescription", value = "моноблок", notes = "Дополнительное свойство товара")
    @Column(name = "additionDescription")
    private String additionDescription;

    public Product() {
    }
    public Product(long TypeProduct, String manufacturer, float cost, int countStock, String additionDescription) {
        this.idType=TypeProduct;
        this.manufacturer = manufacturer;
        this.cost = cost;
        this.countStock = countStock;
        this.additionDescription = additionDescription;
    }
    //ГЕТТЕРЫ
    public long getId() {
        return id;
    }

    public long getIdType() {
        return idType;
    }


    public String getManufacturer() {
        return manufacturer;
    }

    public float getCost() {
        return cost;
    }

    public int getCountStock() {
        return countStock;
    }

    public String getAdditionDescription() {
        return additionDescription;
    }
 //СЕТТЕРЫ


    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setAdditionDescription(String additionDescription) {
        this.additionDescription = additionDescription;
    }

    public void setCountStock(int countStock) {
        this.countStock = countStock;
    }

    public void setIdType(long idType) {
        this.idType = idType;
    }
}
