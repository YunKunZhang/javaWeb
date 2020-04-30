package Dao;

import beans.Course;
import beans.Student;

public interface IStudentDao {
    //查询数据库中是否有该名学生（登陆验证）
    Object selectStudent(String num, String password);

    //获取数据库中学生端用户信息
    Student getInfo(String num);

    //修改数据库中学生的信息
    int modifyInfo(String[] info,String num);

    //获取数据库中学生端用户课程信息
    Course[] getCourseInfo(String num,String semester);

    //获取数据库中学生端用户可选课程的总数
    int getOptionalNumber();

    //根据条件获取数据库中学生端用户可选课程的总数
    int getQueryOptionalNumber(String num,String condition1,String condition2,String input);

    //获取数据库中学生端用户可选课程的信息(普通分页查询)
    Course[] getOptional(String num,int pageNum);

    //根据条件获取数据库中学生端用户符合条件的可选课程信息（条件分页查询）
    Course[] queryOptional(String num,String condition1,String condition2,String input,int pageNum);

    //学生端用户根据课程号选取相应的课程
    int selectCourse(String number,String courseNumber);

    //学生端用户根据课程号退选相应的课程
    int exitCourse(String number,String courseNumber);

    //获取数据库中学生端用户成绩信息
    Course[] getGradeInfo(String num,String semester);

    //修改数据库中学生端用户的密码
    int modifyPassword(String num, String password, String answer);

    //修改数据库中学生端用户的密保答案
    int modifyAnswer(String num,String oldAnswer,String newAnswer);

    //查询数据库中学生端用户的密保答案是否修改了
    boolean ifChanged(String num);

    //查询数据库中是否有该学生账号
    boolean ifExist(String num);

    //根据课程号查询数据库中指定课程剩余可选名额
    int courseResidueNum(String courseNum);

    //获取学期字符串
    String getSemester(String semester);
}
