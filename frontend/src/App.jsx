import { Link, Routes, Route, Navigate } from "react-router-dom";
import AddProductPage from "./pages/AddProductPage";
import ProductListPage from "./pages/ProductListPage";
import UpdateProductPage from "./pages/UpdateProductPage";
import CartsListPage from "./pages/CartsListPage";
import CartDetailsPage from "./pages/CartDetailsPage";

function App() {
  return (
    <div>
      <h1>Microservices Frontend</h1>

      <nav>
        <Link to="/products">Products</Link> |{" "}
        <Link to="/add-product">Add Product</Link> |{" "}
        <Link to="/update-product">Update Product</Link> |{" "}
        <Link to="/carts">Cart</Link>
      </nav>

      <hr />

      <Routes>
        <Route path="/" element={<Navigate to="/products" />} />
        <Route path="/products" element={<ProductListPage />} />
        <Route path="/add-product" element={<AddProductPage />} />
        <Route path="/update-product" element={<UpdateProductPage />} />
        <Route path="/carts" element={<CartsListPage />} />
        <Route path="/cart-details/:id" element={<CartDetailsPage />} />
      </Routes>
    </div>
  );
}

export default App;