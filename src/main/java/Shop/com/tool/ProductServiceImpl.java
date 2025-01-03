package Shop.com.tool;

import Shop.com.Interface.productService;
import Shop.com.JdbcUtils;
import Shop.com.Product;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductServiceImpl implements productService {

    private ProductDaoImpl productDao; // ProductDaoImpl 实例，用于操作商品数据


    public ProductServiceImpl(ProductDaoImpl productDao) {
        this.productDao = productDao;
    }


    @Override
    public ArrayList<Product> findAll() {
        return productDao.findAll();
    }


    @Override
    public Product findById(int id) {
        return productDao.findById(id);
    }


    @Override
    public int insert(Product product) {
        Connection conn = null;
        int result = 0;
        try {
            conn = JdbcUtils.getConnection();
            JdbcUtils.beginTransaction(conn);
            result = productDao.insert(product);
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
    public int update(Product product) {
        Connection conn = null;
        int result = 0;
        try {
            conn = JdbcUtils.getConnection();
            JdbcUtils.beginTransaction(conn);
            result = productDao.update(product);
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
            result = productDao.delete(id);
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
