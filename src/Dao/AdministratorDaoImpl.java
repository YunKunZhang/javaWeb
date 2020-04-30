package Dao;

import beans.Administrator;
import beans.Teacher;
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
    public Object selectAdministrator(String num, String password) {
        //返回0表示密码错误，返回1表示数据匹配，返回-1表示未注册,返回2表示后台异常
        boolean exist = ifExist(num);
        if (exist) {
            try {
                conn = JDBCUtils.getConnection();
                String sql = "select ManagerName from administrator where ManagerNum=? and AES_DECRYPT(UNHEX(ManagerPassword),?)=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, num);
                ps.setString(2, num);
                ps.setString(3, password);
                rs = ps.executeQuery();

                if (rs.next()) {
                    return rs.getString(1);
                }
                return 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return 2;
            } finally {
                JDBCUtils.close1(conn, ps, rs);
            }
        }
        return -1;
    }

    //获取管理员信息

    @Override
    public Administrator getInfo(String num) {
        Administrator administrator = new Administrator();
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM vi_administrator WHERE ManagerNum=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, num);
            rs = ps.executeQuery();

            if (rs.next()) {
                administrator.setNum(rs.getString("ManagerNum"));
                administrator.setName(rs.getString("ManagerName"));
                administrator.setSex(rs.getString("ManagerSex"));
                administrator.setBirthday(rs.getDate("ManagerBirthday"));
                administrator.setPhone(rs.getString("ManagerPhone"));
                administrator.setIdentity(rs.getString("SuperManager"));
            }
            return administrator;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return null;
    }

    //修改管理员信息

    @Override
    public int modifyInfo(String[] info, String num) {
        //返回1表示修改成功,0表示失败
        try {
            conn = JDBCUtils.getConnection();
            String sql = "update administrator set ManagerName=?,ManagerSex=?,SuperManager=?,ManagerBirthday=STR_TO_DATE(?,'%Y-%m-%d'),ManagerPhone=? where ManagerNum=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, info[0]);
            ps.setString(2, info[1]);
            ps.setString(3, info[2]);
            ps.setString(4, info[3]);
            ps.setString(5, info[4]);
            ps.setString(6, num);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close2(conn,ps);
        }
        return 0;
    }

    //    管理员端密码修改
    @Override
    public int modifyPassword(String num, String password, String answer) {
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
            String sql = "CALL existAdministrator(?)";
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
