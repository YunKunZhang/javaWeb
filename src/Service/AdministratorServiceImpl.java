package Service;

import Dao.AdministratorDaoImpl;
import Dao.IAdministratorDao;
import beans.Administrator;
import beans.Course;
import beans.Grade;
import beans.Student;

public class AdministratorServiceImpl implements IAdministratorService {
    private IAdministratorDao dao;

    public AdministratorServiceImpl() {
        dao = new AdministratorDaoImpl();
    }

    @Override
    public Object checkAdministrator(String num, String password) {
        return dao.selectAdministrator(num, password);
    }

    @Override
    public Administrator getAdministratorInfo(String num) {
        return dao.getInfo(num);
    }

    @Override
    public int getAdministratorCourseAmount() {
        return dao.getCourseAmount();
    }

    @Override
    public Course[] getAdministratorCourseInfo(int pageNum) {
        return dao.getCourseInfo(pageNum);
    }

    @Override
    public Grade[] queryAdministratorGrade(String semester, String condition1, String condition2, String input, int pageNum) {
        return dao.queryGrade(semester, condition1, condition2, input, pageNum);
    }

    @Override
    public Course[] getAdministratorOptionalInfo(int pageNum) {
        return dao.getOptionalInfo(pageNum);
    }

    @Override
    public int getAdministratorQueryCourseNumber(String condition1, String condition2, String input) {
        return dao.getQueryCourseNumber(condition1, condition2, input);
    }

    @Override
    public int getAdministratorQueryGradeNumber(String semester, String condition1, String condition2, String input) {
        return dao.getQueryGradeNumber(semester, condition1, condition2, input);
    }

    @Override
    public int getAdministratorQueryOptionalNumber(String condition1, String condition2, String input) {
        return dao.getQueryOptionalNumber(condition1, condition2, input);
    }

    @Override
    public Course[] queryAdministratorCourse(String condition1, String condition2, String input, int pageNum) {
        return dao.queryCourse(condition1, condition2, input, pageNum);
    }

    @Override
    public int getAdministratorOptionalNumber() {
        return dao.getOptionalAmount();
    }

    @Override
    public Course[] queryAdministratorOptional(String condition1, String condition2, String input, int pageNum) {
        return dao.queryOptional(condition1, condition2, input, pageNum);
    }

    @Override
    public int getAdministratorGradeAmount(String semester) {
        return dao.getGradeAmount(semester);
    }

    @Override
    public Grade[] getAdministratorGradeInfo(String semester, int pageNum) {
        return dao.getGradeInfo(semester, pageNum);
    }

    @Override
    public int getAllPersonAmount(String identity) {
        return dao.getAllPersonNumber(identity);
    }

    @Override
    public Object[] getAllPersonInfo(String identity, int pageNum) {
        return dao.getAllPersonInfo(identity, pageNum);
    }

    @Override
    public int modifyAdministratorInfo(String[] info, String num) {
        return dao.modifyInfo(info, num);
    }

    @Override
    public int getQueryPersonAmount(String identity, String condition1, String condition2, String input) {
        return dao.getQueryPersonNumber(identity, condition1, condition2, input);
    }

    @Override
    public Object[] getQueryPersonInfo(String identity, String condition1, String condition2, String input, int pageNum) {
        return dao.getQueryPersonInfo(identity, condition1, condition2, input, pageNum);
    }

    @Override
    public String[] getMajor(String department) {
        return dao.getMajor(department);
    }

    @Override
    public String[] queryMajorByCourseNum(String courseNum) {
        return dao.queryMajorByCourseNum(courseNum);
    }

    @Override
    public int addPerson(String identity, String[] information) {
        return dao.addPerson(identity, information);
    }

    @Override
    public int addCourse(String[] information) {
        return dao.addCourse(information);
    }

    @Override
    public int removePersonInfo(String identity, String num) {
        return dao.removePersonInfo(identity, num);
    }

    @Override
    public int removeCourseInfo(String num) {
        return dao.removeCourseInfo(num);
    }

    @Override
    public int modifyPersonInfo(String identity, String[] information) {
        return dao.modifyPersonInfo(identity, information);
    }

    @Override
    public int modifyCourseInfo(String[] information) {
        return dao.modifyCourseInfo(information);
    }

    @Override
    public int selectControl(String control) {
        return dao.selectControl(control);
    }

    @Override
    public int control(String control, String nowStatus) {
        return dao.control(control, nowStatus);
    }

    @Override
    public int modifyAdministratorAnswer(String num, String oldAnswer, String newAnswer) {
        return dao.modifyAnswer(num, oldAnswer, newAnswer);
    }

    @Override
    public int modifyAdministratorPassword(String num, String password, String answer) {
        return dao.modifyPassword(num, password, answer);
    }
}
