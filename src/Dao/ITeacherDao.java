package Dao;

public interface ITeacherDao {
    //查询数据库中是否有该名教师
    int selectTeacher(String num, String password);

    //修改数据库中教师的密码
    int modifyTeacher(String num, String password, String answer);

    //查询数据库中是否有该教师账号
    boolean ifExist(String num);
}
