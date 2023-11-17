package com.aws.devessentials.controller;

import com.aws.devessentials.model.Product;
import com.aws.devessentials.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.InetAddress;
import java.net.UnknownHostException;
@CrossOrigin
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/lab3/v1/backend/create/products", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addProduct(@RequestBody final Product product) throws UnknownHostException {
        System.out.println("Saving a new product in RDS...");

        int id = this.productService.addProduct(product);

        final String privateIp = InetAddress.getLocalHost().getHostAddress();
        final String privateDns = InetAddress.getLocalHost().getHostName();

        final String jsonResponse = "{" +
                "\"message\":\"PRODUCT_CREATED\"," +
                "\"productId\":\"" + id + "\"," +
                "\"privateIp\":\"" + privateIp + "\"," +
                "\"privateDns\":\"" + privateDns + "\"" +
                "}";

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("dev-essentials-private-ip", privateIp);
        responseHeaders.set("dev-essentials-private-dns", privateDns);

        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }

    @GetMapping(value = "/", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> healthCheck() throws UnknownHostException {
        final String privateIp = InetAddress.getLocalHost().getHostAddress();
        final String privateDns = InetAddress.getLocalHost().getHostName();

        final String jsonResponse = "{" +
                "\"service\":\"backend-create-products\"," +
                "\"status\":\"ok\"," +
                "\"privateIp\":\"" + privateIp + "\"," +
                "\"privateDns\":\"" + privateDns + "\"" +
                "}";

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("dev-essentials-private-ip", privateIp);
        responseHeaders.set("dev-essentials-private-dns", privateDns);

        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }

    @PostMapping(value = "/lab3/v1/backend/delete/products", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> removeProduct(@RequestBody final Product product) throws UnknownHostException {
        System.out.println("Deleting a product in RDS...");

        this.productService.removeProduct(product);

        final String privateIp = InetAddress.getLocalHost().getHostAddress();
        final String privateDns = InetAddress.getLocalHost().getHostName();

        final String jsonResponse = "{" +
                "\"message\":\"PRODUCT_REMOVED\"," +
                "\"privateIp\":\"" + privateIp + "\"," +
                "\"privateDns\":\"" + privateDns + "\"" +
                "}";

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("dev-essentials-private-ip", privateIp);
        responseHeaders.set("dev-essentials-private-dns", privateDns);

        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }

    @PostMapping(value = "/lab3/v1/backend/update/products", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateProduct(@RequestBody final Product product) throws UnknownHostException {
        System.out.println("Updating a product in RDS...");

        this.productService.updateProduct(product);

        final String privateIp = InetAddress.getLocalHost().getHostAddress();
        final String privateDns = InetAddress.getLocalHost().getHostName();

        final String jsonResponse = "{" +
                "\"message\":\"PRODUCT_UPDATED\"," +
                "\"privateIp\":\"" + privateIp + "\"," +
                "\"privateDns\":\"" + privateDns + "\"" +
                "}";

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("dev-essentials-private-ip", privateIp);
        responseHeaders.set("dev-essentials-private-dns", privateDns);

        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }
}
