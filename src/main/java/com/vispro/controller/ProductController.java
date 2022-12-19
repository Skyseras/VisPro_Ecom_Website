package com.vispro.controller;

import com.vispro.dto.product.ProductDto;
import com.vispro.model.Category;
import com.vispro.response.ApiResponse;
import com.vispro.service.CategoryService;
import com.vispro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ProductController {
    @Autowired ProductService productService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("home")
    public String getAll(Model model) {
        List<ProductDto> body = productService.listProducts();
        model.addAttribute("listProduct" , body);
        return "index";
    }
    @GetMapping("shop")
    public String getProducts(Model model) {
        List<ProductDto> body = productService.listProducts();
        model.addAttribute("listProduct" , body);
        return "shop";
    }

    @PostMapping("add")
    public String addProduct(@RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return "shop";
        }
        Category category = optionalCategory.get();
        productService.addProduct(productDto, category);
        return "shop";
    }

    @PostMapping("update/{productID}")
    public String updateProduct(@PathVariable("productID") Integer productID, @RequestBody @Valid ProductDto productDto) {
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return "shop";
        }
        Category category = optionalCategory.get();
        productService.updateProduct(productID, productDto, category);
        return "shop";
        }
}
