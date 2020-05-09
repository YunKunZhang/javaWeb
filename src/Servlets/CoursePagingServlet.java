package Servlets;

import Service.*;
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

        Object[] obj = null;
        if (identity != null && num != null) {
            if ("teacher".equals(identity)) {
                //分页显示指定学期的学生成绩
                obj = chooseGrade(num, request.getParameter("semester"), Integer.parseInt(pageNum));
            } else if ("student".equals(identity)) {
                //获取当前所有可选的课程信息
                obj = chooseCourse(identity, pageNum, num);
            } else if ("administrator".equals(identity)) {
                String temp = request.getParameter("pageName");
                if ("grade".equals(temp)) {
                    //分页显示成绩信息
                    obj = chooseGrade(request.getParameter("semester"), Integer.parseInt(pageNum));
                } else {
                    //分页显示可选的课程信息、课程信息
                    obj = chooseCourse(identity, pageNum, temp);
                }
            }
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
        writer.print(stringify.equals("[]") ? 0 : stringify);
        writer.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected Course[] chooseCourse(String identity, String pageNum, String num) {//学生、管理员获取所有课程相关信息
        if ("student".equals(identity)) {
            //2.创建Service对象
            IStudentService service = new StudentServiceImpl();
            //3.调用service对象的getStudentOptional()方法获取可选课程的信息
            return service.getStudentOptional(num, Integer.parseInt(pageNum));
        } else {
            //2.创建Service对象
            IAdministratorService service = new AdministratorServiceImpl();
            if ("course".equals(num)) {//管理员端课程分页
                //3.调用Service对象的getAdministratorCourseInfo()方法获取信息
                return service.getAdministratorCourseInfo(Integer.parseInt(pageNum));
            } else {
                //3.调用Service对象的getAdministratorOptionalInfo()方法获取信息
                return service.getAdministratorOptionalInfo(Integer.parseInt(pageNum));
            }
        }
    }

    protected Grade[] chooseGrade(String semester, int pageNum) {
        //2.创建Service对象
        IAdministratorService service = new AdministratorServiceImpl();
        //3.调用Service对象的getAdministratorGradeInfo()方法获取信息
        return service.getAdministratorGradeInfo(semester, pageNum);
    }


    protected Grade[] chooseGrade(String num, String semester, int pageNum) {//教师获取不同学期的自己所教授的学生成绩
        //2.创建service对象
        ITeacherService service = new TeacherServiceImpl();
        //3.调用Service对象的getTeacherGradeInfo()方法获取信息
        return service.getTeacherGradeInfo(num, semester, pageNum);
    }
}
