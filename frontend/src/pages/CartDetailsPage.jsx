import { useParams, Link } from "react-router-dom";
import useCartDetails from "../hooks/useCartDetails";

function CartDetailsPage() {
  const { id } = useParams();
  const { cart, loading, error } = useCartDetails(id);

  if (loading) {
    return <h2>Loading cart details...</h2>;
  }

  if (error) {
    return <h2>{error}</h2>;
  }

  return (
    <div>
      <h1>Cart Details</h1>

      <p><strong>Cart ID:</strong> {cart.id}</p>
      <p><strong>User ID:</strong> {cart.userId}</p>

      <table border="1" cellPadding="10" style={{ borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th>Item ID</th>
            <th>Product ID</th>
            <th>Quantity</th>
          </tr>
        </thead>

        <tbody>
          {cart.items && cart.items.length > 0 ? (
            cart.items.map((item) => (
              <tr key={item.id}>
                <td>{item.id}</td>
                <td>{item.productId}</td>
                <td>{item.quantity}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="3">No items found in this cart.</td>
            </tr>
          )}
        </tbody>
      </table>

      <br />

      <Link to="/carts">Back to Carts</Link>
    </div>
  );
}

export default CartDetailsPage;