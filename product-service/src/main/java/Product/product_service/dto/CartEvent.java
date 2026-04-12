package Product.product_service.dto;

public class CartEvent {

    private Integer cartId;
    private Integer productId;
    private Integer quantity;
    private String eventType;

    public CartEvent() {
    }

    public CartEvent(Integer cartId, Integer productId, Integer quantity, String eventType) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
        this.eventType = eventType;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "CartEvent{" +
                "cartId=" + cartId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}