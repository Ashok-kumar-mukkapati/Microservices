import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { updateProduct } from "../services/productService";

function UpdateProductPage() {
  const [searchParams] = useSearchParams();

  const [productId, setProductId] = useState("");
  const [formData, setFormData] = useState({
    name: "",
    price: "",
    stock: "",
  });

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  useEffect(() => {
    const id = searchParams.get("id");

    if (id) {
      setProductId(id);
    }
  }, [searchParams]);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      setLoading(true);
      setMessage("");

      const payload = {
        name: formData.name,
        price: Number(formData.price),
        stock: Number(formData.stock),
      };

      await updateProduct(productId, payload);

      setMessage("Product updated successfully.");
    } catch (error) {
      console.error(error);
      setMessage("Failed to update product.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h1>Update Product</h1>

      {message && <p>{message}</p>}

      <form onSubmit={handleSubmit}>
        <div>
          <label>Product ID: </label>
          <input
            type="number"
            value={productId}
            onChange={(e) => setProductId(e.target.value)}
            required
          />
        </div>

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
          {loading ? "Updating..." : "Update Product"}
        </button>
      </form>
    </div>
  );
}

export default UpdateProductPage;