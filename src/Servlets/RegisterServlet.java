package Servlets;

import Service.IStudentService;
import Service.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        //1.获取请求中的参数
        String num = request.getParameter("num");
        String operation = request.getParameter("operation");
        HttpSession session = request.getSession(false);

        if (num == null || session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String number = (String) session.getAttribute("num");

        int outcome;
        if (number != null && operation != null) {
            //2.创建service对象
            IStudentService service = new StudentServiceImpl();

            if ("register".equals(operation)) {
                //3.调用service对象的studentSelectCourse()方法进行选课
                outcome = service.studentSelectCourse(number, num);
            } else {
                //3.调用service对象的studentExitCourse()方法进行退选
                outcome = service.studentExitCourse(number, num);
            }
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
