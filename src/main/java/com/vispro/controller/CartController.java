package com.vispro.controller;

import com.vispro.dto.cart.AddToCartDto;
import com.vispro.dto.cart.CartDto;
import com.vispro.dto.cart.CartItemDto;
import com.vispro.exceptions.CartItemNotExistException;
import com.vispro.exceptions.ProductNotExistException;
import com.vispro.model.Product;
import com.vispro.response.ApiResponse;
import com.vispro.service.CartService;
import com.vispro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;


    @PostMapping("cart/add")
    public String addToCart(@RequestParam("id") Integer id, @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity, HttpServletRequest request) throws ProductNotExistException {
        Product product = productService.getProductById(id);
        cartService.addToCart(quantity, product);
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("cart")
    public String getCartItems(Model model) {
        CartDto cartDto = cartService.listCartItems();
        List<CartItemDto> cartItemDto = cartDto.getcartItems();
        model.addAttribute("totalCart" , cartDto.getTotalCost());
        model.addAttribute("listCart" , cartItemDto);
        return "cart";
    }

    @PutMapping("/update/{cartItemId}")
    public String updateCartItem(@RequestBody @Valid AddToCartDto cartDto) throws ProductNotExistException {
        Product product = productService.getProductById(cartDto.getProductId());
        cartService.updateCartItem(cartDto,product);
        return "cart";
    }

    @DeleteMapping("/delete/{cartItemId}")
    public String deleteCartItem(@PathVariable("cartItemId") int itemID) throws CartItemNotExistException {
        cartService.deleteCartItem(itemID);
        return "cart";
    }

}
