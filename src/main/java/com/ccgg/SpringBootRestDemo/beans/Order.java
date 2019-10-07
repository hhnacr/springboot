package com.ccgg.SpringBootRestDemo.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ccgg_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ")
    @SequenceGenerator(name = "SEQ", sequenceName = "CCGG_ORDER_SEQ")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CcggUser user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "c_order_c_product",
            joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")})
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Product> productSet = new HashSet<>();

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "product_num")
    private double productNum;

    public double calculateTotalPrice() {
        totalPrice = 0;
        for (Product product : productSet) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    public double calculateProductQuantity() {
        productNum = productSet.size();
        return productNum;
    }

    public void addProduct(Product product) {
        productSet.add(product);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

    public CcggUser getUser() {
        return user;
    }

    public void setUser(CcggUser user) {
        this.user = user;
    }

    public boolean hasProduct(Product product) {
        return productSet.contains(product);
    }

    public void deleteProduct(Product product) {
        productSet.remove(product);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productSet=" + productSet +
                '}';
    }
}
