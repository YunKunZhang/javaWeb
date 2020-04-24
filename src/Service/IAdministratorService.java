package Service;

public interface IAdministratorService {
    //对管理员进行登陆验证
    int checkAdministrator(String num, String password);
}
