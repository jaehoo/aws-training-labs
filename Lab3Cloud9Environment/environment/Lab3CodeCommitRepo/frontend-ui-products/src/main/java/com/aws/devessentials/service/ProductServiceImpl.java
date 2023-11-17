package com.aws.devessentials.service;

import com.aws.devessentials.model.BackendServices;
import com.aws.devessentials.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private BackendServices backendServices;

    private static final String RETRIEVE_PRODUCTS = "/lab3/v1/backend/retrieve/products";

    private static final String CREATE_PRODUCTS = "/lab3/v1/backend/create/products";

    public List<Product> getAllProducts() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<Product>> response = restTemplate.exchange(
                    this.backendServices.getBackendRetrieveUrl() + RETRIEVE_PRODUCTS,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>(){});
            return response.getBody();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Application ran into problems while loading the products");
        }
    }

    public void addProduct(final Product product) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Product> request = new HttpEntity<>(product);
            restTemplate.postForObject(
                    this.backendServices.getBackendCreateUrl() + CREATE_PRODUCTS, request, Product.class);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Application ran into problems while adding a new product");
        }
    }
}
