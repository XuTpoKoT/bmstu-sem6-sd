package com.music_shop.BL.service;

import com.music_shop.BL.API.ProductService;
import com.music_shop.BL.exception.DBException;
import com.music_shop.BL.exception.NonexistentProductException;
import com.music_shop.BL.log.Logger;
import com.music_shop.BL.log.LoggerImpl;
import com.music_shop.BL.model.Product;
import com.music_shop.DB.API.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger log = new LoggerImpl(getClass().getName());
    private final ProductRepo productRepo;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public Product getProductById(UUID id) {
        log.info("getProductById called with id " + id);
        Product product = productRepo.getProductById(id);
        if (product == null) {
            RuntimeException e = new NonexistentProductException("Нет такого продукта!");
            log.error("getProductById failed", e);
            throw e;
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("getAllProducts called");
        return productRepo.getAllProducts();
    }

    @Override
    public List<Product> getProductsByIds(List<UUID> ids) {
        log.info("getAllProductsByIds called");
        return productRepo.getAllProductsByIds(ids);
    }

    @Override
    public List<Product> getProductsBySkipAndLimit(int skip, int limit) {
        return productRepo.getProductsBySkipAndLimit(skip, limit);
    }

    @Override
    public int getCountProducts() {
        return productRepo.getCountProducts();
    }
}
