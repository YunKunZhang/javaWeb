package Servlets;

import Service.ITeacherService;
import Service.TeacherServiceImpl;
import utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ScoreServlet")
public class ScoreServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        //获取请求中的参数
        String semester = request.getParameter("semester");
        String courseName = request.getParameter("courseName");
        String stuNum = request.getParameter("stuNum");
        String score = request.getParameter("score");

        HttpSession session = request.getSession(false);

        if (semester == null || session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Object identity = session.getAttribute("identity");

        int outcome;
        if ("teacher".equals(identity)) {
            //创建service对象
            ITeacherService service = new TeacherServiceImpl();
            //调用service对象的modifyTeacherGradeInfo()方法修改学生成绩
            outcome = service.modifyTeacherGradeInfo(semester, courseName, stuNum, score);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        //创建标准输出流对象
        PrintWriter writer = response.getWriter();
        writer.print(outcome);
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
