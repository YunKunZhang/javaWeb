package Service;

import Dao.ITeacherDao;
import Dao.TeacherDaoImpl;
import beans.Course;
import beans.Grade;
import beans.Teacher;

public class TeacherServiceImpl implements ITeacherService {
    private ITeacherDao dao;

    public TeacherServiceImpl() {
        dao = new TeacherDaoImpl();
    }

    @Override
    public Object checkTeacher(String num, String password) {
        return dao.selectTeacher(num, password);
    }

    @Override
    public Teacher getTeacherInfo(String num) {
        return dao.getInfo(num);
    }

    @Override
    public int modifyTeacherInfo(String[] info, String num) {
        return dao.modifyInfo(info, num);
    }

    @Override
    public int getStudentNumber(String num, String semester) {
        return dao.getNumber(num, semester);
    }

    @Override
    public Course[] getTeacherCourseInfo(String num, String semester) {
        return dao.getCourseInfo(num, semester);
    }

    @Override
    public Grade[] getTeacherGradeInfo(String num, String semester, int pageNum) {
        return dao.getGradeInfo(num, semester, pageNum);
    }

    @Override
    public int modifyTeacherPassword(String num, String password, String answer) {
        return dao.modifyPassword(num, password, answer);
    }

    @Override
    public int modifyTeacherAnswer(String num, String oldAnswer, String newAnswer) {
        return dao.modifyAnswer(num, oldAnswer, newAnswer);
    }
}
