package Servlets;

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
        String name = request.getParameter("pageName");
        HttpSession session = request.getSession(false);

        //防止用户以Get方式提交
        if (name == null || session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String identity = (String) session.getAttribute("identity");
        String num = (String) session.getAttribute("num");

        if (num != null && identity != null) {

            request.setAttribute("info", chooseService(identity, name.substring(4), num));
            request.getRequestDispatcher(name).forward(request, response);

        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected Object chooseService(String identity, String pageName, String num) {
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
                //3.调用Service对象的getStudentCourseInfo()方法获取信息
                return service.getStudentCourseInfo(num, null);

            } else if ("teacher".equals(identity)) {
                //2.创建Service对象
                ITeacherService service = new TeacherServiceImpl();
                //3.调用Service对象的getTeacherCourseInfo()方法获取信息
                return service.getTeacherCourseInfo(num, null);
            } else if ("administrator".equals(identity)) {

            }
        } else if ("gradeInformation.jsp".equals(pageName)) {
            if ("student".equals(identity)) {
                //2.创建Service对象
                IStudentService service = new StudentServiceImpl();
                //3.调用Service对象的getStudentGradeInfo()方法获取信息
                return service.getStudentGradeInfo(num, null);
            }else if("teacher".equals(identity)){
                //2.创建Service对象
                ITeacherService service=new TeacherServiceImpl();
                //3.调用Service对象的getTeacherGradeInfo()方法获取信息
                return service.getTeacherGradeInfo(num,null,1);
            }
        } else if ("changePassword.jsp".equals(pageName)) {
            return num;
        }
        return null;
    }
}
