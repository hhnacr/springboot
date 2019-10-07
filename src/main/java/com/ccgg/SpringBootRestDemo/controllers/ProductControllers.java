package com.ccgg.SpringBootRestDemo.controllers;

import com.ccgg.SpringBootRestDemo.beans.Product;
import com.ccgg.SpringBootRestDemo.http.Response;
import com.ccgg.SpringBootRestDemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/products")
//@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class ProductControllers {
    @Autowired
    ProductService productService;

    @PostMapping(path = "/add")
    public Response addProduct(@RequestBody Product product) {
        return productService.add(product);
    }

    @DeleteMapping(path = "/{id}")
    public Response deleteUser(@PathVariable("id") int id) {
        return productService.deleteById(id);
    }

    @GetMapping(path = "/{id}")
    public Product getProductById(@PathVariable("id") int id){
        return productService.getProductById(id);
    }

    @GetMapping(path = "/all")
    public List<Product> getAllProduct(){
        return productService.findAll();
    }

    @PutMapping(path = "/update/{id}")
    public Response updateProduct(@PathVariable int id, @RequestBody Product product){
        return productService.update(id, product);
    }
}
