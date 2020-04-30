package Service;

import Dao.AdministratorDaoImpl;
import Dao.IAdministratorDao;
import beans.Administrator;

public class AdministratorServiceImpl implements IAdministratorService {
    private IAdministratorDao dao;

    public AdministratorServiceImpl() {
        dao=new AdministratorDaoImpl();
    }

    @Override
    public Object checkAdministrator(String num, String password) {
        return dao.selectAdministrator(num,password);
    }

    @Override
    public Administrator getAdministratorInfo(String num) {
        return dao.getInfo(num);
    }

    @Override
    public int modifyAdministratorInfo(String[] info, String num) {
        return dao.modifyInfo(info,num);
    }

    @Override
    public int modifyAdministratorPassword(String num, String password, String answer) {
        return dao.modifyPassword(num, password, answer);
    }
}
