package Dao;

import beans.Administrator;
import beans.Teacher;

public interface IAdministratorDao {
    //查询数据库是否有该名管理员
    Object selectAdministrator(String num, String password);

    //获取管理员端信息
    Administrator getInfo(String num);

    //修改数据库中管理员的信息
    int modifyInfo(String[] info,String num);

    //修改数据库中管理员的密码
    int modifyPassword(String num, String password, String answer);

    //查询数据库中是否有该管理员账号
    boolean ifExist(String num);
}
