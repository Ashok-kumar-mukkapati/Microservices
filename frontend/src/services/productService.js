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

export const getPagedProducts = async (page = 0, size = 3) => {
  const response = await axios.get(`${PRODUCT_BASE_URL}/products/paged?page=${page}&size=${size}`);
  return response.data;
};

export const updateProduct = async (id, productData) => {
  const response = await axios.put(`${PRODUCT_BASE_URL}/products/${id}`, productData);
  return response.data;
};

export const deleteProduct = async (id) => {
  const response = await axios.delete(`${PRODUCT_BASE_URL}/products/${id}`);
  return response.data;
};