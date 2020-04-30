package Servlets;

import Service.IStudentService;
import Service.ITeacherService;
import Service.StudentServiceImpl;
import Service.TeacherServiceImpl;
import beans.Course;
import beans.Grade;
import utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CoursePagingServlet")
public class CoursePagingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        //1.获取请求中的参数
        String pageNum = request.getParameter("pageNum");
        HttpSession session = request.getSession(false);

        //防止用户以GET方式提交
        if (pageNum == null || session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String identity = (String) session.getAttribute("identity");
        String num = (String) session.getAttribute("num");

        Object[] course;
        if (identity != null && num != null) {
            if ("teacher".equals(identity)) {
                //分页显示指定学期的学生成绩
                course = chooseGrade(num, request.getParameter("semester"), Integer.parseInt(pageNum));
            } else {
                //获取当前所有可选的课程信息
                course = chooseCourse(identity, pageNum, num);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        //调用工具类的方法，将课程信息封装为json数组
        String stringify = JsonUtils.stringify(course);
        //创建标准输出流对象
        PrintWriter writer = response.getWriter();
        writer.print(stringify.equals("[]") ? 0 : stringify);
        writer.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Object login = session.getAttribute("login");

        if (session == null || "false".equals(login)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        //获取当前登录用户的身份
        String identity = (String) session.getAttribute("identity");

        int sum;
        if ("student".equals(identity) || "administrator".equals(identity)) {
            //创建service对象
            IStudentService service = new StudentServiceImpl();
            //调用service对象的getStudentOptionalNumber()方法获取可选课程的总数
            sum = service.getStudentOptionalNumber();
        } else if ("teacher".equals(identity)) {
            //创建service对象
            ITeacherService service = new TeacherServiceImpl();
            //调用service对象的getStudentNumber()方法获取选择教师课的学生总数
            sum = service.getStudentNumber((String) session.getAttribute("num"), null);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        //创建标准输出流对象
        PrintWriter writer = response.getWriter();
        writer.print(sum);
        writer.close();
    }

    protected Course[] chooseCourse(String identity, String pageNum, String num) {
        if ("student".equals(identity)) {
            //2.创建Service对象
            IStudentService service = new StudentServiceImpl();
            //3.调用service对象的getStudentOptional()方法获取可选课程的信息
            return service.getStudentOptional(num, Integer.parseInt(pageNum));
        } else if ("administrator".equals(identity)) {

        }
        return null;

    }

    protected Grade[] chooseGrade(String num, String semester, int pageNum) {
        //2.创建service对象
        ITeacherService service = new TeacherServiceImpl();
        //3.调用Service对象的getTeacherGradeInfo()方法获取信息
        return service.getTeacherGradeInfo(num, semester, pageNum);
    }
}
