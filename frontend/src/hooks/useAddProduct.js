import { useState } from "react";
import { createProduct } from "../services/productService";

function useAddProduct() {
  const [formData, setFormData] = useState({
    name: "",
    price: "",
    stock: "",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      setLoading(true);
      setError("");
      setSuccessMessage("");

      const payload = {
        name: formData.name,
        price: Number(formData.price),
        stock: Number(formData.stock),
      };

      await createProduct(payload);

      setSuccessMessage("Product created successfully.");
      setFormData({
        name: "",
        price: "",
        stock: "",
      });
    } catch (err) {
      console.error("Error creating product:", err);
      setError("Failed to create product.");
    } finally {
      setLoading(false);
    }
  };

  return {
    formData,
    loading,
    error,
    successMessage,
    handleChange,
    handleSubmit,
  };
}

export default useAddProduct;