import { useEffect, useState } from "react";
import { getAllCartItems } from "../services/cartService";

function useCart() {
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    loadCartItems();
  }, []);

  const loadCartItems = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await getAllCartItems();
      setCartItems(data);
    } catch (err) {
      console.error("Error fetching cart items:", err);
      setError("Failed to load cart items.");
    } finally {
      setLoading(false);
    }
  };

  return {
    cartItems,
    loading,
    error,
  };
}

export default useCart;