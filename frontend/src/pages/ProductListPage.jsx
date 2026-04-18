import { useEffect, useMemo, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchProducts } from "../features/productSlice";
import { addProductToCart } from "../services/cartService";

function ProductListPage() {
  const dispatch = useDispatch();

  const { items, loading, error } = useSelector((state) => state.products);

  const [searchTerm, setSearchTerm] = useState("");
  const [maxPrice, setMaxPrice] = useState("");

  useEffect(() => {
    dispatch(fetchProducts());
  }, [dispatch]);

  const filteredProducts = useMemo(() => {
    return items.filter((product) => {
      const matchesSearch = product.name
        .toLowerCase()
        .includes(searchTerm.toLowerCase());

      const matchesPrice =
        maxPrice === "" || product.price <= Number(maxPrice);

      return matchesSearch && matchesPrice;
    });
  }, [items, searchTerm, maxPrice]);

  const handleAddToCart = async (productId) => {
    try {
      await addProductToCart(productId, 1);
      alert("Product added to cart successfully");
    } catch (error) {
      console.error("Error adding product to cart:", error);
      alert("Failed to add product to cart");
    }
  };

  if (loading) {
    return <h2>Loading products...</h2>;
  }

  if (error) {
    return <h2>Error: {error}</h2>;
  }

  return (
    <div>
      <h1>Product List</h1>

      <div>
        <input
          type="text"
          placeholder="Search by product name"
          value={searchTerm}
          onChange={(event) => setSearchTerm(event.target.value)}
        />

        <input
          type="number"
          placeholder="Filter by max price"
          value={maxPrice}
          onChange={(event) => setMaxPrice(event.target.value)}
        />
      </div>

      <table border="1" cellPadding="10" style={{ borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {filteredProducts.map((product) => (
            <tr key={product.id}>
              <td>{product.id}</td>
              <td>{product.name}</td>
              <td>{product.price}</td>
              <td>{product.stock}</td>
              <td>
                <button onClick={() => handleAddToCart(product.id)}>
                  Add to Cart
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ProductListPage;