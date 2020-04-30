package Dao;

import beans.Course;
import beans.Student;
import utils.JDBCUtils;

import java.sql.*;
import java.util.Calendar;

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

    //      获取学生端用户信息

    @Override
    public Student getInfo(String num) {
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
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return null;
    }

    //      修改学生信息

    @Override
    public int modifyInfo(String[] info, String num) {
        //返回1表示修改成功,0表示失败
        try {
            conn = JDBCUtils.getConnection();
            String sql = "update student set StudentName=?,StudentSex=?,MajorNum=(SELECT majorNum FROM major WHERE majorName=?),StudentBirthday=STR_TO_DATE(?,'%Y-%m-%d'),StudentPhone=? where StudentNum=?";
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

    //    获取学生端课程信息

    @Override
    public Course[] getCourseInfo(String num, String semester) {
        int index = 0;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from vi_stucourse where StudentNum=? and Semester=?";
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

    //    获取可选课程总数

    @Override
    public int getOptionalNumber() {
        //返回0表示未在正选时间，返回-1表示异常，返回其他数字表示可选的课程数目
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM vi_numberofOptional;";
            Statement state = conn.createStatement();
            rs = state.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("optionalNumber");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, null, rs);
        }
        return -1;
    }

    //    根据课程获取可选课程总数

    @Override
    public int getQueryOptionalNumber(String num, String condition1, String condition2, String input) {
        try {
            conn = JDBCUtils.getConnection();
            String sql;
            if (condition2 == null && input == null) {
                sql = "SELECT count(*) FROM vi_querycourse WHERE StudentNum=? AND CourseAllowed>CoursePeople";
            } else {
                sql = "SELECT count(*) FROM vi_querycourse WHERE StudentNum=? AND ";
                if ("精确".equals(condition2)) {
                    if ("序号".equals(condition1)) {
                        sql = sql.concat("num=?");
                    } else if ("课程".equals(condition1)) {
                        sql = sql.concat("CourseName=?");
                    } else {
                        sql = sql.concat("varietyName=?");
                    }
                } else if ("模糊".equals(condition2)) {
                    if ("序号".equals(condition1)) {
                        sql = sql.concat("num like ?");
                    } else if ("课程".equals(condition1)) {
                        sql = sql.concat("CourseName like ?");
                    } else {
                        sql = sql.concat("varietyName like ?");
                    }
                }
            }

            ps = conn.prepareStatement(sql);
            ps.setString(1, num);
            if (condition2 != null && input != null) {
                if ("精确".equals(condition1)) {
                    ps.setString(2, input);
                } else {
                    ps.setString(2, "%" + input + "%");
                }
            }

            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //    根据分页条件获取学生端可选课程的信息(普通分页查询)

    @Override
    public Course[] getOptional(String num, int pageNum) {
        //每页显示15个
        int index = 0;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "CALL getOptional(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, num);
            ps.setInt(2, (pageNum - 1) * 15);
            ps.setInt(3, pageNum * 15);
            rs = ps.executeQuery();

            rs.last();
            Course[] arr = new Course[rs.getRow()];

            rs.beforeFirst();
            while (rs.next()) {
                Course course = new Course();
                course.setNum(rs.getString("num"));
                course.setName(rs.getString("CourseName"));
                course.setCredit(rs.getInt("CourseCredit"));
                course.setPeriod(rs.getInt("Period"));
                course.setVariety(rs.getString("VarietyName"));
                course.setTeachingMethod(rs.getString("TeachingMethod"));
                course.setEvaluationMode(rs.getString("EvaluationMode"));
                course.setTeacherName(rs.getString("TeacherName"));
                course.setPeople(rs.getInt("CoursePeople"), rs.getInt("CourseAllowed"));
                course.setStatus(rs.getInt("STATUS"));
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

    //    根据查询条件、分页条件获取学生端可选课程的信息(条件分页查询)

    @Override
    public Course[] queryOptional(String num, String condition1, String condition2, String input, int pageNum) {
        int index = 0;
        try {
            conn = JDBCUtils.getConnection();
            String sql;
            if (condition2 == null && input == null) {
                sql = "SELECT *,(SELECT COUNT(*) FROM course INNER JOIN election ON course.`CourseNum` = election.`CourseNum` WHERE election.`StudentNum`=? AND election.`CourseNum` =vi_querycourse.`num`) AS STATUS FROM vi_querycourse WHERE StudentNum=? AND CourseAllowed>CoursePeople limit ?,?";
            } else {
                sql = "SELECT *,(SELECT COUNT(*) FROM course INNER JOIN election ON course.`CourseNum` = election.`CourseNum` WHERE election.`StudentNum`=? AND election.`CourseNum` =vi_querycourse.`num`) AS STATUS  FROM vi_querycourse WHERE StudentNum=? AND ";
                if ("精确".equals(condition2)) {
                    if ("序号".equals(condition1)) {
                        sql = sql.concat("num=? limit ?,?");
                    } else if ("课程".equals(condition1)) {
                        sql = sql.concat("CourseName=? limit ?,?");
                    } else {
                        sql = sql.concat("varietyName=? limit ?,?");
                    }
                } else if ("模糊".equals(condition2)) {
                    if ("序号".equals(condition1)) {
                        sql = sql.concat("num like ? limit ?,?");
                    } else if ("课程".equals(condition1)) {
                        sql = sql.concat("CourseName like ? limit ?,?");
                    } else {
                        sql = sql.concat("varietyName like ? limit ?,?");
                    }
                }
            }
            ps = conn.prepareStatement(sql);
            ps.setString(1, num);
            ps.setString(2, num);
            if (condition2 != null && input != null) {
                if ("精确".equals(condition1)) {
                    ps.setString(3, input);
                } else {
                    ps.setString(3, "%" + input + "%");
                }
                ps.setInt(4, (pageNum - 1) * 15);
                ps.setInt(5, pageNum * 15);
            } else {
                ps.setInt(3, (pageNum - 1) * 15);
                ps.setInt(4, pageNum * 15);
            }
            rs = ps.executeQuery();

            rs.last();
            Course[] arr = new Course[rs.getRow()];

            rs.beforeFirst();
            while (rs.next()) {
                Course course = new Course();
                course.setNum(rs.getString("num"));
                course.setName(rs.getString("CourseName"));
                course.setCredit(rs.getInt("CourseCredit"));
                course.setPeriod(rs.getInt("Period"));
                course.setVariety(rs.getString("VarietyName"));
                course.setTeacherName(rs.getString("TeacherName"));
                course.setEvaluationMode(rs.getString("EvaluationMode"));
                course.setPeople(rs.getInt("CoursePeople"), rs.getInt("CourseAllowed"));
                course.setStatus(rs.getInt("STATUS"));
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

    //    学生端用户选课

    @Override
    public int selectCourse(String number, String courseNumber) {
        //返回0表示该课程已经没有名额，返回1表示选课成功，返回-1表示操作异常,返回-2表示非正选时间

        //查询是否在正选时间内
        boolean isSelect = getOptionalNumber() > 0 ? true : false;
        if (isSelect) {
            //判断该课程是否还有可选名额
            boolean ifSelect = courseResidueNum(courseNumber) > 0 ? true : false;
            if (ifSelect) {
                try {
                    conn = JDBCUtils.getConnection();
                    String sql = "CALL selectCourse(?,?,?)";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, number);
                    ps.setString(2, courseNumber);
                    ps.setString(3, getSemester(null));
                    ps.execute();

                    return 1;

                } catch (SQLException e) {
                    e.printStackTrace();
                    return -1;
                } finally {
                    JDBCUtils.close2(conn, ps);
                }
            }
            return 0;
        }
        return -2;
    }

    //    退选课程

    @Override
    public int exitCourse(String number, String courseNumber) {
        //返回-1表示后台异常，返回0表示非正选时间，返回1表示退选成功
        //查询是否在正选时间内
        boolean isSelect = getOptionalNumber() > 0 ? true : false;
        if (isSelect) {
            try {
                conn = JDBCUtils.getConnection();
                String sql = "CALL exitCourse(?,?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, number);
                ps.setString(2, courseNumber);
                ps.execute();

                return 1;

            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            } finally {
                JDBCUtils.close1(conn, ps, rs);
            }
        }
        return 0;
    }

    //    获取学生端成绩信息

    @Override
    public Course[] getGradeInfo(String num, String semester) {
        int index = 0;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "CALL getStudentScore(?,?);";
            ps = conn.prepareStatement(sql);
            ps.setString(1, num);
            ps.setString(2, getSemester(semester));
            rs = ps.executeQuery();

            rs.last();
            Course[] arr = new Course[rs.getRow()];

            rs.beforeFirst();
            while (rs.next()) {
                Course course = new Course();
                course.setName(rs.getString("CourseName"));
                course.setCredit(rs.getInt("CourseCredit"));
                course.setVariety(rs.getString("VarietyName"));
                course.setEvaluationMode(rs.getString("EvaluationMode"));
                course.setCharacter(rs.getString("Character"));
                course.setScore(rs.getInt("Grade"));
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

    //    学生端密保答案修改

    @Override
    public int modifyAnswer(String num, String oldAnswer, String newAnswer) {
        //返回-1表示异常，返回0表示原密保错误答案错误，返回1表示修改成功
        boolean flag = ifChanged(num);
        if (flag) {
            try {
                conn = JDBCUtils.getConnection();
                String sql = "UPDATE student SET StudentAnswer=?,IfChange=FALSE WHERE studentNum=? AND IfChange=FALSE AND StudentAnswer=?";
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

    //    查询密保答案是否修改过了

    @Override
    public boolean ifChanged(String num) {
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT ifchange FROM student where StudentNum=?";
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

    public boolean ifExist(String num) {
        boolean exist = false;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "CALL existStudent(?)";
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

    //      获取指定课程的可选名额

    @Override
    public int courseResidueNum(String courseNum) {
        //返回-1表示发生异常
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT getCourseResidueNum(?);";
            ps = conn.prepareStatement(sql);
            ps.setString(1, courseNum);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return -1;
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
