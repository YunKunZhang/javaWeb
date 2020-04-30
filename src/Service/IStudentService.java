package Service;

import beans.Course;
import beans.Student;

public interface IStudentService {
    //对学生端用户进行验证
    Object checkStudent(String num, String password);

    //获取学生端用户信息
    Student getStudentInfo(String num);

    //修改数据库中学生的信息
    int modifyStudentInfo(String[] info,String num);

    //查看学生端用户课程信息
    Course[] getStudentCourseInfo(String num,String semester);

    //查看学生端用户可选课程总数
    int getStudentOptionalNumber();

    //根据条件查看客户端用户可选数目总数
    int getStudentQueryOptionalNumber(String num,String condition1,String condition2,String input);

    //查看学生端用户可选课程信息（普通分页查询）
    Course[] getStudentOptional(String num,int pageNum);

    //学生端用户根据条件查询可选课程信息（条件分页查询）
    Course[]  queryStudentOptional(String num,String condition1,String condition2,String input,int paNum);

    //学生端用户根据课程号选择课程
    int studentSelectCourse(String number,String courseNumber);

    //学生端用户根据课程号进行退选课程
    int studentExitCourse(String number,String courseNumber);

    //查看学生端用户成绩信息
    Course[] getStudentGradeInfo(String num,String semester);

    //修改学生端用户密码
    int modifyStudentPassword(String num, String password, String answer);

    //修改学生端用户密保答案
    int modifyStudentAnswer(String num,String oldAnswer,String newAnswer);
}
