package Service;

import beans.Administrator;
import beans.Teacher;

public interface IAdministratorService {
    //对管理员进行登陆验证
    Object checkAdministrator(String num, String password);

    //获取管理员端信息
    Administrator getAdministratorInfo(String num);

    //修改数据库中管理员的信息
    int modifyAdministratorInfo(String[] info,String num);

    //修改管理员端用户密码
    int modifyAdministratorPassword(String num, String password, String answer);
}
