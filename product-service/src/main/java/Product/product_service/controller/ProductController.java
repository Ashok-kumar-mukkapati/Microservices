package Product.product_service.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Product.product_service.ProductEntity.Product;
import Product.product_service.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        log.info("API CALL: createProduct with name={}", product.getName());
        Product savedProduct = productService.createProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("API CALL: getAllProducts");
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        log.info("API CALL: getProductById with id={}", id);
        Product product = productService.getProductById(id);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        log.info("API CALL: updateProduct with id={}", id);
        Product updatedProduct = productService.updateProduct(id, product);

        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        log.info("API CALL: deleteProduct with id={}", id);
        Product existingProduct = productService.getProductById(id);

        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }

        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/paged")
    public ResponseEntity<Map<String, Object>> getPaginatedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice
    ) {
        log.info("API CALL: getPaginatedProducts page={} size={} sortBy={} sortDir={} keyword={} minPrice={}",
                page, size, sortBy, sortDir, keyword, minPrice);

        Map<String, Object> response = productService.getPaginatedSortedFilteredProducts(
                page, size, sortBy, sortDir, keyword, minPrice
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/price-greater-than")
    public ResponseEntity<List<Product>> getProductsWithPriceGreaterThan(@RequestParam Double price) {
        log.info("API CALL: getProductsWithPriceGreaterThan price={}", price);
        List<Product> products = productService.getProductsWithPriceGreaterThan(price);
        return ResponseEntity.ok(products);
    }
}