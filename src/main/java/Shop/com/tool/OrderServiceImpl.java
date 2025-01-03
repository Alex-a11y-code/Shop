package Shop.com.tool;

import Shop.com.Interface.orderService;
import Shop.com.JdbcUtils;
import Shop.com.Order;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements orderService {

    private OrderDaoImpl orderDao;// OrderDaoImpl 实例，用于操作订单数据


    public OrderServiceImpl() {
        this.orderDao = new OrderDaoImpl();
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Order findById(int id) {
        return orderDao.findById(id);
    }

    @Override
    public int insert(Order order) {
        Connection conn = null;
        int result = 0;
        try {
            conn = JdbcUtils.getConnection();
            JdbcUtils.beginTransaction(conn);// 开始事务
            result = orderDao.insert(order);
            JdbcUtils.commitTransaction(conn);
        } catch (SQLException e) {
            // 异常处理，回滚事务
            if (conn != null) {
                try {
                    JdbcUtils.rollbackTransaction(conn);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, null, null);
        }
        return result;
    }

    @Override
    public int update(Order order) {
        Connection conn = null;
        int result = 0;
        try {
            conn = JdbcUtils.getConnection();
            JdbcUtils.beginTransaction(conn);
            result = orderDao.update(order);
            JdbcUtils.commitTransaction(conn);
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    JdbcUtils.rollbackTransaction(conn);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, null, null);
        }
        return result;
    }

    @Override
    public int delete(int id) {
        Connection conn = null;
        int result = 0;
        try {
            conn = JdbcUtils.getConnection();
            JdbcUtils.beginTransaction(conn);
            result = orderDao.delete(id);
            JdbcUtils.commitTransaction(conn);
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    JdbcUtils.rollbackTransaction(conn);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, null, null);
        }
        return result;
    }

}
