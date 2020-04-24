package Dao;

import beans.Student;

public interface IStudentDao {
    //查询数据库中是否有该名学生（登陆验证）
    Object selectStudent(String num, String password);

    //修改数据库中学生的密码
    int modifyPassword(String num, String password, String answer);

    //修改数据库中学生的信息
    int modifyInfo(String[] info,String num);

    //查询数据库中是否有该学生账号
    boolean ifExist(String num);

    ////获取学生端用户信息
    Student getStudentInfo(String num);
}
