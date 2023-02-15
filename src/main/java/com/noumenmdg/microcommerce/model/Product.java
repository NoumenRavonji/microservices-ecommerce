package com.noumenmdg.microcommerce.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

//@JsonIgnoreProperties(value = {"purchasePrice", "id"})
//@JsonFilter("myDynamicFilter")
@Entity
public class Product {
    @Id
    private int id;

    @Size(min=3, max=25)
    private String name;

    @Min(value = 1)
    private int price;

    //information que nous ne souhaitons pas exposer
    //@JsonIgnore
    private int purchasePrice;

    public Product() {
    }
    public Product(int id, String name, int price, int purchasePrice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.purchasePrice = purchasePrice;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    /*@Override
    public String toString(){
        return "Product{" +
                "id :" +id +
                "name : " + name +
                "price :" + price +
                "}";
    }*/
}
