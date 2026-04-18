import { useState } from "react";
import { createProduct } from "../services/productService";

function AddProductPage() {
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

  return (
    <div>
      <h1>Add Product</h1>

      {error && <p>{error}</p>}
      {successMessage && <p>{successMessage}</p>}

      <form onSubmit={handleSubmit}>
        <div>
          <label>Name: </label>
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </div>

        <div>
          <label>Price: </label>
          <input
            type="number"
            name="price"
            value={formData.price}
            onChange={handleChange}
            required
          />
        </div>

        <div>
          <label>Stock: </label>
          <input
            type="number"
            name="stock"
            value={formData.stock}
            onChange={handleChange}
            required
          />
        </div>

        <button type="submit" disabled={loading}>
          {loading ? "Creating..." : "Create Product"}
        </button>
      </form>
    </div>
  );
}

export default AddProductPage;