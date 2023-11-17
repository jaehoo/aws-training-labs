package com.aws.devessentials.dao;

import com.aws.devessentials.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    List<Product> getAllProducts() throws SQLException;

    void addProduct(final Product product) throws SQLException;
}
