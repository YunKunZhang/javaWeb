package Dao;

import beans.Student;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDaoImpl implements IStudentDao {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    //    学生端登陆验证

    @Override
    public Object selectStudent(String num, String password) {
        boolean exsit = ifExist(num);
        //返回0表示密码错误，返回1表示数据匹配，返回-1表示未注册,返回2表示后台异常
        if (exsit) {
            try {
                conn = JDBCUtils.getConnection();
                String sql = "select StudentName from student where StudentNum=? and AES_DECRYPT(UNHEX(StudentPassword),?)=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, num);
                ps.setString(2, num);
                ps.setString(3, password);
                rs = ps.executeQuery();

                if (rs.next()) {
                    return rs.getString(1);
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
    //    学生端密码修改

    @Override
    public int modifyPassword(String num, String password, String answer) {
        //返回-1表示该账号不存在,返回0表示密保答案错误，返回1表示修改成功,返回2表示后台异常
        boolean exist = ifExist(num);
        if (exist) {
            try {
                conn = JDBCUtils.getConnection();
                String sql = "update student set StudentPassword=HEX(AES_ENCRYPT(?,?)) where StudentNum=? and StudentAnswer=?";
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

    //修改学生信息

    @Override
    public int modifyInfo(String[] info,String num) {
        //返回1表示修改成功,0表示失败
        try {
            conn = JDBCUtils.getConnection();
            String sql = "update student set StudentName=?,StudentSex=?,MajorNum=(SELECT majorNum FROM major WHERE majorName=?),StudentBirthday=STR_TO_DATE(?,'%Y-%m-%d'),StudentPhone=? where StudentNum=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,info[0]);
            String sex=info[1].equals("男")? "1":"0";
            ps.setString(2,sex);
            ps.setString(2,info[1]);
            ps.setString(3,info[3]);
            ps.setString(4,info[5]);
            ps.setString(5,info[4]);
            ps.setString(6,num);

            System.out.println("修改");
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //      获取学生端用户信息

    @Override
    public Student getStudentInfo(String num) {
        Student student = new Student();
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM vi_student WHERE StudentNum=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, num);
            rs = ps.executeQuery();

            if (rs.next()) {
                student.setNum(rs.getString("StudentNum"));
                student.setName(rs.getString("StudentName"));
                student.setSex(rs.getString("StudentSex"));
                student.setDepartment(rs.getString("DepName"));
                student.setMajor(rs.getString("MajorName"));
                student.setBirthday(rs.getDate("StudentBirthday"));
                student.setEnterDate(rs.getDate("StudentEnterDate"));
                student.setPhone(rs.getString("StudentPhone"));
            }
            return student;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //    查询该账号是否存在
    public boolean ifExist(String num) {
        boolean exist = false;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select count(*) from student where StudentNum=?";
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
