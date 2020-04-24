package Dao;

public interface IAdministratorDao {
    //查询数据库是否有该名管理员
    int selectAdministrator(String num, String password);

    //修改数据库中管理员的密码
    int modifyAdministrator(String num, String password, String answer);

    //查询数据库中是否有该管理员账号
    boolean ifExist(String num);
}
