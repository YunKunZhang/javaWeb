package Dao;

import beans.*;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

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

    //    获取管理员信息

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

    //    获取学校开设所有课程的数量

    @Override
    public int getCourseAmount() {
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select count(*) from vi_allcourse";
            ps = conn.prepareStatement(sql);
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

    //    获取学校开设所有可选课程的数量

    @Override
    public int getOptionalAmount() {
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT \n" +
                    "  count(*) \n" +
                    "FROM\n" +
                    "  course \n" +
                    "  INNER JOIN coursevariety \n" +
                    "  ON course.`Variety`=coursevariety.`VarietyId`\n" +
                    "  WHERE course.`Variety` IN (3,4)";
            ps = conn.prepareStatement(sql);
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

    //    获取学校开设所有课程信息

    @Override
    public Course[] getCourseInfo(int pageNum) {
        int index = 0;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM vi_allcourse LIMIT ?,?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, (pageNum - 1) * 14);
            ps.setInt(2, pageNum * 14);
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
                course.setAllowed(rs.getString("CourseAllowed"));
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

    //    获取学校所有可选课程信息

    @Override
    public Course[] getOptionalInfo(int pageNum) {
        //每页显示15个
        int index = 0;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT \n" +
                    "  * \n" +
                    "FROM\n" +
                    "  course \n" +
                    "  INNER JOIN coursevariety \n" +
                    "  ON course.`Variety`=coursevariety.`VarietyId`\n" +
                    "  WHERE course.`Variety` IN (3,4) limit ?,?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, (pageNum - 1) * 14);
            ps.setInt(2, pageNum * 14);
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

    //    获取学校所有成绩总数

    @Override
    public int getGradeAmount(String semester) {
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT \n" +
                    "  COUNT(*)\n" +
                    "FROM\n" +
                    "  election \n" +
                    "  INNER JOIN course \n" +
                    "    ON election.`CourseNum` = course.`CourseNum` \n" +
                    "  INNER JOIN coursevariety \n" +
                    "    ON coursevariety.`VarietyId` = course.`Variety`";
            ps = conn.prepareStatement(sql);
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

    //    获取学校所有成绩信息

    @Override
    public Grade[] getGradeInfo(String semester, int pageNum) {
        int index = 0;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT \n" +
                    "  CourseName,\n" +
                    "  student.`StudentNum`,\n" +
                    "  student.`StudentName`,\n" +
                    "  CourseCredit,\n" +
                    "  varietyName,\n" +
                    "  EvaluationMode,\n" +
                    "  election.`Character`,\n" +
                    "  IFNULL(Grade,-1) Score, \n" +
                    "  Semester \n" +
                    "FROM\n" +
                    "  election \n" +
                    "  INNER JOIN course \n" +
                    "    ON election.`CourseNum` = course.`CourseNum` \n" +
                    "  INNER JOIN student \n" +
                    "    ON election.`StudentNum` = student.`StudentNum` \n" +
                    "  INNER JOIN coursevariety \n" +
                    "    ON coursevariety.`VarietyId` = course.`Variety` where Semester=? LIMIT ?,?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, getSemester(semester));
            ps.setInt(2, (pageNum - 1) * 14);
            ps.setInt(3, pageNum * 14);

            rs = ps.executeQuery();

            rs.last();
            Grade[] arr = new Grade[rs.getRow()];

            rs.beforeFirst();
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setCourseName(rs.getString("CourseName"));
                grade.setStuNum(rs.getString("StudentNum"));
                grade.setStuName(rs.getString("StudentName"));
                grade.setCredit(rs.getShort("CourseCredit"));
                grade.setVariety(rs.getString("VarietyName"));
                grade.setEvaluationMode(rs.getString("EvaluationMode"));
                grade.setCharacter(rs.getString("Character"));
                grade.setScore(rs.getInt("Score"));
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

    //    根据条件查看学校相应课程数量

    @Override
    public int getQueryCourseNumber(String condition1, String condition2, String input) {
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select count(*) from vi_allcourse where ";
            if ("课程号".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("CourseNum=?") : sql.concat("CourseNum like ?");
            } else if ("课程".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("CourseName=?") : sql.concat("CourseName like ?");
            } else if ("类别".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("varietyName=?") : sql.concat("varietyName like ?");
            } else {
                sql = "精确".equals(condition2) ? sql.concat("TeacherName=?") : sql.concat("TeacherName like ?");
            }
            ps = conn.prepareStatement(sql);
            ps.setString(1, "精确".equals(condition2) ? input : "%" + input + "%");
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return 0;
    }

    //    根据条件查看学校相应课程信息

    @Override
    public Course[] queryCourse(String condition1, String condition2, String input, int pageNum) {
        int index = 0;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from vi_allcourse where ";
            if ("课程号".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("CourseNum=?") : sql.concat("CourseNum like ? limit ?,?");
            } else if ("课程".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("CourseName=?") : sql.concat("CourseName like ? limit ?,?");
            } else if ("类别".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("varietyName=?") : sql.concat("varietyName like ? limit ?,?");
            } else {
                sql = "精确".equals(condition2) ? sql.concat("TeacherName=?") : sql.concat("TeacherName like ? limit ?,?");
            }
            ps = conn.prepareStatement(sql);
            ps.setString(1, "精确".equals(condition2) ? input : "%" + input + "%");
            if ("模糊".equals(condition2)) {
                ps.setInt(2, (pageNum - 1) * 14);
                ps.setInt(3, pageNum * 14);
            }
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

    //    根据条件查看学校相应成绩数量

    @Override
    public int getQueryGradeNumber(String semester, String condition1, String condition2, String input) {
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT \n" +
                    "  count(*) \n" +
                    "FROM\n" +
                    "  election \n" +
                    "  INNER JOIN course \n" +
                    "    ON course.`CourseNum` = election.`CourseNum` \n" +
                    "  INNER JOIN coursevariety \n" +
                    "    ON coursevariety.`VarietyId` = course.`Variety` \n" +
                    "  INNER JOIN student \n" +
                    "    ON student.`StudentNum` = election.`StudentNum` WHERE Semester=? AND ";
            if ("课程".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("CourseName=?") : sql.concat("CourseName like ?");
            } else if ("学号".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("StudentNum=?") : sql.concat("StudentNum like ?");
            } else if ("姓名".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("StudentName=?") : sql.concat("StudentName like ?");
            }
            ps = conn.prepareStatement(sql);
            ps.setString(1, semester);
            ps.setString(2, "精确".equals(condition2) ? input : "%" + input + "%");

            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return 0;
    }

    //    根据条件查看学校相应成绩信息

    @Override
    public Grade[] queryGrade(String semester, String condition1, String condition2, String input, int pageNum) {
        int index = 0;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT \n" +
                    "  courseName,\n" +
                    "  student.`StudentNum`,\n" +
                    "  student.`StudentName`,\n" +
                    "  Semester,\n" +
                    "  CourseCredit,\n" +
                    "  VarietyName,\n" +
                    "  EvaluationMode,\n" +
                    "  election.`Character`,\n" +
                    "  IFNULL(Grade,-1) AS Score\n" +
                    "FROM\n" +
                    "  election \n" +
                    "  INNER JOIN course \n" +
                    "    ON course.`CourseNum` = election.`CourseNum` \n" +
                    "  INNER JOIN coursevariety \n" +
                    "    ON coursevariety.`VarietyId` = course.`Variety` \n" +
                    "  INNER JOIN student \n" +
                    "    ON student.`StudentNum` = election.`StudentNum` WHERE Semester=? AND  ";
            if ("课程".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("courseName=? limit ?,?") : sql.concat("courseName like ? limit ?,?");
            } else if ("学号".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("StudentNum=?") : sql.concat("StudentNum like ? limit ?,?");
            } else if ("姓名".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("StudentName=?") : sql.concat("StudentName like ? limit ?,?");
            }
            ps = conn.prepareStatement(sql);

            ps.setString(1, semester);
            ps.setString(2, "精确".equals(condition2) ? input : "%" + input + "%");
            if ("模糊".equals(condition2) || "课程".equals(condition1)) {
                ps.setInt(3, (pageNum - 1) * 14);
                ps.setInt(4, pageNum * 14);
            }
            rs = ps.executeQuery();

            rs.last();
            Grade[] arr = new Grade[rs.getRow()];

            rs.beforeFirst();
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setCourseName(rs.getString("CourseName"));
                grade.setStuNum(rs.getString("StudentNum"));
                grade.setStuName(rs.getString("StudentName"));
                grade.setCredit(rs.getShort("CourseCredit"));
                grade.setVariety(rs.getString("VarietyName"));
                grade.setEvaluationMode(rs.getString("EvaluationMode"));
                grade.setCharacter(rs.getString("Character"));
                grade.setScore(rs.getInt("Score"));
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

    //    根据条件查看学校相应可选课程数量

    @Override
    public int getQueryOptionalNumber(String condition1, String condition2, String input) {
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT \n" +
                    "  count(*) \n" +
                    "FROM\n" +
                    "  course \n" +
                    "  INNER JOIN coursevariety \n" +
                    "  ON course.`Variety`=coursevariety.`VarietyId`\n" +
                    "  WHERE course.`Variety` IN (3,4) AND ";
            if ("课程".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("courseName=?") : sql.concat("courseName like ?");
            } else if ("类别".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("varietyName=?") : sql.concat("varietyName like ?");
            } else if ("序号".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("CourseNum=?") : sql.concat("CourseNum like ?");
            } else if ("可选".equals(condition1)) {
                sql = sql.concat("CourseAllowed>CoursePeople");
            }
            ps = conn.prepareStatement(sql);
            if (!"可选".equals(condition1)) {
                ps.setString(1, "精确".equals(condition2) ? input : "%" + input + "%");
            }

            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }

        return 0;
    }

    //    根据条件查看学校相应可选课程信息

    @Override
    public Course[] queryOptional(String condition1, String condition2, String input, int pageNum) {
        int index = 0;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT \n" +
                    "  * \n" +
                    "FROM\n" +
                    "  course \n" +
                    "  INNER JOIN coursevariety \n" +
                    "  ON course.`Variety`=coursevariety.`VarietyId`\n" +
                    "  WHERE course.`Variety` IN (3,4) AND ";
            if ("课程".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("courseName=?") : sql.concat("courseName like ? limit ?,?");
            } else if ("类别".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("varietyName=?") : sql.concat("varietyName like ? limit ?,?");
            } else if ("序号".equals(condition1)) {
                sql = "精确".equals(condition2) ? sql.concat("CourseNum=?") : sql.concat("CourseNum like ? limit ?,?");
            } else if ("可选".equals(condition1)) {
                sql = sql.concat("CourseAllowed>CoursePeople");
            }

            ps = conn.prepareStatement(sql);
            if (!"可选".equals(condition1)) {
                ps.setString(1, "精确".equals(condition2) ? input : "%" + input + "%");
            }
            if ("模糊".equals(condition2)) {
                ps.setInt(2, (pageNum - 1) * 14);
                ps.setInt(3, pageNum * 14);
            }
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

    //    查询所有学生(教师)数量

    @Override
    public int getAllPersonNumber(String identity) {
        try {
            conn = JDBCUtils.getConnection();
            String sql;
            if ("student".equals(identity)) {
                sql = "select count(*) from vi_student";
            } else {
                sql = "select count(*) from vi_teacher";
            }
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return 0;
    }

    //    查询所有学生（教师）信息

    @Override
    public Object[] getAllPersonInfo(String identity, int pageNum) {
        int index = 0;
        try {
            conn = JDBCUtils.getConnection();
            String sql;
            if ("student".equals(identity)) {
                sql = "select * from vi_student LIMIT ?,?";
            } else {
                sql = "select * from vi_teacher LIMIT ?,?";
            }
            ps = conn.prepareStatement(sql);
            ps.setInt(1, (pageNum - 1) * 14);
            ps.setInt(2, pageNum * 14);
            rs = ps.executeQuery();

            rs.last();
            Object[] arr;
            if ("student".equals(identity)) {
                arr = new Student[rs.getRow()];

                rs.beforeFirst();
                while (rs.next()) {
                    Student student = new Student();
                    student.setNum(rs.getString("StudentNum"));
                    student.setName(rs.getString("StudentName"));
                    student.setSex(rs.getString("StudentSex"));
                    student.setMajor(rs.getString("MajorName"));
                    student.setDepartment(rs.getString("DepName"));
                    student.setBirthday(rs.getDate("StudentBirthday"));
                    student.setEnterDate(rs.getDate("StudentEnterDate"));
                    student.setPhone(rs.getString("StudentPhone"));
                    arr[index] = student;
                    index++;
                }
            } else {
                arr = new Teacher[rs.getRow()];

                rs.beforeFirst();
                while (rs.next()) {
                    Teacher teacher = new Teacher();
                    teacher.setNum(rs.getString("TeacherNum"));
                    teacher.setName(rs.getString("TeacherName"));
                    teacher.setSex(rs.getString("TeacherSex"));
                    teacher.setDepartment(rs.getString("DepName"));
                    teacher.setRank(rs.getString("TeacherRank"));
                    teacher.setBirthday(rs.getDate("TeacherBirthday"));
                    teacher.setPhone(rs.getString("TeacherPhone"));
                    arr[index] = teacher;
                    index++;
                }
            }

            return arr;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return null;
    }

    //    根据条件查询符合条件的学生(教师)总数

    @Override
    public int getQueryPersonNumber(String identity, String condition1, String condition2, String input) {
        try {
            conn = JDBCUtils.getConnection();
            String sql;
            if ("student".equals(identity)) {
                sql = "select count(*) from vi_student where ";
                if ("账号".equals(condition1)) {
                    sql = "精确".equals(condition2) ? sql.concat("StudentNum=?") : sql.concat("StudentNum like ?");
                } else if ("姓名".equals(condition1)) {
                    sql = "精确".equals(condition2) ? sql.concat("StudentName=?") : sql.concat("StudentName like ?");
                } else {
                    sql = "精确".equals(condition2) ? sql.concat("DepName=?") : sql.concat("DepName like ?");
                }
            } else {
                sql = "select count(*) from vi_teacher where ";
                if ("账号".equals(condition1)) {
                    sql = "精确".equals(condition2) ? sql.concat("TeacherNum=?") : sql.concat("TeacherNum like ?");
                } else if ("姓名".equals(condition1)) {
                    sql = "精确".equals(condition2) ? sql.concat("TeacherName=?") : sql.concat("TeacherName like ?");
                } else {
                    sql = "精确".equals(condition2) ? sql.concat("DepName=?") : sql.concat("DepName like ?");
                }
            }

            ps = conn.prepareStatement(sql);
            ps.setString(1, "精确".equals(condition2) ? input : "%" + input + "%");
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

    //    根据条件查询符合条件的学生(教师)信息

    @Override
    public Object[] getQueryPersonInfo(String identity, String condition1, String condition2, String input, int pageNum) {
        int index = 0;
        try {
            conn = JDBCUtils.getConnection();
            String sql;
            if ("student".equals(identity)) {
                sql = "select * from vi_student where ";
                if ("账号".equals(condition1)) {
                    sql = "精确".equals(condition2) ? sql.concat("StudentNum=?") : sql.concat("StudentNum like ? limit ?,?");
                } else if ("姓名".equals(condition1)) {
                    sql = "精确".equals(condition2) ? sql.concat("StudentName=?") : sql.concat("StudentName like ? limit ?,?");
                } else {
                    sql = "精确".equals(condition2) ? sql.concat("DepName=?") : sql.concat("DepName like ? limit ?,?");
                }
            } else {
                sql = "select * from vi_teacher where ";
                if ("账号".equals(condition1)) {
                    sql = "精确".equals(condition2) ? sql.concat("TeacherNum=?") : sql.concat("TeacherNum like ?");
                } else if ("姓名".equals(condition1)) {
                    sql = "精确".equals(condition2) ? sql.concat("TeacherName=?") : sql.concat("TeacherName like ?");
                } else {
                    sql = "精确".equals(condition2) ? sql.concat("DepName=?") : sql.concat("DepName like ?");
                }
            }

            ps = conn.prepareStatement(sql);
            ps.setString(1, "精确".equals(condition2) ? input : "%" + input + "%");
            if ("模糊".equals(condition2)) {
                ps.setInt(2, (pageNum - 1) * 14);
                ps.setInt(3, pageNum * 14);
            }
            rs = ps.executeQuery();

            rs.last();
            Object[] arr;
            if ("student".equals(identity)) {
                arr = new Student[rs.getRow()];

                rs.beforeFirst();
                while (rs.next()) {
                    Student student = new Student();
                    student.setNum(rs.getString("StudentNum"));
                    student.setName(rs.getString("StudentName"));
                    student.setSex(rs.getString("StudentSex"));
                    student.setMajor(rs.getString("MajorName"));
                    student.setDepartment(rs.getString("DepName"));
                    student.setBirthday(rs.getDate("StudentBirthday"));
                    student.setEnterDate(rs.getDate("StudentEnterDate"));
                    student.setPhone(rs.getString("StudentPhone"));
                    arr[index] = student;
                    index++;
                }
            } else {
                arr = new Teacher[rs.getRow()];

                rs.beforeFirst();
                while (rs.next()) {
                    Teacher teacher = new Teacher();
                    teacher.setNum(rs.getString("TeacherNum"));
                    teacher.setName(rs.getString("TeacherName"));
                    teacher.setSex(rs.getString("TeacherSex"));
                    teacher.setDepartment(rs.getString("DepName"));
                    teacher.setRank(rs.getString("TeacherRank"));
                    teacher.setBirthday(rs.getDate("TeacherBirthday"));
                    teacher.setPhone(rs.getString("TeacherPhone"));
                    arr[index] = teacher;
                    index++;
                }
            }

            return arr;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return null;
    }

    //    添加教师（学生）用户信息

    @Override
    public int addPerson(String identity, String[] information) {
        try {
            conn = JDBCUtils.getConnection();
            String sql;
            if ("student".equals(identity)) {
                sql = "INSERT INTO student \n" +
                        "VALUES\n" +
                        "  (\n" +
                        "    ?,\n" +
                        "    ?,\n" +
                        "    ?,\n" +
                        "    (SELECT \n" +
                        "      MajorNum \n" +
                        "    FROM\n" +
                        "      major \n" +
                        "    WHERE majorName = ?),\n" +
                        "    STR_TO_DATE(?, '%Y-%m-%d'),\n" +
                        "    STR_TO_DATE(?, '%Y-%m-%d'),\n" +
                        "    ?,\n" +
                        "    HEX(\n" +
                        "      AES_ENCRYPT(?, ?)\n" +
                        "    ),\n" +
                        "    ?,\n" +
                        "    0\n" +
                        "  ) ";
            } else {
                sql = "INSERT INTO teacher \n" +
                        "VALUES\n" +
                        "  (\n" +
                        "    ?,\n" +
                        "    ?,\n" +
                        "    ?,\n" +
                        "    (SELECT \n" +
                        "      DepNum \n" +
                        "    FROM\n" +
                        "      department \n" +
                        "    WHERE DepName = ?),\n" +
                        "    STR_TO_DATE(?, '%Y-%m-%d'),\n" +
                        "    ?,\n" +
                        "    ?,\n" +
                        "    HEX(\n" +
                        "      AES_ENCRYPT(?,?)\n" +
                        "    ),\n" +
                        "    ?,\n" +
                        "    0\n" +
                        "  ) ;\n";
            }
            ps = conn.prepareStatement(sql);
            ps.setString(1, information[0]);
            ps.setString(2, information[1]);
            ps.setString(3, information[2]);
            ps.setString(4, "student".equals(identity) ? information[4] : information[3]);
            ps.setString(5, information[6]);
            ps.setString(6, "student".equals(identity) ? information[7] : information[5]);
            ps.setString(7, information[8]);
            ps.setString(8, information[0]);
            ps.setString(9, information[0]);
            ps.setString(10, information[0]);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, null);
        }
        return -1;
    }

    //    添加课程信息

    @Override
    public int addCourse(String[] information) {
        try {
            conn = JDBCUtils.getConnection();
            String sql = "insert into course values(?,?,?,?,(SELECT \n" +
                    "  VarietyId \n" +
                    "FROM\n" +
                    "  coursevariety \n" +
                    "WHERE VarietyName = ?),?,?,(SELECT \n" +
                    "  TeacherNum \n" +
                    "FROM\n" +
                    "  teacher \n" +
                    "WHERE teacher.`TeacherName` = ?),?,?,?,(SELECT \n" +
                    "  majorNum \n" +
                    "FROM\n" +
                    "  major \n" +
                    "WHERE major.`MajorName` =?))";
            ps = conn.prepareStatement(sql);
            ps.setString(1, information[0]);
            ps.setString(2, information[1]);
            ps.setInt(3, Integer.parseInt(information[2]));
            ps.setInt(4, Integer.parseInt(information[3]));
            ps.setString(5, information[4]);
            ps.setString(6, information[7]);
            ps.setString(7, information[8]);
            ps.setString(8, information[9]);
            ps.setString(9, information[9]);
            ps.setInt(10, 0);
            ps.setInt(11, Integer.parseInt(information[10]));
            ps.setString(12, information[6]);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return 0;
    }

    //    删除教师（学生）用户信息

    @Override
    public int removePersonInfo(String identity, String num) {
        try {
            conn = JDBCUtils.getConnection();
            String sql;
            if ("student".equals(identity)) {
                sql = "call removeStudent(?)";
            } else {
                sql = "call removeTeacher(?)";
            }
            ps = conn.prepareStatement(sql);
            ps.setString(1, num);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return -1;
    }

    //    删除课程信息

    @Override
    public int removeCourseInfo(String num) {
        boolean flag = ifSelectedCourse(num) > 0 ? true : false;
        if (flag) {
            try {
                conn = JDBCUtils.getConnection();
                String sql = "DELETE FROM course WHERE CourseNum=?";
                ps = conn.prepareStatement(sql);

                return ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.close2(conn, ps);
            }
        }
        return -1;
    }

    //    查看要删除的课程是否被选了

    @Override
    public int ifSelectedCourse(String num) {
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT COUNT(*) FROM course WHERE CoursePeople=0 AND CourseNum=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, num);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return 0;
    }

    //    修改教师（学生）信息

    @Override
    public int modifyPersonInfo(String identity, String[] information) {
        try {
            conn = JDBCUtils.getConnection();
            String sql;
            if ("student".equals(identity)) {
                sql = "UPDATE \n" +
                        "  student \n" +
                        "SET\n" +
                        "  StudentName = ?,\n" +
                        "  StudentSex = ?,\n" +
                        "  MajorNum = \n" +
                        "  (SELECT \n" +
                        "    majorNum \n" +
                        "  FROM\n" +
                        "    major \n" +
                        "  WHERE majorName = ?),\n" +
                        "  StudentBirthday = STR_TO_DATE(?, '%Y-%m-%d'),\n" +
                        "  StudentEnterDate = STR_TO_DATE(?, '%Y-%m-%d'),\n" +
                        "  StudentPhone = ? \n" +
                        "WHERE StudentNum = ?";
            } else {
                sql = "UPDATE \n" +
                        "  Teacher \n" +
                        "SET\n" +
                        "  TeacherName = ?,\n" +
                        "  TeacherSex = ?,\n" +
                        "  DepNum = \n" +
                        "  (SELECT \n" +
                        "    DepNum \n" +
                        "  FROM\n" +
                        "    department \n" +
                        "  WHERE DepName = ?),\n" +
                        "  TeacherRank = ?,\n" +
                        "  TeacherBirthday = STR_TO_DATE(?, '%Y-%m-%d'),\n" +
                        "  TeacherPhone = ? \n" +
                        "WHERE TeacherNum = ?";
            }
            ps = conn.prepareStatement(sql);
            if ("student".equals(identity)) {
                ps.setString(1, information[1]);
                ps.setString(2, information[2]);
                ps.setString(3, information[4]);
                ps.setString(4, information[6]);
                ps.setString(5, information[7]);
                ps.setString(6, information[8]);
                ps.setString(7, information[0]);
            } else {
                ps.setString(1, information[1]);
                ps.setString(2, information[2]);
                ps.setString(3, information[3]);
                ps.setString(4, information[5]);
                ps.setString(5, information[6]);
                ps.setString(6, information[8]);
                ps.setString(7, information[0]);
            }

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return -1;
    }

    //    修改课程信息

    @Override
    public int modifyCourseInfo(String[] information) {
        try {
            conn = JDBCUtils.getConnection();
            String sql = "UPDATE \n" +
                    "  course \n" +
                    "SET\n" +
                    "  CourseNum = ?,\n" +
                    "  CourseName = ?,\n" +
                    "  CourseCredit = ?,\n" +
                    "  Period = ?,\n" +
                    "  Variety = \n" +
                    "  (SELECT \n" +
                    "    VarietyId \n" +
                    "  FROM\n" +
                    "    coursevariety \n" +
                    "  WHERE coursevariety.`VarietyName` = ?),\n" +
                    "  MajorNum = \n" +
                    "  (SELECT \n" +
                    "    MajorNum \n" +
                    "  FROM\n" +
                    "    major \n" +
                    "  WHERE major.`MajorName` = ?),\n" +
                    "  TeachingMethod = ?,\n" +
                    "  EvaluationMode = ?,\n" +
                    "  TeacherNum = \n" +
                    "  (SELECT \n" +
                    "    TeacherNum \n" +
                    "  FROM\n" +
                    "    teacher \n" +
                    "  WHERE teacher.`TeacherName` = ?),\n" +
                    "  TeacherName = ?,\n" +
                    "  CourseAllowed=?\n" +
                    "  WHERE CourseNum=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, information[0]);
            ps.setString(2, information[1]);
            ps.setInt(3, Integer.parseInt(information[2]));
            ps.setInt(4, Integer.parseInt(information[3]));
            ps.setString(5, information[4]);
            ps.setString(6, information[6]);
            ps.setString(7, information[7]);
            ps.setString(8, information[8]);
            ps.setString(9, information[9]);
            ps.setString(10, information[9]);
            ps.setInt(11, Integer.parseInt(information[10]));
            ps.setString(12, information[0]);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return -1;
    }

    //    根据学院查询专业

    @Override
    public String[] getMajor(String department) {
        int index = 0;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT \n" +
                    "  MajorName \n" +
                    "FROM\n" +
                    "  major \n" +
                    "  INNER JOIN department \n" +
                    "    ON major.`DepNum` = department.`DepNum` \n" +
                    "WHERE department.`DepName` =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, department);
            rs = ps.executeQuery();

            rs.last();
            String[] arr = new String[rs.getRow()];

            rs.beforeFirst();
            while (rs.next()) {
                arr[index] = rs.getString("MajorName");
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

    //    根据课程号查询专业、学院信息

    @Override
    public String[] queryMajorByCourseNum(String courseNum) {
        String[] arr = new String[2];
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT \n" +
                    "  DepName,\n" +
                    "  MajorName \n" +
                    "FROM\n" +
                    "  course \n" +
                    "  INNER JOIN major \n" +
                    "    ON major.`MajorNum` = course.`MajorNum` \n" +
                    "  INNER JOIN department \n" +
                    "    ON department.`DepNum` = major.`DepNum` WHERE CourseNum=? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, courseNum);
            rs = ps.executeQuery();

            if (rs.next()) {
                arr[0] = rs.getString("DepName");
                arr[1] = rs.getString("MajorName");
                return arr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close1(conn, ps, rs);
        }
        return null;
    }

    //    查询选课（成绩录入）控制

    @Override
    public int selectControl(String control) {
        try {
            conn = JDBCUtils.getConnection();
            String sql;
            if ("choose".equals(control)) {
                sql = "SELECT IfTakeCourse from control";
            } else {
                sql = "SELECT IfInputGrade from control";
            }
            ps = conn.prepareStatement(sql);
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

    //    选课（成绩录入）控制

    @Override
    public int control(String control, String nowStatus) {
        try {
            conn = JDBCUtils.getConnection();
            String sql;
            if ("choose".equals(control)) {
                sql = "UPDATE control SET IfTakeCourse=?";
            } else {
                sql = "UPDATE control SET IfInputGrade=?";
            }
            ps = conn.prepareStatement(sql);
            ps.setInt(1, "正选".equals(nowStatus) ? 0 : 1);

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close2(conn, ps);
        }
        return 0;
    }

    //    修改管理员信息

    @Override
    public int modifyInfo(String[] info, String num) {
        //返回1表示修改成功,0表示失败
        try {
            conn = JDBCUtils.getConnection();
            String sql = "update administrator set ManagerName=?,ManagerSex=?,ManagerBirthday=STR_TO_DATE(?,'%Y-%m-%d'),ManagerPhone=? where ManagerNum=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, info[0]);
            ps.setString(2, info[1]);
            ps.setString(3, info[2]);
            ps.setString(4, info[3]);
            ps.setString(5, num);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close2(conn, ps);
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

    //    管理员密保答案修改

    @Override
    public int modifyAnswer(String num, String oldAnswer, String newAnswer) {
        //返回-1表示异常，返回0表示原密保错误答案错误，返回1表示修改成功
        boolean flag = ifChanged(num);
        if (flag) {
            try {
                conn = JDBCUtils.getConnection();
                String sql = "UPDATE administrator SET ManagerAnswer=?,IfChange=FALSE WHERE ManagerNum=? AND IfChange=FALSE AND ManagerAnswer=?";
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
            String sql = "SELECT ifchange FROM administrator where ManagerNum=?";
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


