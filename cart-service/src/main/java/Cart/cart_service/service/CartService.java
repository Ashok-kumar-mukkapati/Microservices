package Cart.cart_service.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import Cart.cart_service.CartEntity.Cart;
import Cart.cart_service.CartEntity.CartItem;
import Cart.cart_service.dto.CartEvent;
import Cart.cart_service.dto.ProductResponse;
import Cart.cart_service.repository.CartItemRepository;
import Cart.cart_service.repository.CartRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final WebClient webClient;
    private final KafkaProducerService kafkaProducerService;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       WebClient webClient,
                       KafkaProducerService kafkaProducerService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.webClient = webClient;
        this.kafkaProducerService = kafkaProducerService;
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
        try {
            return addCartItemAsync(cartItem).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Async processing interrupted");
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause().getMessage());
        }
    }

    public CompletableFuture<CartItem> addCartItemAsync(CartItem cartItem) {
        if (cartItem.getCart() == null || cartItem.getCart().getId() == null) {
            throw new RuntimeException("Cart id is required");
        }

        Integer cartId = cartItem.getCart().getId();
        Integer productId = cartItem.getProductId();
        Integer quantity = cartItem.getQuantity();

        CompletableFuture<Cart> cartFuture = CompletableFuture.supplyAsync(() ->
                cartRepository.findById(cartId)
                        .orElseThrow(() -> new RuntimeException("Cart not found"))
        );

        CompletableFuture<ProductResponse> productFuture = CompletableFuture.supplyAsync(() -> {
            ProductResponse product = webClient.get()
                    .uri("/products/{id}", productId)
                    .retrieve()
                    .bodyToMono(ProductResponse.class)
                    .block();

            if (product == null) {
                throw new RuntimeException("Product not found in product-service");
            }

            return product;
        });

        CompletableFuture<ProductResponse> validatedProductFuture = productFuture.thenApplyAsync(product -> {
            if (product.getStock() < quantity) {
                throw new RuntimeException("Insufficient stock available");
            }
            return product;
        });

        return cartFuture.thenCombine(validatedProductFuture, (existingCart, validatedProduct) -> {
            cartItem.setCart(existingCart);
            CartItem savedCartItem = cartItemRepository.save(cartItem);

            CartEvent cartEvent = new CartEvent(
                    existingCart.getId(),
                    savedCartItem.getProductId(),
                    savedCartItem.getQuantity(),
                    "ITEM_ADDED"
            );

            kafkaProducerService.sendCartEvent(cartEvent);

            return savedCartItem;
        });
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