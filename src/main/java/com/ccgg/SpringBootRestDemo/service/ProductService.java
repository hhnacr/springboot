package com.ccgg.SpringBootRestDemo.service;

import com.ccgg.SpringBootRestDemo.beans.Product;
import com.ccgg.SpringBootRestDemo.dao.ProductDao;
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
public class ProductService{
    @Autowired
    ProductDao productDao;

    public Response add(Product product) {
        productDao.save(product);
        return new Response(true);
    }

    public Response update(int id, Product product){
        Optional<Product> productOld = findById(id);
        if (productOld.isPresent()) {
            deleteById(id);
            productDao.save(product);
            return new Response(true, String.format("Product %d updated successfully", id));
        } else {
            productDao.save(product);
            return new Response(true, String.format("Product %d does not exist, save it as a new product", id));
        }
    }

    @Nullable
    public Product getProductById(int id) {
        return findById(id).orElse(null);
    }

    private Optional<Product> findById(int id) {
        return productDao.findById(id);
    }

    public List<Product> findAll(){
        return productDao.findAll();
    }

    public Response deleteById(int id) {
        if (findById(id).isPresent()) {
            productDao.deleteById(id);
            return new Response(true);
        } else {
            return new Response(false, String.format("Product id %d does not exist", id));
        }
    }
}
