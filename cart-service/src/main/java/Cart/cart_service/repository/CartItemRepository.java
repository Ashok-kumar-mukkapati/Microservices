package Cart.cart_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Cart.cart_service.CartEntity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByCart_IdAndProductId(Integer cartId, Integer productId);
}