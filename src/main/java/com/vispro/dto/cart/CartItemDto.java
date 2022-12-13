package com.vispro.dto.cart;

import com.vispro.model.Cart;
import com.vispro.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Integer id;
    private @NotNull Integer quantity;
    private @NotNull Product product;

    public CartItemDto(Cart cart) {
        this.setId(cart.getId());
        this.setQuantity(cart.getQuantity());
        this.setProduct(cart.getProduct());
    }
}
