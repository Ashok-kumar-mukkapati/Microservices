package Product.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Product.product_service.ProductEntity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}