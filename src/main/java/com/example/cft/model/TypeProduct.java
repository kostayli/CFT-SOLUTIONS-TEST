package com.example.cft.model;

import javax.persistence.*;

@Entity
@Table(name = "type")
public class TypeProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "nameType")
    private String nameType;

    @Column(name = "nameDescription")
    private String nameDescription;

    @Column(name = "descriptionFeatures")
    private String descriptionFeatures;

    public TypeProduct() {
    }

    public TypeProduct(String nameType, String nameDescription,String descriptionFeatures) {
        this.nameType = nameType;
        this.nameDescription = nameDescription;
        this.descriptionFeatures=descriptionFeatures;
    }
    //ГЕТТЕРЫ
    public long getId() {
        return id;
    }

    public String getNameType() {
        return nameType;
    }

    public String getNameDescription() {
        return nameDescription;
    }

    public String getDescriptionFeatures() {
        return descriptionFeatures;
    }
    //СЕТТЕРЫ
    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public void setNameDescription(String nameDescription) {
        this.nameDescription = nameDescription;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescriptionFeatures(String descriptionFeatures) {
        this.descriptionFeatures = descriptionFeatures;
    }
}
