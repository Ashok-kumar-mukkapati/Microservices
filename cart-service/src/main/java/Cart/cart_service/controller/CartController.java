package Cart.cart_service.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Cart.cart_service.CartEntity.Cart;
import Cart.cart_service.CartEntity.CartItem;
import Cart.cart_service.service.CartService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/carts")
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        log.info("API CALL: createCart");
        Cart savedCart = cartService.createCart(cart);
        return ResponseEntity.ok(savedCart);
    }

    @PatchMapping("/items/{id}/remove-quantity")
    public ResponseEntity<?> removeCartItemQuantity(
            @PathVariable Integer id,
            @RequestParam Integer quantity
    ) {
        log.info("API CALL: removeCartItemQuantity with id={} quantity={}", id, quantity);

        CartItem updatedCartItem = cartService.removeCartItemQuantity(id, quantity);

        if (updatedCartItem == null) {
            return ResponseEntity.ok("Cart item removed completely");
        }

        return ResponseEntity.ok(updatedCartItem);
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        log.info("API CALL: getAllCarts");
        List<Cart> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Integer id) {
        log.info("API CALL: getCartById with id={}", id);
        Cart cart = cartService.getCartById(id);

        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")
    public ResponseEntity<?> addCartItem(@Valid @RequestBody CartItem cartItem) {
        log.info("API CALL: addCartItem with productId={} quantity={}", cartItem.getProductId(), cartItem.getQuantity());
        CartItem savedCartItem = cartService.addCartItem(cartItem);
        return ResponseEntity.ok(savedCartItem);
    }

    @PostMapping("/items/async")
    public CompletableFuture<ResponseEntity<Object>> addCartItemAsync(@Valid @RequestBody CartItem cartItem) {
        log.info("API CALL: addCartItemAsync with productId={} quantity={}", cartItem.getProductId(), cartItem.getQuantity());

        return cartService.addCartItemAsync(cartItem)
                .thenApply(savedCartItem -> ResponseEntity.ok((Object) savedCartItem))
                .exceptionally(ex -> ResponseEntity.badRequest().body(
                        (Object) (ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage())
                ));
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getAllCartItems() {
        log.info("API CALL: getAllCartItems");
        List<CartItem> cartItems = cartService.getAllCartItems();
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Integer id) {
        log.info("API CALL: deleteCart with id={}", id);

        Cart existingCart = cartService.getCartById(id);
        if (existingCart == null) {
            return ResponseEntity.notFound().build();
        }

        cartService.deleteCart(id);
        return ResponseEntity.ok("Cart deleted successfully");
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Integer id) {
        log.info("API CALL: deleteCartItem with id={}", id);
        cartService.deleteCartItem(id);
        return ResponseEntity.ok("Cart item deleted successfully");
    }
}