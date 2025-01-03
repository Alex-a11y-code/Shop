package Shop.com;

import java.sql.Timestamp;
import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private BigDecimal price;
    private Timestamp createdAt;


    public Product(int id, String name, BigDecimal price, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
    }


    public Product() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", createdAt=" + createdAt +
                '}';
    }
}
