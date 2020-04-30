package Service;

import beans.Course;
import beans.Grade;
import beans.Student;
import beans.Teacher;

public interface ITeacherService {
    //对教师端学生进行验证
    Object checkTeacher(String num, String password);

    //获取教师端用户信息
    Teacher getTeacherInfo(String num);

    //修改数据库中教师的信息
    int modifyTeacherInfo(String[] info,String num);

    //获取教师端用户所教授课程的学生总数
    int getStudentNumber(String num,String semester);

    //查看教师端用户所教授课程信息
    Course[]  getTeacherCourseInfo(String num,String semester);

    //查看教师端用户所教授课程的成绩信息
    Grade[]  getTeacherGradeInfo(String num,String semester,int pageNum);

    //修改教师端用户密码
    int modifyTeacherPassword(String num, String password, String answer);

    //修改教师端用户密保答案
    int modifyTeacherAnswer(String num,String oldAnswer,String newAnswer);
}
