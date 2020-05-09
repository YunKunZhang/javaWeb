package Servlets;

import Service.*;
import beans.Course;
import utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;

@WebServlet(name = "ChooseSemesterServlet")
public class ChooseSemesterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        //1.获取请求中的参数
        String semester = request.getParameter("semester");
        String info = request.getParameter("info");
        HttpSession session = request.getSession(false);

        //防止用户以Get方式提交
        if (semester == null || session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String identity = (String) session.getAttribute("identity");
        //根据用户身份判断是获取账号还是姓名
        String num = (String) session.getAttribute("num");

        Object[] obj;
        Integer amount;
        if (identity != null && num != null) {
            amount = "administrator".equals(identity) ? gainAmount(semester) : null;
            //选择不同学期的课程、成绩信息
            obj = chooseSemester(identity, info, num, semester);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        //调用工具类的方法，将课程信息封装为json数组
        String stringify = JsonUtils.stringify(obj);
        //创建标准输出流对象
        PrintWriter writer = response.getWriter();
        if ("administrator".equals(identity)) {
            writer.print(amount);
        }
        writer.print(stringify.equals("[]") ? 0 : stringify);
        writer.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected int gainAmount(String semester) {//管理员判断成绩信息是否为空
        //创建Service对象
        IAdministratorService service = new AdministratorServiceImpl();
        //调用getAdministratorGradeAmount()方法获取成绩总数
        return service.getAdministratorGradeAmount(semester);
    }

    protected Object[] chooseSemester(String identity, String info, String num, String semester) {
        if ("student".equals(identity)) {
            //2.创建Service对象
            IStudentService service = new StudentServiceImpl();
            if ("course".equals(info)) {
                //3.调用Service对象的getStudentCourseInfo()方法获取信息
                return service.getStudentCourseInfo(num, semester);
            } else if ("grade".equals(info)) {
                //3.调用Service对象的getStudentGradeInfo()方法获取信息
                return service.getStudentGradeInfo(num, semester);
            }

        } else if ("teacher".equals(identity)) {
            //2.创建Service对象
            ITeacherService service = new TeacherServiceImpl();
            if ("course".equals(info)) {
                //3.调用Service对象的getTeacherCourseInfo()方法获取信息
                return service.getTeacherCourseInfo(num, semester);
            } else if ("grade".equals(info)) {
                //3.调用Service对象的getTeacherGradeInfo()方法获取信息
                return service.getTeacherGradeInfo(num, semester, 1);
            }
        } else if ("administrator".equals(identity)) {
            //创建service对象
            IAdministratorService service = new AdministratorServiceImpl();
            //调用service对象的getAdministratorGradeInfo()方法获取信息
            return service.getAdministratorGradeInfo(semester, 1);
        }
        return null;
    }
}
