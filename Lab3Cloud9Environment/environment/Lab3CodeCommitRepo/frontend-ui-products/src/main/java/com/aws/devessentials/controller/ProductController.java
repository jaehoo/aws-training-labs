package com.aws.devessentials.controller;

import com.aws.devessentials.model.Product;
import com.aws.devessentials.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String getProducts(Model model) throws UnknownHostException {
        model.addAttribute("localAddress", "Private IP: " + InetAddress.getLocalHost().getHostAddress());
        model.addAttribute("localHostname", "Private DNS: " + InetAddress.getLocalHost().getHostName());

        System.out.println("Getting all products using backend-retrieve-products microservice ...");
        model.addAttribute("products", this.productService.getAllProducts());
        return "products";
    }

    @PostMapping(value = "/")
    public String addProduct(@RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("amount") Integer amount,
                             @RequestParam("price") Double price,
                             Model model) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setAmount(amount);
        product.setPrice(price);

        System.out.println("Saving a new product using backend-create-products microservice...");
        this.productService.addProduct(product);
        return "redirect:/";
    }

    @GetMapping(value = "/health", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> healthCheck() throws UnknownHostException {
        final String privateIp = InetAddress.getLocalHost().getHostAddress();
        final String privateDns = InetAddress.getLocalHost().getHostName();

        final String jsonResponse = "{" +
                "\"service\":\"frontend-ui-products\"," +
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
