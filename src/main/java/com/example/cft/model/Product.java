package com.example.cft.model;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "idType")
    private long idType;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "cost")
    private float cost;

    @Column(name = "countStock")
    private int countStock;

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
