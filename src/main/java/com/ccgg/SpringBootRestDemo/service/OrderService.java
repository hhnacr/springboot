package com.ccgg.SpringBootRestDemo.service;


import com.ccgg.SpringBootRestDemo.beans.CcggUser;
import com.ccgg.SpringBootRestDemo.beans.Order;
import com.ccgg.SpringBootRestDemo.dao.OrderDao;
import com.ccgg.SpringBootRestDemo.dao.ProductDao;
import com.ccgg.SpringBootRestDemo.dao.UserDao;
import com.ccgg.SpringBootRestDemo.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Repository
public class OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    UserDao userDao;

    public Response create(Order order, String userName) {
        if (userDao.findByUsername(userName) == null) {
            return new Response(false, String.format("User %s does not exist", userName));
        }
        order.setUser(userDao.findByUsername(userName));
        order.calculateTotalPrice();
        order.calculateProductQuantity();
        orderDao.save(order);
        return new Response(true);
    }

    public Response update(int id, String userName, Order order){
        Optional<Order> orderOld = findById(id);
        if (orderOld.isPresent()) {
            CcggUser user = orderOld.get().getUser();
            deleteById(id);
            order.setUser(user);
            order.calculateProductQuantity();
            order.calculateTotalPrice();
            orderDao.save(order);
            return new Response(true, String.format("Product %d updated successfully", id));
        } else {
            create(order, userName);
            return new Response(true, String.format("Product %d does not exist, save it as a new product", id));
        }
    }

    @Nullable
    public Order getOrderById(int id) {
        return findById(id).orElse(null);
    }

    private Optional<Order> findById(int id) {
        return orderDao.findById(id);
    }

    public List<Order> findAll(){
        return orderDao.findAll();
    }

    public Response deleteById(int id) {
        if (findById(id) != null) {
            orderDao.deleteById(id);
            return new Response(true);
        } else {
            return new Response(false, String.format("Product id %d does not exist", id));
        }
    }

    public Response addProduct(int orderId, int productId) {
        if (!findById(orderId).isPresent()) {
            return new Response(false, String.format("Order id %d does not exist", orderId));
        }
        if (!productDao.findById(productId).isPresent()) {
            return new Response(false, String.format("Product id %d does not exist", productId));
        }
        Order order = findById(orderId).get();
        order.addProduct(productDao.findById(productId).get());
        order.calculateTotalPrice();
        order.calculateProductQuantity();
        return new Response(true);
    }

    public Response deleteProduct(int orderId, int productId) {
        if (!findById(orderId).isPresent()) {
            return new Response(false, String.format("Order id %d does not exist", orderId));
        }
        if (!productDao.findById(productId).isPresent()) {
            return new Response(false, String.format("Product id %d does not exist", productId));
        }
        Order order = findById(orderId).get();
        order.deleteProduct(productDao.findById(productId).get());
        order.calculateTotalPrice();
        return new Response(true);
    }
}
