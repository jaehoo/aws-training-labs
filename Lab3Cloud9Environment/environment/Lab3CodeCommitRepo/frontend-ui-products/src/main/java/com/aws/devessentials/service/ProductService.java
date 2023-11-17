package com.aws.devessentials.service;

import com.aws.devessentials.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    void addProduct(final Product product);
}
