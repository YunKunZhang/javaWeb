package Dao;

import beans.Course;
import beans.Grade;
import beans.Student;
import beans.Teacher;

public interface ITeacherDao {
    //查询数据库中是否有该名教师
    Object selectTeacher(String num, String password);

    //获取教师端用户信息
    Teacher getInfo(String num);

    //修改数据库中教师的信息
    int modifyInfo(String[] info,String num);

    //获取数据库中教师所教课程的学生数目
    int getNumber(String num,String semester);

    //获取数据库中教师的课程信息
    Course[] getCourseInfo(String num, String semester);

    //获取数据库中教师所讲课程的学生成绩信息
    Grade[]  getGradeInfo(String num,String semester,int pageNum);

    //修改数据库中教师所讲授课程的学生成绩信息
    int modifyGradeInfo(String semester,String courseName,String stuNum,String score);

    //修改数据库中教师的密码
    int modifyPassword(String num, String password, String answer);

    //修改数据库中教师端用户的密保答案
    int modifyAnswer(String num,String oldAnswer,String newAnswer);

    //查询数据库中教师端用户的密保答案是否修改了
    boolean ifChanged(String num);

    //查询数据库中是否有该教师账号
    boolean ifExist(String num);

    //查询是否开启成绩录入
    int ifInputGrade();

    //获取学期字符串
    String getSemester(String semester);
}
