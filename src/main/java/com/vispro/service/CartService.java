package com.vispro.service;

import com.vispro.dto.cart.AddToCartDto;
import com.vispro.dto.cart.CartDto;
import com.vispro.dto.cart.CartItemDto;
import com.vispro.exceptions.CartItemNotExistException;
import com.vispro.model.Cart;
import com.vispro.model.Product;
import com.vispro.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public CartService(){}

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addToCart(AddToCartDto addToCartDto, Product product){
        Cart cart = new Cart(product, addToCartDto.getQuantity());
        cartRepository.save(cart);
    }

    public CartDto listCartItems() {
        List<Cart> cartList = cartRepository.findAll();
        List<CartItemDto> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDto cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += (cartItemDto.getProduct().getPrice()* cartItemDto.getQuantity());
        }
        return new CartDto(cartItems,totalCost);
    }


    public static CartItemDto getDtoFromCart(Cart cart) {
        return new CartItemDto(cart);
    }





//// need to check this one on getreferencebyid
    public void updateCartItem(AddToCartDto cartDto/*,Product product*/ ){
        Cart cart = cartRepository.getReferenceById(cartDto.getId());
        cart.setQuantity(cartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cartRepository.save(cart);
    }

    public void deleteCartItem(int id) throws CartItemNotExistException {
        if (!cartRepository.existsById(id))
            throw new CartItemNotExistException("Cart id is invalid : " + id);
        cartRepository.deleteById(id);
    }
}


