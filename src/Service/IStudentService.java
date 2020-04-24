package Service;

import beans.Student;

public interface IStudentService {
    //对学生端用户进行验证
    Object checkStudent(String num, String password);

    //修改学生端用户密码
    int verifyStudent(String num, String password, String answer);

    //修改数据库中学生的信息
    int modify(String[] info,String num);

    //获取学生端用户信息
    Student getStudentInfo(String num);
}
