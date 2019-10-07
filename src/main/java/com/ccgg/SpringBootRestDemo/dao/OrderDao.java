package com.ccgg.SpringBootRestDemo.dao;

import com.ccgg.SpringBootRestDemo.beans.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Integer> {
}
