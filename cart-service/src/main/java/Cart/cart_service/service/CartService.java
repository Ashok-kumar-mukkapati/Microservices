package Cart.cart_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import Cart.cart_service.CartEntity.Cart;
import Cart.cart_service.CartEntity.CartItem;
import Cart.cart_service.repository.CartItemRepository;
import Cart.cart_service.repository.CartRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(Integer id) {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        return optionalCart.orElse(null);
    }

    public CartItem addCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    public void deleteCart(Integer id) {
        cartRepository.deleteById(id);
    }

    public void deleteCartItem(Integer id) {
        cartItemRepository.deleteById(id);
    }
}