package com.aws.devessentials.dao;

import com.aws.devessentials.model.DbCredentials;
import com.aws.devessentials.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private DbCredentials dbCredentials;

    @Override
    public int addProduct(final Product product) throws SQLException {
        Connection connection = null;
        try {
            final String sql = "INSERT INTO tb_products(pro_creation_date,pro_modification_date,pro_name, pro_description, " +
                    "pro_amount, pro_price) VALUES (?, ?, ?, ?, ?, ?)";
            Timestamp timestamp = new Timestamp(new Date().getTime());

            connection = ConnectionFactory.getConnection(dbCredentials);
            PreparedStatement preparedStmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setTimestamp(1, timestamp);
            preparedStmt.setTimestamp(2, timestamp);
            preparedStmt.setString(3, product.getName());
            preparedStmt.setString(4, product.getDescription());
            preparedStmt.setInt(5, product.getAmount());
            preparedStmt.setDouble(6, product.getPrice());
            preparedStmt.execute();
            ResultSet rs = preparedStmt.getGeneratedKeys();
            int last_inserted_id = 0;
            if(rs.next()){
                last_inserted_id = rs.getInt(1);
            }
            System.out.println("Inserted ProductId: "+last_inserted_id);
            return last_inserted_id;
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
    @Override
    public void removeProduct(final Product product) throws SQLException {
        Connection connection = null;
        try {
            final String sql = "DELETE FROM tb_products WHERE pro_id=?";

            connection = ConnectionFactory.getConnection(dbCredentials);
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setLong(1, product.getId());
            preparedStmt.execute();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public void updateProduct(final Product product) throws SQLException {
        Connection connection = null;
        try {
            final String sql = "UPDATE tb_products SET pro_modification_date=? ,pro_name=?, pro_description=?, " +
                    "pro_amount=?, pro_price=? WHERE pro_id=?";
            Timestamp timestamp = new Timestamp(new Date().getTime());

            connection = ConnectionFactory.getConnection(dbCredentials);
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setTimestamp(1, timestamp);
            preparedStmt.setString(2, product.getName());
            preparedStmt.setString(3, product.getDescription());
            preparedStmt.setInt(4, product.getAmount());
            preparedStmt.setDouble(5, product.getPrice());
            preparedStmt.setDouble(6, product.getId());
            preparedStmt.execute();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
