package Shop.com.tool;

import Shop.com.Interface.productDao;
import Shop.com.JdbcUtils;
import Shop.com.Product;
import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;

public class ProductDaoImpl implements productDao {


    @Override
    public ArrayList<Product> findAll() {
        // 创建存储商品对象的列表
        ArrayList<Product> products = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM products";
            rs = stmt.executeQuery(sql);
            // 遍历查询结果并将每一条记录转换为 Product 对象
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                BigDecimal price = rs.getBigDecimal("price");
                Timestamp createdAt = rs.getTimestamp("created_at");

                Product product = new Product(id, name, price, createdAt);
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, stmt, rs);
        }

        return products;
    }


    @Override
    public Product findById(int id) {
        Product product = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();
            String sql = "SELECT * FROM products WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            // 若查询到商品，创建 Product 对象并返回
            if (rs.next()) {
                int productId = rs.getInt("id");
                String name = rs.getString("name");
                BigDecimal price = rs.getBigDecimal("price");
                Timestamp createdAt = rs.getTimestamp("created_at");

                product = new Product(productId, name, price, createdAt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, stmt, rs);
        }

        return product;
    }


    @Override
    public int insert(Product product) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;

        try {
            conn = JdbcUtils.getConnection();
            String sql = "INSERT INTO products (name, price, created_at) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getName());
            stmt.setBigDecimal(2, product.getPrice());
            stmt.setTimestamp(3, product.getCreatedAt());
            result = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, stmt, null);
        }

        return result;
    }

    // 更新商品
    @Override
    public int update(Product product) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;

        try {
            conn = JdbcUtils.getConnection();
            String sql = "UPDATE products SET name = ?, price = ?, created_at = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getName());
            stmt.setBigDecimal(2, product.getPrice());
            stmt.setTimestamp(3, product.getCreatedAt());
            stmt.setInt(4, product.getId());
            result = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, stmt, null);
        }

        return result;
    }


    @Override
    public int delete(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int result = 0;

        try {
            conn = JdbcUtils.getConnection();
            String sql = "DELETE FROM products WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            result = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, stmt, null);
        }

        return result;
    }
}
