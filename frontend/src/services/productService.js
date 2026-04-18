import axios from "axios";

const PRODUCT_BASE_URL = import.meta.env.VITE_PRODUCT_SERVICE_URL;

export const getAllProducts = async () => {
  const response = await axios.get(`${PRODUCT_BASE_URL}/products`);
  return response.data;
};

export const createProduct = async (productData) => {
  const response = await axios.post(`${PRODUCT_BASE_URL}/products`, productData);
  return response.data;
};