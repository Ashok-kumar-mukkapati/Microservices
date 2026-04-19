import axios from "axios";

const CART_BASE_URL = import.meta.env.VITE_CART_SERVICE_URL;

export const getAllCartItems = async () => {
  const response = await axios.get(`${CART_BASE_URL}/carts/items`);
  return response.data;
};

export const addCartItem = async (cartItemData) => {
  const response = await axios.post(`${CART_BASE_URL}/carts/items`, cartItemData);
  return response.data;
};

export const addProductToCart = async (cartId, productId, quantity = 1) => {
  const payload = {
    cart: {
      id: Number(cartId),
    },
    productId,
    quantity,
  };

  const response = await axios.post(`${CART_BASE_URL}/carts/items`, payload);
  return response.data;
};

export const getAllCarts = async () => {
  const response = await axios.get(`${CART_BASE_URL}/carts`);
  return response.data;
};

export const deleteCartItem = async (itemId) => {
  const response = await axios.delete(`${CART_BASE_URL}/carts/items/${itemId}`);
  return response.data;
};