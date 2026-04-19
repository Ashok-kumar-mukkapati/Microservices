import { useNavigate } from "react-router-dom";
import useProducts from "../hooks/useProducts";

function ProductListPage() {
  const navigate = useNavigate();

  const {
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
  } = useProducts();

  const onAddToCart = async (productId) => {
    const result = await handleAddToCart(productId);
    alert(result.message);
  };

  const onDeleteProduct = async (productId) => {
    const confirmDelete = window.confirm(
      `Are you sure you want to delete product ${productId}?`
    );

    if (!confirmDelete) return;

    const result = await handleDeleteProduct(productId);
    alert(result.message);
  };

  const onUpdateProduct = (productId) => {
    navigate(`/update-product?id=${productId}`);
  };

  if (loading) return <h2>Loading products...</h2>;
  if (error) return <h2>{error}</h2>;

  return (
    <div>
      <h1>Product List</h1>

      <input
        type="text"
        placeholder="Search by product name"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />

      <input
        type="number"
        placeholder="Filter by max price"
        value={maxPrice}
        onChange={(e) => setMaxPrice(e.target.value)}
      />

      <select
        value={pageSize}
        onChange={(e) => {
          setPageSize(Number(e.target.value));
          setPage(0);
        }}
      >
        <option value={3}>3 per page</option>
        <option value={5}>5 per page</option>
        <option value={10}>10 per page</option>
      </select>

      <div>
        <label>Select Cart: </label>
        <select
          value={selectedCartId}
          onChange={(e) => setSelectedCartId(e.target.value)}
        >
          {availableCarts.map((cart) => (
            <option key={cart.id} value={cart.id}>
              Cart {cart.id} - User {cart.userId}
            </option>
          ))}
        </select>
      </div>

      <table border="1" cellPadding="10" style={{ borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Add</th>
            <th>Update</th>
            <th>Delete</th>
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
                <button onClick={() => onAddToCart(product.id)}>
                  Add to Cart
                </button>
              </td>

              <td>
                <button onClick={() => onUpdateProduct(product.id)}>
                  Update
                </button>
              </td>

              <td>
                <button onClick={() => onDeleteProduct(product.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <br />

      <button onClick={() => setPage(page - 1)} disabled={page === 0}>
        Prev
      </button>

      <span> Page {page + 1} of {totalPages} </span>

      <button
        onClick={() => setPage(page + 1)}
        disabled={page + 1 >= totalPages}
      >
        Next
      </button>
    </div>
  );
}

export default ProductListPage;