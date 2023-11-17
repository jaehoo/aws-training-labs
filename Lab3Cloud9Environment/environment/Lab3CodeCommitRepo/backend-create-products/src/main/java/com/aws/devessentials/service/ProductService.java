package com.aws.devessentials.service;

import com.aws.devessentials.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    int addProduct(final Product product);

    void removeProduct(final Product product);

    void updateProduct(final Product product);

}
