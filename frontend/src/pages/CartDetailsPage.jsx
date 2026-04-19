import { useParams, Link } from "react-router-dom";
import useCartDetails from "../hooks/useCartDetails";

function CartDetailsPage() {
  const { id } = useParams();

  const {
    cart,
    loading,
    error,
    handleDeleteItem,
  } = useCartDetails(id);

  const onDeleteItem = async (itemId, availableQuantity) => {
    const quantityToRemove = window.prompt("Enter quantity to remove from cart:");

    if (!quantityToRemove) return;

    const parsedQuantity = Number(quantityToRemove);

    if (Number.isNaN(parsedQuantity) || parsedQuantity <= 0) {
      alert("Please enter a valid quantity greater than 0.");
      return;
    }

    if (parsedQuantity > availableQuantity) {
      alert(
        `Only ${availableQuantity} quantity available in cart. Please enter correct quantity`
      );
      return;
    }

    const result = await handleDeleteItem(itemId, parsedQuantity);
    alert(result.message);
  };

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
            <th>Product Name</th>
            <th>Quantity</th>
            <th>Delete</th>
          </tr>
        </thead>

        <tbody>
          {cart.items && cart.items.length > 0 ? (
            cart.items.map((item) => (
              <tr key={item.id}>
                <td>{item.id}</td>
                <td>{item.productId}</td>
                <td>{item.productName}</td>
                <td>{item.quantity}</td>
                <td>
                  <button onClick={() => onDeleteItem(item.id, item.quantity)}>
                    Delete Item
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="5">No items found in this cart.</td>
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