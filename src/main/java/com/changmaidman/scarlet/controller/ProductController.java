package com.changmaidman.scarlet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
public class ProductController {

    @GetMapping("/products")
    public String getProducts(Model model) {
        model.addAttribute("products", Arrays.asList("Headphones", "Laptop", "Shoes"));
        return "products";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "/";
    }
}
