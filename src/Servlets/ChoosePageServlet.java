package Servlets;

import Dao.AdministratorDaoImpl;
import Service.*;
import beans.Student;
import beans.Teacher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ChoosePageServlet")
public class ChoosePageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        //1.获取参数中的变量
        String pageName = request.getParameter("pageName");
        String name = pageName.substring(4);
        HttpSession session = request.getSession(false);

        //防止用户以Get方式提交
        if (name == null || session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String identity = (String) session.getAttribute("identity");
        String num = (String) session.getAttribute("num");

        if (num != null && identity != null) {
            boolean flag = true;
            Integer amount = null;
            //管理员用户在查看部分信息时需要获取信息的总数，如果总数大于0就查询信息，反之不查询
            if ("administrator".equals(identity) || ("student".equals(identity) && "chooseCourse.jsp".equals(name))) {
                amount = gainAmount(identity, name);
                flag = amount > 0 ? true : false;
            } else if ("teacher".equals(identity) && "gradeInformation.jsp".equals(name)) {
                amount = gainAmount(identity, name, num);
                flag = amount > 0 ? true : false;
            }

            if (flag) {//flag为true表示管理员想要获取的信息数目大于0，进一步进行查询，反之不查询
                request.setAttribute("info", chooseService(identity, name, num));
                request.setAttribute("amount", amount != null ? amount : 0);
                if ("personnelManagement.jsp".equals(name)) {
                    request.setAttribute("type", "student");
                } else if ("chooseCourse.jsp".equals(name) || "gradeInformation.jsp".equals(name)) {
                    request.setAttribute("status", gainStatus(name.substring(0, 6)));
                }
            }

            request.getRequestDispatcher(pageName).forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected int gainAmount(String identity, String pageName) {//学生获取可选课程数量，管理员获取课程、成绩、选课数量
        if ("student".equals(identity)) {
            //创建service对象
            IStudentService service = new StudentServiceImpl();
            //调用service对象的getStudentOptionalNumber()方法获取可选课程的总数
            return service.getStudentOptionalNumber();
        } else {
            //创建Service对象
            IAdministratorService service = new AdministratorServiceImpl();
            if ("courseInformation.jsp".equals(pageName)) {
                //调用getAdministratorCourseAmount()方法获取课程总数
                return service.getAdministratorCourseAmount();
            } else if ("gradeInformation.jsp".equals(pageName)) {
                //调用getAdministratorGradeAmount()方法获取成绩总数
                return service.getAdministratorGradeAmount(null);
            } else if ("chooseCourse.jsp".equals(pageName)) {
                //调用getAdministratorCourseAmount()方法获取课程总数
                return service.getAdministratorOptionalNumber();
            } else {
                //调用getAllStudentAmount()方法获取所有学生数
                return service.getAllPersonAmount("student");
            }
        }
    }

    protected int gainAmount(String identity, String pageName, String num) {//教师端获取所有学生成绩的数目
        //创建service对象
        ITeacherService service = new TeacherServiceImpl();
        //调用service对象的getStudentNumber()方法获取选择教师课的学生总数
        return service.getStudentNumber(num, null);
    }

    protected int gainStatus(String control) {
        IAdministratorService service = new AdministratorServiceImpl();
        //调用service对象的getStudentNumber()方法获取选择教师课的学生总数
        return service.selectControl(control);
    }

    protected Object chooseService(String identity, String pageName, String num) {//根据不同的身份、页面地址调用对象的相应方法
        if ("showInformation.jsp".equals(pageName)) {
            if ("student".equals(identity)) {
                //2.创建Service对象
                IStudentService service = new StudentServiceImpl();
                //3.调用Service对象的getStudentInfo()方法获取信息
                return service.getStudentInfo(num);

            } else if ("teacher".equals(identity)) {
                //2.创建Service对象
                ITeacherService service = new TeacherServiceImpl();
                //3.调用Service对象的getStudentInfo()方法获取信息
                return service.getTeacherInfo(num);

            } else if ("administrator".equals(identity)) {
                //2.创建Service对象
                IAdministratorService service = new AdministratorServiceImpl();
                //3.调用Service对象的getAdministratorInfo()方法获取信息
                return service.getAdministratorInfo(num);
            }
        } else if ("courseInformation.jsp".equals(pageName) || "chooseCourse.jsp".equals(pageName)) {
            if ("student".equals(identity)) {
                //2.创建Service对象
                IStudentService service = new StudentServiceImpl();
                if ("courseInformation.jsp".equals(pageName)) {
                    //3.调用Service对象的getStudentCourseInfo()方法获取信息
                    return service.getStudentCourseInfo(num, null);
                } else {
                    //3.调用service对象的getStudentOptional()方法获取可选课程的信息
                    return service.getStudentOptional(num, 1);
                }
            } else if ("teacher".equals(identity)) {
                //2.创建Service对象
                ITeacherService service = new TeacherServiceImpl();
                //3.调用Service对象的getTeacherCourseInfo()方法获取信息
                return service.getTeacherCourseInfo(num, null);
            } else if ("administrator".equals(identity)) {
                //2.创建Service对象
                IAdministratorService service = new AdministratorServiceImpl();
                if ("courseInformation.jsp".equals(pageName)) {
                    //3.调用Service对象的getAdministratorCourseInfo()方法获取信息
                    return service.getAdministratorCourseInfo(1);
                } else {
                    //3.调用Service对象的getAdministratorOptionalInfo()方法获取信息
                    return service.getAdministratorOptionalInfo(1);
                }
            }
        } else if ("gradeInformation.jsp".equals(pageName)) {
            if ("student".equals(identity)) {
                //2.创建Service对象
                IStudentService service = new StudentServiceImpl();
                //3.调用Service对象的getStudentGradeInfo()方法获取信息
                return service.getStudentGradeInfo(num, null);
            } else if ("teacher".equals(identity)) {
                //2.创建Service对象
                ITeacherService service = new TeacherServiceImpl();
                //3.调用Service对象的getTeacherGradeInfo()方法获取信息
                return service.getTeacherGradeInfo(num, null, 1);
            } else {
                //2.创建Service对象
                IAdministratorService service = new AdministratorServiceImpl();
                //3.调用service对象的getAdministratorGradeInfo()方法获取信息
                return service.getAdministratorGradeInfo(null, 1);
            }
        } else if ("personnelManagement.jsp".equals(pageName)) {
            //2.创建service对象
            IAdministratorService service = new AdministratorServiceImpl();
            return service.getAllPersonInfo("student", 1);
        } else if ("changePassword.jsp".equals(pageName)) {
            return num;
        }
        return null;
    }
}
