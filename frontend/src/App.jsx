import { Link, Routes, Route, Navigate } from "react-router-dom";
import AddProductPage from "./pages/AddProductPage";
import ProductListPage from "./pages/ProductListPage";
import CartPage from "./pages/CartPage";

function App() {
  return (
    <div>
      <h1>Microservices Frontend</h1>

      <nav>
        <Link to="/products">Products</Link> |{" "}
        <Link to="/add-product">Add Product</Link> |{" "}
        <Link to="/cart">Cart</Link>
      </nav>

      <hr />

      <Routes>
        <Route path="/" element={<Navigate to="/products" />} />
        <Route path="/products" element={<ProductListPage />} />
        <Route path="/add-product" element={<AddProductPage />} />
        <Route path="/cart" element={<CartPage />} />
      </Routes>
    </div>
  );
}

export default App;