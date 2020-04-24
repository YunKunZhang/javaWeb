package Servlets;

import Service.IStudentService;
import Service.StudentServiceImpl;
import beans.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "choosePageServlet")
public class choosePageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取参数中的变量
        String name = request.getParameter("pageName");
        HttpSession session = request.getSession(false);

        //防止用户以Get方式提交
        if (name == null || session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String num = (String) session.getAttribute("num");

        if(num!=null){
            //2.创建Service对象
            IStudentService service = new StudentServiceImpl();
            //3.调用Service对象的getStudentInfo()方法获取信息
            Student student = service.getStudentInfo(num);

            request.setAttribute("info", student);
            request.getRequestDispatcher(name).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
