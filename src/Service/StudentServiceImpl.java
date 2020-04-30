package Service;

import Dao.IStudentDao;
import Dao.StudentDaoImpl;
import beans.Course;
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
    public Student getStudentInfo(String num) {
        return dao.getInfo(num);
    }

    @Override
    public int modifyStudentInfo(String[] info,String num) {
        return dao.modifyInfo(info,num);
    }

    @Override
    public Course[] getStudentCourseInfo(String num,String semester) {
        return dao.getCourseInfo(num,semester);
    }

    @Override
    public int getStudentOptionalNumber() {
        return dao.getOptionalNumber();
    }

    @Override
    public int getStudentQueryOptionalNumber(String num,String condition1,String condition2,String input) {
        return dao.getQueryOptionalNumber(num,condition1,condition2,input);
    }

    @Override
    public Course[] getStudentOptional(String num, int pageNum) {
        return dao.getOptional(num,pageNum);
    }

    @Override
    public Course[] queryStudentOptional(String num,String condition1,String condition2,String input,int pageNum) {
        return dao.queryOptional(num,condition1,condition2,input,pageNum);
    }

    @Override
    public int studentSelectCourse(String number, String courseNumber) {
        return dao.selectCourse(number,courseNumber);
    }

    @Override
    public int studentExitCourse(String number, String courseNumber) {
        return dao.exitCourse(number,courseNumber);
    }

    @Override
    public Course[] getStudentGradeInfo(String num,String semester) {
        return dao.getGradeInfo(num,semester);
    }

    @Override
    public int modifyStudentPassword(String num, String password, String answer) {
        return dao.modifyPassword(num, password, answer);
    }

    @Override
    public int modifyStudentAnswer(String num, String oldAnswer, String newAnswer) {
        return dao.modifyAnswer(num,oldAnswer,newAnswer);
    }
}
