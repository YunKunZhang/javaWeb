package Service;

import Dao.AdministratorDaoImpl;
import Dao.IAdministratorDao;

public class AdministratorServiceImpl implements IAdministratorService {
    private IAdministratorDao dao;

    public AdministratorServiceImpl() {
        dao=new AdministratorDaoImpl();
    }

    @Override
    public int checkAdministrator(String num, String password) {
        return dao.selectAdministrator(num,password);
    }
}
