package Dao;

import beans.*;

public interface IAdministratorDao {
    //查询数据库是否有该名管理员
    Object selectAdministrator(String num, String password);

    //获取管理员端信息
    Administrator getInfo(String num);

    //查看学校所有课程总数
    int getCourseAmount();

    //查看学校所有课程信息
    Course[] getCourseInfo(int pageNum);

    //查看学校所有可选课程信息
    Course[] getOptionalInfo(int pageNum);

    //查看学校所有成绩总数
    int getGradeAmount(String semester);

    //查看学校所有成绩信息
    Grade[] getGradeInfo(String semester, int pageNum);

    //查看学校所有可选课程总数
    int getOptionalAmount();

    //根据条件查看学校相应课程数量
    int getQueryCourseNumber(String condition1, String condition2, String input);

    //根据条件查看学校相应成绩数量
    int getQueryGradeNumber(String semester, String condition1, String condition2, String input);

    //根据条件查看学校相应可选课程数量
    int getQueryOptionalNumber(String condition1, String condition2, String input);

    //根据条件查看学校相应课程信息
    Course[] queryCourse(String condition1, String condition2, String input, int pageNum);

    //根据条件查看学校相应成绩信息
    Grade[] queryGrade(String semester, String condition1, String condition2, String input, int pageNum);

    //根据条件查看学校相应可选课程信息
    Course[] queryOptional(String condition1, String condition2, String input, int pageNum);

    //查询数据库中所有学生(教师)数目
    int getAllPersonNumber(String identity);

    //查询数据库中所有学生(教师)信息
    Object[] getAllPersonInfo(String identity, int pageNum);

    //根据条件查询符合条件的学生(教师)总数
    int getQueryPersonNumber(String identity, String condition1, String condition2, String input);

    //根据条件查询符合条件的学生(教师)信息
    Object[] getQueryPersonInfo(String identity, String condition1, String condition2, String input, int pageNum);

    //添加学生（教师）信息进入数据库
    int addPerson(String identity, String[] information);

    //删除数据库中符合条件的学生（教师）信息
    int removePersonInfo(String identity, String num);

    //根据学院信息获取专业信息
    String[] getMajor(String department);

    //修改数据库中管理员的信息
    int modifyInfo(String[] info, String num);

    //修改数据库中管理员的密码
    int modifyPassword(String num, String password, String answer);

    //修改数据库中管理员端用户的密保答案
    int modifyAnswer(String num, String oldAnswer, String newAnswer);

    //查询数据库中管理员端用户的密保答案是否修改了
    boolean ifChanged(String num);

    //查询数据库中是否有该管理员账号
    boolean ifExist(String num);

    //获取学期字符串
    String getSemester(String semester);
}
