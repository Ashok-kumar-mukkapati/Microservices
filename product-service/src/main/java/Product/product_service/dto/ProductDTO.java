package Product.product_service.dto;

public class ProductDTO {

    private Integer id;
    private String name;
    private Double price;
    private Integer stock;
    private String availability;

    public ProductDTO() {
    }

    public ProductDTO(Integer id, String name, Double price, Integer stock, String availability) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.availability = availability;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}