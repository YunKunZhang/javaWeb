package Servlets;

import Service.AdministratorServiceImpl;
import Service.IAdministratorService;
import Service.IStudentService;
import Service.StudentServiceImpl;
import beans.Course;
import net.sf.json.util.JSONUtils;
import utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "QueryServlet")
public class QueryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        //1.获取请求中的参数
        String condition = request.getParameter("condition");
        String extraCondition = request.getParameter("extraCondition");
        String input = request.getParameter("input");
        Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));

        HttpSession session = request.getSession(false);

        if (condition == null || session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String num = (String) session.getAttribute("num");
        Object identity = session.getAttribute("identity");

        int courseNumber;
        Object[] obj;
        if (num != null) {
            if ("student".equals(identity)) {
                //创建service对象
                IStudentService service = new StudentServiceImpl();
                if ("可选".equals(condition)) {
                    //调用service对象的getStudentQueryOptionalNumber()方法获取符合条件的可选课程数目
                    courseNumber = service.getStudentQueryOptionalNumber(num, condition, null, null);

                    //判断符合条件的可选课程数目是否大于0，如果大于0就调用service对象的queryStudentOptional()方法获取课程信息
                    obj = courseNumber > 0 ? service.queryStudentOptional(num, condition, null, null, pageNum) : null;
                } else {
                    //调用service对象的getStudentQueryOptionalNumber()方法获取符合条件的可选课程数目
                    courseNumber = service.getStudentQueryOptionalNumber(num, condition, extraCondition, input);

                    //判断符合条件的可选课程数目是否大于0，如果大于0就调用service对象的queryStudentOptional()方法获取课程信息
                    obj = courseNumber > 0 ? service.queryStudentOptional(num, condition, extraCondition, input, pageNum) : null;
                }
            } else {
                String pageName = request.getParameter("pageName");
                //创建service对象
                IAdministratorService service = new AdministratorServiceImpl();

                if ("course".equals(pageName)) {
                    //调用service对象的getAdministratorQueryCourseNumber()方法获取符合条件的课程总数
                    courseNumber = service.getAdministratorQueryCourseNumber(condition, extraCondition, input);

                    //判断符合条件的可选课程数目是否大于0，如果大于0就调用service对象的queryAdministratorCourse()方法获取课程信息
                    obj = courseNumber > 0 ? service.queryAdministratorCourse(condition, extraCondition, input, pageNum) : null;
                } else if ("grade".equals(pageName)) {
                    //调用service对象的getAdministratorQueryGradeNumber()方法获取符合条件的成绩总数
                    courseNumber = service.getAdministratorQueryGradeNumber(request.getParameter("semester"), condition, extraCondition, input);

                    //判断符合条件的可选课程数目是否大于0，如果大于0就调用service对象的queryAdministratorGrade()方法获取课程信息
                    obj = courseNumber > 0 ? service.queryAdministratorGrade(request.getParameter("semester"), condition, extraCondition, input, pageNum) : null;
                } else {
                    //调用service对象的getAdministratorQueryOptionalNumber()方法获取符合条件的课程总数
                    courseNumber = service.getAdministratorQueryOptionalNumber(condition, extraCondition, input);

                    //判断符合条件的可选课程数目是否大于0，如果大于0就调用service对象的queryAdministratorCourse()方法获取课程信息
                    obj = courseNumber > 0 ? service.queryAdministratorOptional(condition, extraCondition, input, pageNum) : null;
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
        writer.print(courseNumber);
        writer.print(stringify.equals("[]") ? 0 : stringify);
        writer.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
