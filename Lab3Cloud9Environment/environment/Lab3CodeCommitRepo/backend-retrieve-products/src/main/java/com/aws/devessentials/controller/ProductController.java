package com.aws.devessentials.controller;

import com.aws.devessentials.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.InetAddress;
import java.net.UnknownHostException;

@CrossOrigin
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/lab3/v1/backend/retrieve/products", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getProducts() throws UnknownHostException {
        System.out.println("Getting all products in RDS...");

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("dev-essentials-private-ip", InetAddress.getLocalHost().getHostAddress());
        responseHeaders.set("dev-essentials-private-dns", InetAddress.getLocalHost().getHostName());

        return ResponseEntity.ok().headers(responseHeaders).body(this.productService.getAllProducts());
    }

    @GetMapping(value = "/", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> healthCheck() throws UnknownHostException {
        final String privateIp = InetAddress.getLocalHost().getHostAddress();
        final String privateDns = InetAddress.getLocalHost().getHostName();

        final String jsonResponse = "{" +
                "\"service\":\"backend-retrieve-products\"," +
                "\"status\":\"ok\"," +
                "\"privateIp\":\"" + privateIp + "\"," +
                "\"privateDns\":\"" + privateDns + "\"" +
                "}";

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("dev-essentials-private-ip", privateIp);
        responseHeaders.set("dev-essentials-private-dns", privateDns);

        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }
}
