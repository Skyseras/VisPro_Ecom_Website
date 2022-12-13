package com.vispro.controller;

import com.vispro.dto.cart.AddToCartDto;
import com.vispro.dto.cart.CartDto;
import com.vispro.exceptions.CartItemNotExistException;
import com.vispro.exceptions.ProductNotExistException;
import com.vispro.model.Product;
import com.vispro.response.ApiResponse;
import com.vispro.service.CartService;
import com.vispro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto) throws ProductNotExistException {
        Product product = productService.getProductById(addToCartDto.getProductId());
        System.out.println("product to add"+  product.getName());
        cartService.addToCart(addToCartDto, product);
        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);

    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems() {
        CartDto cartDto = cartService.listCartItems();
        return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
    }

    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<ApiResponse> updateCartItem(@RequestBody @Valid AddToCartDto cartDto) throws ProductNotExistException {
        /*Product product = productService.getProductById(cartDto.getProductId());*/
        cartService.updateCartItem(cartDto/*,product*/);
        return new ResponseEntity<>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") int itemID) throws CartItemNotExistException {
        cartService.deleteCartItem(itemID);
        return new ResponseEntity<>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
    }

}
