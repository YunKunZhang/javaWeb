package Dao;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministratorDaoImpl implements IAdministratorDao {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    //    管理员端登陆验证
    @Override
    public int selectAdministrator(String num, String password) {
        //返回0表示密码错误，返回1表示数据匹配，返回-1表示未注册,返回2表示后台异常
        boolean exist = ifExist(num);
        if (exist) {
            try {
                conn = JDBCUtils.getConnection();
                String sql = "select count(*) from administrator where ManagerNum=? and AES_DECRYPT(UNHEX(ManagerPassword),?)=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, num);
                ps.setString(2, num);
                ps.setString(3, password);
                rs = ps.executeQuery();

                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return 2;
            } finally {
                JDBCUtils.close1(conn, ps, rs);
            }
        }
        return -1;
    }

    //    管理员端密码修改
    @Override
    public int modifyAdministrator(String num, String password, String answer) {
        //返回-1表示该账号不存在,返回0表示密保答案错误，返回1表示修改成功,返回2表示后台异常
        boolean exist = ifExist(num);
        if (exist) {
            try {
                conn = JDBCUtils.getConnection();
                String sql = "update teacher set ManagerPassword=HEX(AES_ENCRYPT(?,?)) where ManagerNum=? and ManagerAnswer=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, password);
                ps.setString(2, num);
                ps.setString(3, num);
                ps.setString(4, answer);

                return ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 2;
            } finally {
                JDBCUtils.close2(conn, ps);
            }
        }
        return -1;
    }

    //    查询该账号是否存在
    @Override
    public boolean ifExist(String num) {
        boolean exist = false;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select count(*) from administrator where ManagerNum=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, num);
            rs = ps.executeQuery();

            if (rs.next()) {
                exist = rs.getInt(1) == 1 ? true : false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return exist;
    }
}
