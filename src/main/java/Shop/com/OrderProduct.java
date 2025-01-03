package Shop.com;

import java.math.BigDecimal;

public class OrderProduct {

    private int orderId;       // 订单编号
    private int productId;     // 商品编号
    private int quantity;      // 商品数量
    private BigDecimal price;  // 商品价格


    public OrderProduct(Product product, int quantity) {
        this.productId = product.getId();
        this.quantity = quantity;
        this.price = product.getPrice(); // 从 Product 获取价格
    }



    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        } else {
            throw new IllegalArgumentException("商品数量必须大于零");
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // 计算商品总价（单价 * 数量）
    public BigDecimal calculateTotalPrice() {
        return this.price.multiply(BigDecimal.valueOf(this.quantity));
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalPrice=" + calculateTotalPrice() +
                '}';
    }
}
