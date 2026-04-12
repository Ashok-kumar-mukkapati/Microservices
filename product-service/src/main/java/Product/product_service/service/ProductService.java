package Product.product_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import Product.product_service.ProductEntity.Product;
import Product.product_service.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    public Product updateProduct(Integer id, Product updatedProduct) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setStock(updatedProduct.getStock());
            return productRepository.save(existingProduct);
        }

        return null;
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    public boolean isStockAvailable(Integer productId, Integer quantity) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return product.getStock() >= quantity;
        }

        return false;
    }
}