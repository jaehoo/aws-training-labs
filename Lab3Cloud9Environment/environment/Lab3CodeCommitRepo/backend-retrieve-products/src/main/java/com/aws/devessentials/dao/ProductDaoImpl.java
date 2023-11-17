package com.aws.devessentials.dao;

import com.aws.devessentials.model.DbCredentials;
import com.aws.devessentials.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private DbCredentials dbCredentials;

    @Override
    public void addProduct(final Product product) throws SQLException {
        Connection connection = null;
        try {
            final String sql = "INSERT INTO tb_products(pro_creation_date,pro_modification_date,pro_name, pro_description, " +
                    "pro_amount, pro_price) VALUES (?, ?, ?, ?, ?, ?)";
            Timestamp timestamp = new Timestamp(new Date().getTime());

            connection = ConnectionFactory.getConnection(dbCredentials);
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setTimestamp(1, timestamp);
            preparedStmt.setTimestamp(2, timestamp);
            preparedStmt.setString(3, product.getName());
            preparedStmt.setString(4, product.getDescription());
            preparedStmt.setInt(5, product.getAmount());
            preparedStmt.setDouble(6, product.getPrice());
            preparedStmt.execute();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        final String query = "SELECT * FROM tb_products";

        try (
                Connection connection = ConnectionFactory.getConnection(dbCredentials);
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getLong("pro_id"));
                product.setName(resultSet.getString("pro_name"));
                product.setDescription(resultSet.getString("pro_description"));
                product.setAmount(resultSet.getInt("pro_amount"));
                product.setPrice(resultSet.getDouble("pro_price"));
                product.setCreationDate(resultSet.getTimestamp("pro_creation_date"));
                product.setModificationDate(resultSet.getTimestamp("pro_modification_date"));
                products.add(product);
            }
        }
        return products;
    }
}
