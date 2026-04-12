package Cart.cart_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Cart.cart_service.CartEntity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
}