package Service;

import Dao.ITeacherDao;
import Dao.TeacherDaoImpl;

public class TeacherServiceImpl implements ITeacherService {
    private ITeacherDao dao;

    public TeacherServiceImpl() {
        dao=new TeacherDaoImpl();
    }

    @Override
    public int checkTeacher(String num, String password) {
        return dao.selectTeacher(num,password);
    }
}
