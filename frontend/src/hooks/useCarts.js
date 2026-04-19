import { useEffect, useState } from "react";
import { getAllCarts } from "../services/cartService";

function useCarts() {
  const [carts, setCarts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    loadCarts();
  }, []);

  const loadCarts = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await getAllCarts();
      setCarts(data);
    } catch (err) {
      console.error("Error loading carts:", err);
      setError("Failed to load carts.");
    } finally {
      setLoading(false);
    }
  };

  return {
    carts,
    loading,
    error,
  };
}

export default useCarts;