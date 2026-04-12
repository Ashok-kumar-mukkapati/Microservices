package Cart.cart_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Cart.cart_service.CartEntity.Cart;
import Cart.cart_service.CartEntity.CartItem;
import Cart.cart_service.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        Cart savedCart = cartService.createCart(cart);
        return ResponseEntity.ok(savedCart);
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Integer id) {
        Cart cart = cartService.getCartById(id);

        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")
    public ResponseEntity<?> addCartItem(@RequestBody CartItem cartItem) {
        try {
            CartItem savedCartItem = cartService.addCartItem(cartItem);
            return ResponseEntity.ok(savedCartItem);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getAllCartItems() {
        List<CartItem> cartItems = cartService.getAllCartItems();
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Integer id) {
        Cart existingCart = cartService.getCartById(id);

        if (existingCart == null) {
            return ResponseEntity.notFound().build();
        }

        cartService.deleteCart(id);
        return ResponseEntity.ok("Cart deleted successfully");
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Integer id) {
        cartService.deleteCartItem(id);
        return ResponseEntity.ok("Cart item deleted successfully");
    }
}