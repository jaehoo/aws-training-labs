package com.aws.devessentials.dao;

import com.aws.devessentials.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    List<Product> getAllProducts() throws SQLException;

    int addProduct(final Product product) throws SQLException;

    void removeProduct(final Product product) throws SQLException;

    void updateProduct(final Product product) throws SQLException;


}
