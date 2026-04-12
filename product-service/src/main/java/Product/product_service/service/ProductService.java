package Product.product_service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import Product.product_service.ProductEntity.Product;
import Product.product_service.dto.ProductDTO;
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

    public Map<String, Object> getPaginatedSortedFilteredProducts(
            int page,
            int size,
            String sortBy,
            String sortDir,
            String keyword,
            Double minPrice
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductDTO> filteredProducts = productPage.getContent()
                .stream()
                .filter(product -> keyword == null || keyword.isBlank()
                        || product.getName().toLowerCase().contains(keyword.toLowerCase()))
                .filter(product -> minPrice == null || product.getPrice() >= minPrice)
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getStock(),
                        product.getStock() > 0 ? "IN STOCK" : "OUT OF STOCK"
                ))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", productPage.getNumber());
        response.put("pageSize", productPage.getSize());
        response.put("totalElements", productPage.getTotalElements());
        response.put("totalPages", productPage.getTotalPages());
        response.put("sortBy", sortBy);
        response.put("sortDirection", sortDir);
        response.put("products", filteredProducts);

        return response;
    }
}