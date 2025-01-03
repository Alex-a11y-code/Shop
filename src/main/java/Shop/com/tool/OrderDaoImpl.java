package Shop.com.tool;

import Shop.com.Interface.orderDao;
import Shop.com.JdbcUtils;
import Shop.com.Order;
import Shop.com.OrderProduct;
import Shop.com.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements orderDao {

    // 查询所有订单
    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, order_time FROM orders ORDER BY order_time";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();  // 获取数据库连接
            stmt = conn.prepareStatement(sql);  // 创建预处理语句
            rs = stmt.executeQuery();  // 执行查询

            // 遍历查询结果并构建订单对象
            while (rs.next()) {
                int id = rs.getInt("id");
                Timestamp orderTime = rs.getTimestamp("order_time");
                List<OrderProduct> orderProducts = getOrderProductsByOrderId(id);

                // 创建并添加订单对象到列表中
                orders.add(new Order(id, orderTime, orderProducts));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, stmt, rs);
        }

        return orders;
    }

    // 根据ID查找订单
    @Override
    public Order findById(int id) {
        Order order = null;
        String sql = "SELECT id, order_time FROM orders WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            // 如果查询到订单.则构建对象
            if (rs.next()) {
                Timestamp orderTime = rs.getTimestamp("order_time");
                List<OrderProduct> orderProducts = getOrderProductsByOrderId(id);

                order = new Order(id, orderTime, orderProducts);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, stmt, rs);
        }

        return order;
    }

    // 插入订单
    @Override
    public int insert(Order order) {
        String sql = "INSERT INTO orders (order_time) VALUES (?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            conn = JdbcUtils.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setTimestamp(1, order.getOrderTime());
            int rows = stmt.executeUpdate();

            // 获取生成的订单ID并插入订单商品信息
            if (rows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    insertOrderProducts(orderId, order.getOrderProducts());
                }
            }

            return rows;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, stmt, generatedKeys);
        }

        return 0;
    }

    // 更新订单
    @Override
    public int update(Order order) {
        String sql = "UPDATE orders SET order_time = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = JdbcUtils.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, order.getOrderTime());
            stmt.setInt(2, order.getId());
            int rows = stmt.executeUpdate();

            //更新订单商品信息
            if (rows > 0) {
                deleteOrderProducts(order.getId());
                insertOrderProducts(order.getId(), order.getOrderProducts());
            }

            return rows;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, stmt, null);
        }

        return 0;
    }

    // 删除订单
    @Override
    public int delete(int id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = JdbcUtils.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            deleteOrderProducts(id);

            return stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, stmt, null);
        }

        return 0;
    }

    // 根据订单ID获取订单中的商品信息
    private List<OrderProduct> getOrderProductsByOrderId(int orderId) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.price, op.quantity " +
                "FROM products p " +
                "JOIN order_product op ON p.id = op.product_id " +
                "WHERE op.order_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            rs = stmt.executeQuery();

            //构建订单商品对象
            while (rs.next()) {
                int productId = rs.getInt("id");
                String name = rs.getString("name");
                BigDecimal price = rs.getBigDecimal("price");
                int quantity = rs.getInt("quantity");


                Product product = new Product(productId, name, price, null);
                orderProducts.add(new OrderProduct(product, quantity));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, stmt, rs);
        }

        return orderProducts;
    }

    // 插入订单商品信息
    private void insertOrderProducts(int orderId, List<OrderProduct> orderProducts) {
        String sql = "INSERT INTO order_product (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = JdbcUtils.getConnection();
            stmt = conn.prepareStatement(sql);


            for (OrderProduct orderProduct : orderProducts) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, orderProduct.getProductId());
                stmt.setInt(3, orderProduct.getQuantity());
                stmt.setBigDecimal(4, orderProduct.getPrice());
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, stmt, null);
        }
    }

    // 删除订单商品信息
    private void deleteOrderProducts(int orderId) {
        String sql = "DELETE FROM order_product WHERE order_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = JdbcUtils.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, stmt, null);
        }
    }
}
