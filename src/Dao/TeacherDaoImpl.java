package Dao;

import beans.Course;
import beans.Grade;
import beans.Student;
import beans.Teacher;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class TeacherDaoImpl implements ITeacherDao {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    //    教师端登陆验证
    @Override
    public Object selectTeacher(String num, String password) {
        boolean exsit = ifExist(num);
        //返回0表示密码错误，返回1表示数据匹配，返回-1表示未注册,返回2表示后台异常
        if (exsit) {
            try {
                conn = JDBCUtils.getConnection();
                String sql = "select TeacherName from teacher where TeacherNum=? and AES_DECRYPT(UNHEX(TeacherPassword),?)=?";
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

    //获取教师端信息

    @Override
    public Teacher getInfo(String num) {
        Teacher teacher = new Teacher();
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM vi_teacher WHERE TeacherNum=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, num);
            rs = ps.executeQuery();

            if (rs.next()) {
                teacher.setNum(rs.getString("TeacherNum"));
                teacher.setName(rs.getString("TeacherName"));
                teacher.setSex(rs.getString("TeacherSex"));
                teacher.setDepartment(rs.getString("DepName"));
                teacher.setBirthday(rs.getDate("TeacherBirthday"));
                teacher.setPhone(rs.getString("TeacherPhone"));
                teacher.setRank(rs.getString("TeacherRank"));
            }
            return teacher;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return null;
    }

    //修改教师端信息

    @Override
    public int modifyInfo(String[] info, String num) {
        //返回1表示修改成功,0表示失败
        try {
            conn = JDBCUtils.getConnection();
            String sql = "update Teacher set TeacherName=?,TeacherSex=?,DepNum=(SELECT DepNum FROM department WHERE DepName=?),TeacherBirthday=STR_TO_DATE(?,'%Y-%m-%d'),TeacherPhone=? where TeacherNum=?";
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
        } finally {
            JDBCUtils.close2(conn, ps);
        }
        return 0;
    }

    //    获取不同学期教师端用户所教授课程的学生总数

    @Override
    public int getNumber(String num, String semester) {
        //返回-1表示操作异常，返回0表示没有符合条件的信息
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT COUNT(*) FROM vi_numberofstudent WHERE teacherNum=? AND Semester=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, num);
            ps.setString(2, getSemester(semester));
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //    获取不同学期教师端用户所教授课程的信息

    @Override
    public Course[] getCourseInfo(String num, String semester) {
        int index = 0;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "CALL teacherCourse(?,?);";
            ps = conn.prepareStatement(sql);
            ps.setString(1, num);
            ps.setString(2, getSemester(semester));
            rs = ps.executeQuery();

            rs.last();
            Course[] arr = new Course[rs.getRow()];

            rs.beforeFirst();
            while (rs.next()) {
                Course course = new Course();
                course.setNum(rs.getString("CourseNum"));
                course.setName(rs.getString("CourseName"));
                course.setCredit(rs.getInt("CourseCredit"));
                course.setPeriod(rs.getInt("Period"));
                course.setVariety(rs.getString("VarietyName"));
                course.setTeachingMethod(rs.getString("TeachingMethod"));
                course.setEvaluationMode(rs.getString("EvaluationMode"));
                course.setTeacherName(rs.getString("TeacherName"));
                course.setPeople(rs.getInt("CoursePeople"), rs.getInt("CourseAllowed"));
                arr[index] = course;
                index++;
            }

            return arr;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return null;
    }

    //    获取不同学期教师端用户所教授课程的学生成绩信息

    @Override
    public Grade[] getGradeInfo(String num, String semester, int pageNum) {
        //一页15个
        int index = 0;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM vi_teacherscore where TeacherNum=? AND Semester=? LIMIT ?,?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, num);
            ps.setString(2, getSemester(semester));
            ps.setInt(3, (pageNum - 1) * 14);
            ps.setInt(4, pageNum * 14);

            rs = ps.executeQuery();

            rs.last();
            Grade[] arr = new Grade[rs.getRow()];

            rs.beforeFirst();
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setCourseName(rs.getString("CourseName"));
                grade.setStuNum(rs.getString("StudentNum"));
                grade.setStuName(rs.getString("StudentName"));
                grade.setCredit(rs.getInt("CourseCredit"));
                grade.setVariety(rs.getString("VarietyName"));
                grade.setEvaluationMode(rs.getString("EvaluationMode"));
                grade.setCharacter(rs.getString("Character"));
                grade.setScore(rs.getInt("Grade"));
                arr[index] = grade;
                index++;
            }

            return arr;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return null;
    }

    //    修改不同学期教师端用户所教授课程的学生成绩信息

    @Override
    public int modifyGradeInfo(String semester, String courseName, String stuNum, String score) {
        //返回-1表示后台异常
        try {
            conn = JDBCUtils.getConnection();
            String sql = "UPDATE \n" +
                    "  election \n" +
                    "  INNER JOIN course \n" +
                    "    ON course.`CourseNum` = election.`CourseNum` \n" +
                    "  INNER JOIN student \n" +
                    "    ON election.`StudentNum` = student.`StudentNum` SET Grade = ? \n" +
                    "WHERE Semester = ?\n" +
                    "  AND courseName = ? \n" +
                    "  AND election.studentNum = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,score);
            ps.setString(2,semester);
            ps.setString(3,courseName);
            ps.setString(4,stuNum);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //    教师端密码修改
    @Override
    public int modifyPassword(String num, String password, String answer) {
        //返回-1表示该账号不存在,返回0表示密保答案错误，返回1表示修改成功,返回2表示后台异常
        boolean exist = ifExist(num);
        if (exist) {
            try {
                conn = JDBCUtils.getConnection();
                String sql = "update teacher set TeacherPassword=HEX(AES_ENCRYPT(?,?)) where TeacherNum=? and TeacherAnswer=?";
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

    //    修改教师端用户密保答案

    @Override
    public int modifyAnswer(String num, String oldAnswer, String newAnswer) {
        //返回-1表示异常，返回0表示原密保错误答案错误，返回1表示修改成功
        boolean flag = ifChanged(num);
        if (flag) {
            try {
                conn = JDBCUtils.getConnection();
                String sql = "UPDATE teacher SET TeacherAnswer=?,IfChange=FALSE WHERE TeacherNum=? AND IfChange=FALSE AND TeacherAnswer=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, newAnswer);
                ps.setString(2, num);
                ps.setString(3, oldAnswer);

                return ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            } finally {
                JDBCUtils.close2(conn, ps);
            }
        }
        return 0;
    }

    //    查看密保答案是否修改过

    @Override
    public boolean ifChanged(String num) {
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT ifchange FROM teacher where TeacherNum=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, num);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("IfChange");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return false;
    }

    //    查询该账号是否存在
    @Override
    public boolean ifExist(String num) {
        boolean exist = false;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "CALL existTeacher(?)";
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

    //     获取学期

    @Override
    public String getSemester(String semester) {
        if (semester == null) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            semester = month < 7 ? (year - 1) + "-" + year + "学年第二学期" : year + "-" + (year + 1) + "学年第一学期";
        }
        return semester;
    }
}
