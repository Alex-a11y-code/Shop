package Shop.com;

import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcUtils {

    private static final Logger logger = LoggerFactory.getLogger(JdbcUtils.class);
    private static final String URL = "jdbc:mysql://localhost:3306/shop";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Failed to load JDBC driver: " + e.getMessage());
        }
    }

    // 数据库连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 关闭资源
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            logger.error("Failed to close ResultSet", e);
        }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            logger.error("Failed to close Statement", e);
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            logger.error("Failed to close Connection", e);
        }
    }

    // 开始事务
    public static void beginTransaction(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.setAutoCommit(false);
        }
    }


    public static void commitTransaction(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.commit();
        }
    }


    public static void rollbackTransaction(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.rollback();
        }
    }
}
