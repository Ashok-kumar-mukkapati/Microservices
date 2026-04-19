import { useEffect, useMemo, useState } from "react";
import { getAllCarts, addProductToCart } from "../services/cartService";
import { deleteProduct, getPagedProducts } from "../services/productService";

function useProducts() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [searchTerm, setSearchTerm] = useState("");
  const [maxPrice, setMaxPrice] = useState("");

  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [totalPages, setTotalPages] = useState(0);

  const [availableCarts, setAvailableCarts] = useState([]);
  const [selectedCartId, setSelectedCartId] = useState("");

  useEffect(() => {
    loadAvailableCarts();
  }, []);

  useEffect(() => {
    loadProducts(page, pageSize);
  }, [page, pageSize]);

  const loadAvailableCarts = async () => {
    try {
      const carts = await getAllCarts();
      setAvailableCarts(carts);

      if (carts.length > 0) {
        setSelectedCartId(String(carts[0].id));
      }
    } catch (err) {
      console.error("Error loading carts:", err);
    }
  };

  const loadProducts = async (pageNumber, size) => {
    try {
      setLoading(true);
      setError("");

      const data = await getPagedProducts(pageNumber, size);
      setProducts(data.products);
      setTotalPages(data.totalPages);
    } catch (err) {
      console.error("Error loading paged products:", err);
      setError("Failed to load products.");
    } finally {
      setLoading(false);
    }
  };

  const filteredProducts = useMemo(() => {
    return products.filter((product) => {
      const matchesSearch = product.name
        .toLowerCase()
        .includes(searchTerm.toLowerCase());

      const matchesPrice =
        maxPrice === "" || product.price <= Number(maxPrice);

      return matchesSearch && matchesPrice;
    });
  }, [products, searchTerm, maxPrice]);

  const handleAddToCart = async (productId) => {
    if (!selectedCartId) {
      return {
        success: false,
        message: "Please select a cart first.",
      };
    }

    try {
      await addProductToCart(selectedCartId, productId, 1);
      return {
        success: true,
        message: `Product added to cart ${selectedCartId} successfully.`,
      };
    } catch (err) {
      console.error("Error adding product to cart:", err);
      return {
        success: false,
        message: "Failed to add product to selected cart.",
      };
    }
  };

  const handleDeleteProduct = async (productId) => {
    try {
      await deleteProduct(productId);
      await loadProducts(page, pageSize);
      return {
        success: true,
        message: "Product deleted successfully.",
      };
    } catch (err) {
      console.error("Error deleting product:", err);
      return {
        success: false,
        message: "Failed to delete product.",
      };
    }
  };

  return {
    filteredProducts,
    loading,
    error,
    searchTerm,
    setSearchTerm,
    maxPrice,
    setMaxPrice,
    page,
    setPage,
    pageSize,
    setPageSize,
    totalPages,
    handleAddToCart,
    handleDeleteProduct,
    availableCarts,
    selectedCartId,
    setSelectedCartId,
  };
}

export default useProducts;