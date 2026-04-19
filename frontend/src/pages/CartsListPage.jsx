import { useState } from "react";
import { useNavigate } from "react-router-dom";
import useCarts from "../hooks/useCarts";

function CartsListPage() {
  const navigate = useNavigate();
  const { carts, loading, error, handleCreateCart, handleDeleteCart } = useCarts();

  const [userId, setUserId] = useState("");

  const onCreateCart = async () => {
    if (!userId) {
      alert("Please enter a user ID.");
      return;
    }

    const result = await handleCreateCart(userId);
    alert(result.message);

    if (result.success) {
      setUserId("");
    }
  };

  const onDeleteCart = async (cartId) => {
    const confirmDelete = window.confirm(
      `Are you sure you want to delete cart ${cartId}?`
    );

    if (!confirmDelete) return;

    const result = await handleDeleteCart(cartId);
    alert(result.message);
  };

  const handleViewItems = (cartId) => {
    navigate(`/cart-details/${cartId}`);
  };

  if (loading) {
    return <h2>Loading carts...</h2>;
  }

  if (error) {
    return <h2>{error}</h2>;
  }

  return (
    <div>
      <h1>Available Carts</h1>

      <div>
        <input
          type="number"
          placeholder="Enter user ID"
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
        />
        <button onClick={onCreateCart}>Create New Cart</button>
      </div>

      <br />

      <table border="1" cellPadding="10" style={{ borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th>Cart ID</th>
            <th>User ID</th>
            <th>Item Count</th>
            <th>View</th>
            <th>Delete</th>
          </tr>
        </thead>

        <tbody>
          {carts.map((cart) => (
            <tr key={cart.id}>
              <td>{cart.id}</td>
              <td>{cart.userId}</td>
              <td>{cart.items ? cart.items.length : 0}</td>
              <td>
                <button onClick={() => handleViewItems(cart.id)}>
                  View Items
                </button>
              </td>
              <td>
                <button onClick={() => onDeleteCart(cart.id)}>
                  Delete Cart
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default CartsListPage;