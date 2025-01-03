package Shop.com;

import java.sql.Timestamp;
import java.util.List;

public class Order {

    private int id;                       // 编号
    private Timestamp orderTime;          // 日期
    private List<OrderProduct> orderProducts; // 订单包含的商品及数量列表


    public Order(int id, Timestamp orderTime, List<OrderProduct> orderProducts) {
        this.id = id;
        this.orderTime = orderTime;
        this.orderProducts = orderProducts;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public Timestamp getOrderTime() {
        return orderTime;
    }


    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }


    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }


    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    // 计算总金额
    public double calculateTotalPrice() {
        if (orderProducts == null || orderProducts.isEmpty()) {
            return 0.0;
        }
        return orderProducts.stream()
                .mapToDouble(orderProduct -> orderProduct.getPrice().doubleValue() * orderProduct.getQuantity()) // 使用 getPrice() 代替 getProduct().getPrice()
                .sum();
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderTime=" + orderTime +
                ", orderProducts=" + orderProducts +
                ", totalPrice=" + calculateTotalPrice() +
                '}';
    }

    // 订单是否有效
    public boolean isValid() {
        return id > 0 && orderTime != null && orderProducts != null && !orderProducts.isEmpty();
    }
}
