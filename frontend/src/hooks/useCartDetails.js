import { useEffect, useState } from "react";
import { getAllCarts } from "../services/cartService";

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
      const selectedCart = carts.find((item) => String(item.id) === String(cartId));

      if (!selectedCart) {
        setError("Cart not found.");
        setCart(null);
      } else {
        setCart(selectedCart);
      }
    } catch (err) {
      console.error("Error loading cart details:", err);
      setError("Failed to load cart details.");
    } finally {
      setLoading(false);
    }
  };

  return {
    cart,
    loading,
    error,
  };
}

export default useCartDetails;