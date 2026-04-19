import { useEffect, useState } from "react";
import {
  getAllCarts,
  createCart,
  deleteCart,
} from "../services/cartService";

function useCarts() {
  const [carts, setCarts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadCarts = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await getAllCarts();
      setCarts(data);
    } catch (err) {
      console.error(err);
      setError("Failed to load carts.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadCarts();
  }, []);

  const handleCreateCart = async (userId) => {
    try {
      await createCart({
        userId: Number(userId),
      });

      await loadCarts();

      return {
        success: true,
        message: "Cart created successfully.",
      };
    } catch (err) {
      console.error(err);

      return {
        success: false,
        message: "Failed to create cart.",
      };
    }
  };

  const handleDeleteCart = async (cartId) => {
    try {
      await deleteCart(cartId);

      await loadCarts();

      return {
        success: true,
        message: "Cart deleted successfully.",
      };
    } catch (err) {
      console.error(err);

      return {
        success: false,
        message: "Failed to delete cart.",
      };
    }
  };

  return {
    carts,
    loading,
    error,
    handleCreateCart,
    handleDeleteCart,
  };
}

export default useCarts;