package Servlets;

import Service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //1.获取请求参数
        String num = request.getParameter("num");
        String password = request.getParameter("password");
        String identity = request.getParameter("identity");

        Object exist = 0;//默认返回值为0
        if ("student".equals(identity)) {
            //2.创建Service对象
            IStudentService service = new StudentServiceImpl();
            //3.调用Service对象的checkStudent()方法对用户进行验证
            exist = service.checkStudent(num, password);
        } else if ("teacher".equals(identity)) {
            //2.创建Service对象
            ITeacherService service = new TeacherServiceImpl();
            //3.调用Service对象的checkTeacher()方法对用户进行验证
            exist = service.checkTeacher(num, password);
        } else if ("administrator".equals(identity)) {
            //2.创建Service对象
            IAdministratorService service = new AdministratorServiceImpl();
            //3.调用Service对象的checkAdministrator()方法对用户进行验证
            exist = service.checkAdministrator(num, password);
        } else {
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return;
        }

        PrintWriter writer = response.getWriter();//创建标准输出流对象
        if (exist instanceof Integer) {
            //4.验证没通过，说明该账号并未注册，或密码错误，跳转到登录页面，让用户再次输入登录信息。
            writer.print(exist);
        } else {
            //5.验证通过，则跳转到系统主页index.jsp
            HttpSession session = request.getSession(true);
            session.setAttribute("login", "true");
            session.setAttribute("identity", identity);
            session.setAttribute("num", num);
            session.setAttribute("name", exist);
            writer.print(request.getContextPath() + "/index");
        }

        writer.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        //1.获取请求中的参数
        String logout = req.getParameter("logout");

        HttpSession session = req.getSession(false);
        Object login = session.getAttribute("login");

        if (logout == null || session == null) {
            doPost(req, resp);
        } else {
            session.setAttribute("login", logout);
            PrintWriter writer = resp.getWriter();//创建标准输出流对象
            writer.print(req.getContextPath() + "/login.jsp");
        }
    }
}
