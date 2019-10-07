package com.ccgg.SpringBootRestDemo.beans;

import javax.persistence.*;

@Entity
@Table(name = "ccgg_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ")
    @SequenceGenerator(name = "SEQ", sequenceName = "CCGG_PRODUCT_SEQ")
    private int id;
    @Column(name = "productname", nullable = false)
    private String productName;
    @Column(name = "price", nullable = false)
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                '}';
    }

}
