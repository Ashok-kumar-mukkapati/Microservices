# Microservices Frontend (React + Vite)

Frontend UI for the Microservices Assignment.

## Tech Stack

- React
- Vite
- Redux Toolkit
- React Redux
- Axios

## Services Used

- Product Service → http://localhost:8081
- Cart Service → http://localhost:8082

## Features Completed

### 2A - Setup
- React app created using Vite
- Project folder structure created
- Environment variables configured

### 2B - Product UI
- Add Product form
- Product List page
- Fetch products from backend
- Create product from UI

### 2C - Redux
- Redux store configured
- Product slice created
- Async thunk for products API
- Product list uses Redux state

### 2D - Cart UI + Integration
- Cart Page created
- Add to Cart button
- UI calls Cart API
- Cart items displayed

### 2E - Loading + Error Handling
- Product List loading/error state
- Cart Page loading/error state
- Add Product submit loading/error state

### 2F - Search + Filter
- Search by product name
- Filter products by max price
- useMemo used for optimized filtering
- Real-time filtered product list

### 2G - Pagination UI
- Backend pagination API integrated
- Prev / Next navigation added
- Current page indicator displayed
- Dynamic page size selector (3 / 5 / 10)
- Product list updates based on page and selected size

### 2H - Routing
- React Router v6 integrated
- Products, Add Product, Cart routes created
- Navigation without page reload
- Default route redirects to /products
- Future flags enabled for React Router v7 compatibility

### 2I - Custom Hooks + Clean Architecture
- Created custom hooks: useProducts, useCart, useAddProduct
- Moved page logic out of UI components
- Components now focus on rendering only
- Services handle API communication
- Redux handles product state
- Hooks handle reusable UI logic

### Additional Product Management Enhancements
- Added Update Product page
- Added Update action from Product List
- Added Delete action from Product List
- Product delete flow integrated with backend API

### Additional Cart Browsing Enhancements
- Added Carts List page to display available carts
- Added Cart Details page to show items for a selected cart
- Each cart shows cart id, user id, and item count
- View Items navigation added from cart list to selected cart details

## Run Project

```bash
npm install
npm run dev