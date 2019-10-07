package com.ccgg.SpringBootRestDemo.dao;

import com.ccgg.SpringBootRestDemo.beans.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Integer> {
}
