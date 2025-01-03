package Shop.com.Interface;

import Shop.com.Order;
import java.util.List;

public interface orderDao {
    List<Order> findAll();
    Order findById(int id);
    int insert(Order order);
    int update(Order order);
    int delete(int id);
}
