package Cart.cart_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import Cart.cart_service.CartEntity.Cart;
import Cart.cart_service.CartEntity.CartItem;
import Cart.cart_service.dto.ProductResponse;
import Cart.cart_service.repository.CartItemRepository;
import Cart.cart_service.repository.CartRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final WebClient webClient;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       WebClient webClient) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.webClient = webClient;
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
        Integer productId = cartItem.getProductId();
        Integer quantity = cartItem.getQuantity();

        ProductResponse product = webClient.get()
                .uri("/products/{id}", productId)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();

        if (product == null) {
            throw new RuntimeException("Product not found in product-service");
        }

        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock available");
        }

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