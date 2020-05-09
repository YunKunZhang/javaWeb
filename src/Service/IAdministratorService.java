package Service;

import beans.*;

public interface IAdministratorService {
    //对管理员进行登陆验证
    Object checkAdministrator(String num, String password);

    //获取管理员端信息
    Administrator getAdministratorInfo(String num);

    //修改数据库中管理员的信息
    int modifyAdministratorInfo(String[] info, String num);

    //查看学校课程的总数
    int getAdministratorCourseAmount();

    //查看学校所有课程信息
    Course[] getAdministratorCourseInfo(int pageNum);

    //查看学校所有可选课程总数
    int getAdministratorOptionalNumber();

    //查看学校所有可选课程信息
    Course[] getAdministratorOptionalInfo(int pageNum);

    //查看学校成绩的总数
    int getAdministratorGradeAmount(String semester);

    //查看学校成绩的信息
    Grade[] getAdministratorGradeInfo(String semester, int pageNum);

    //根据条件查看学校课程总数
    int getAdministratorQueryCourseNumber(String condition1, String condition2, String input);

    //根据条件查看学校成绩总数
    int getAdministratorQueryGradeNumber(String semester, String condition1, String condition2, String input);

    //根据条件查看学校可选课程总数
    int getAdministratorQueryOptionalNumber(String condition1, String condition2, String input);

    //根据条件查看学校课程信息
    Course[] queryAdministratorCourse(String condition1, String condition2, String input, int pageNum);

    //根据条件查看学校课程信息
    Grade[] queryAdministratorGrade(String semester, String condition1, String condition2, String input, int pageNum);

    //根据条件查看学校可选课程信息
    Course[] queryAdministratorOptional(String condition1, String condition2, String input, int pageNum);

    //查询所有学生（教师）总数
    int getAllPersonAmount(String identity);

    //查询所有学生(教师)信息
    Object[] getAllPersonInfo(String identity, int pageNum);

    //根据条件查询符合条件的学生(教师)总数
    int getQueryPersonAmount(String identity, String condition1, String condition2, String input);

    //根据条件查询符合条件的学生(教师)信息
    Object[] getQueryPersonInfo(String identity, String condition1, String condition2, String input, int pageNum);

    //根据学院查询专业
    String[] getMajor(String department);

    //添加教师（学生）
    int addPerson(String identity, String[] information);

    //删除符合条件的学生（教师）信息
    int removePersonInfo(String identity, String num);

    //修改管理员端用户密保答案
    int modifyAdministratorAnswer(String num, String oldAnswer, String newAnswer);

    //修改管理员端用户密码
    int modifyAdministratorPassword(String num, String password, String answer);
}
