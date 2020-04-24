package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    private static DataSource ds;
    static {
        try {
            Properties pro = new Properties();
            pro.load(JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void close1(Connection conn, PreparedStatement state, ResultSet result) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
        if (state != null) {
            try {
                state.close();
            } catch (SQLException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
        if (result != null) {
            try {
                result.close();
            } catch (SQLException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
    }

    public static void close2(Connection conn, PreparedStatement state) {
        close1(conn, state, null);
    }

}