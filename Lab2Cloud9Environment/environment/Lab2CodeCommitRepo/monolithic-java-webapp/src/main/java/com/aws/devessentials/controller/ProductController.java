package com.aws.devessentials.controller;

import com.aws.devessentials.model.Product;
import com.aws.devessentials.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String getProducts(Model model) throws UnknownHostException {
        model.addAttribute("localAddress", "Private IP: " + InetAddress.getLocalHost().getHostAddress());
        model.addAttribute("localHostname", "Private DNS: " + InetAddress.getLocalHost().getHostName());

        System.out.println("Getting all products in RDS...");
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

        System.out.println("Saving a new product in RDS...");
        this.productService.addProduct(product);
        return "redirect:/";
    }
}
