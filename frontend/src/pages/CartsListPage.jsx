import { useNavigate } from "react-router-dom";
import useCarts from "../hooks/useCarts";

function CartsListPage() {
  const navigate = useNavigate();
  const { carts, loading, error } = useCarts();

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

      <table border="1" cellPadding="10" style={{ borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th>Cart ID</th>
            <th>User ID</th>
            <th>Item Count</th>
            <th>Action</th>
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
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default CartsListPage;