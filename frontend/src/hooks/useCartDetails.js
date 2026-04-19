import { useEffect, useState } from "react";
import {
  getAllCarts,
  deleteCartItem,
} from "../services/cartService";
import { getAllProducts } from "../services/productService";

function useCartDetails(cartId) {
  const [cart, setCart] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    loadCartDetails();
  }, [cartId]);

  const loadCartDetails = async () => {
    try {
      setLoading(true);
      setError("");

      const carts = await getAllCarts();
      const products = await getAllProducts();

      const selectedCart = carts.find(
        (item) => String(item.id) === String(cartId)
      );

      if (!selectedCart) {
        setError("Cart not found.");
        setCart(null);
        return;
      }

      const updatedItems = selectedCart.items.map((item) => {
        const matchedProduct = products.find(
          (product) => product.id === item.productId
        );

        return {
          ...item,
          productName: matchedProduct
            ? matchedProduct.name
            : "Unknown Product",
        };
      });

      setCart({
        ...selectedCart,
        items: updatedItems,
      });
    } catch (err) {
      console.error(err);
      setError("Failed to load cart details.");
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteItem = async (itemId) => {
    try {
      await deleteCartItem(itemId);
      await loadCartDetails();

      return {
        success: true,
        message: "Item removed from cart successfully.",
      };
    } catch (err) {
      console.error(err);

      return {
        success: false,
        message: "Failed to remove item from cart.",
      };
    }
  };

  return {
    cart,
    loading,
    error,
    handleDeleteItem,
  };
}

export default useCartDetails;