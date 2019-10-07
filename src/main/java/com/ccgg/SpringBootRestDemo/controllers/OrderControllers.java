package com.ccgg.SpringBootRestDemo.controllers;

import com.ccgg.SpringBootRestDemo.beans.Order;
import com.ccgg.SpringBootRestDemo.http.Response;
import com.ccgg.SpringBootRestDemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/orders")
//@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class OrderControllers {
    @Autowired
    OrderService orderService;
    @PostMapping(path = "/create/{username}")
    public Response createOrder(@RequestBody Order order, @PathVariable("username") String userName) {
        return orderService.create(order, userName);
    }

    @DeleteMapping(path = "/{id}")
    public Response deleteOrder(@PathVariable("id") int id) {
        return orderService.deleteById(id);
    }

    @GetMapping(path = "/{id}")
    public Order getOrderById(@PathVariable("id") int id){
        return orderService.getOrderById(id);
    }

    @PutMapping(path = "/update/{id}/{username}")
    public Response updateOrder(@PathVariable("id") int id, @PathVariable("username") String userName, @RequestBody Order order){
        return orderService.update(id, userName, order);
    }

    @PutMapping(path = "/update/{order_id}/addproduct/{product_id}")
    public Response addProduct(@PathVariable("order_id") int orderId, @PathVariable("product_id") int productId) {
        return orderService.addProduct(orderId, productId);
    }

    @PutMapping(path = "/update/{order_id}/deleteProduct/{product_id}")
    public Response deleteProduct(@PathVariable("order_id") int orderId, @PathVariable("product_id") int productId) {
        return orderService.deleteProduct(orderId, productId);
    }
}
