import org.junit.jupiter.api.*;
import Shop.com.Order;
import Shop.com.OrderProduct;
import Shop.com.Product;
import Shop.com.tool.OrderDaoImpl;
import Shop.com.tool.ProductDaoImpl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleSystemTest {

    private static ProductDaoImpl productDao;
    private static OrderDaoImpl orderDao;

    @BeforeAll
    static void setUp() {
        productDao = new ProductDaoImpl();
        orderDao = new OrderDaoImpl();
    }

    @Test
    @DisplayName("Add Product")
    void testAddProduct() {
        int result = productDao.insert(new Product(0, "Laptop", BigDecimal.valueOf(1200), null));
        assertTrue(result > 0, "Product should be added.");
    }

    @Test
    @DisplayName("Find Product")
    void testFindProduct() {
        Product product = productDao.findById(1);
        assertNotNull(product, "Product should exist.");
    }

    @Test
    @DisplayName("Delete Product")
    void testDeleteProduct() {
        int result = productDao.delete(1);
        assertTrue(result > 0, "Product should be deleted.");
    }

    @Test
    @DisplayName("Add Order")
    void testAddOrder() {
        // 创建产品
        Product product = new Product(1, "Laptop", BigDecimal.valueOf(1200), null);
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(new OrderProduct(product, 1));

        // 创建订单
        Order order = new Order(0, new Timestamp(System.currentTimeMillis()), orderProducts);
        int result = orderDao.insert(order);
        assertTrue(result > 0, "Order should be added.");
    }

    @Test
    @DisplayName("Find Order")
    void testFindOrder() {
        Order order = orderDao.findById(1);
        assertNotNull(order, "Order should exist.");
    }
}
