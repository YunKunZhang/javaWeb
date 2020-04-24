package Service;

import Dao.IStudentDao;
import Dao.StudentDaoImpl;
import beans.Student;

public class StudentServiceImpl implements IStudentService {
    private IStudentDao dao;

    public StudentServiceImpl() {
        dao = new StudentDaoImpl();
    }

    @Override
    public Object checkStudent(String num, String password) {
        return dao.selectStudent(num, password);
    }

    @Override
    public int verifyStudent(String num, String password, String answer) {
        return dao.modifyPassword(num, password, answer);
    }

    @Override
    public Student getStudentInfo(String num) {
        return dao.getStudentInfo(num);
    }

    @Override
    public int modify(String[] info,String num) {
        return dao.modifyInfo(info,num);
    }
}
