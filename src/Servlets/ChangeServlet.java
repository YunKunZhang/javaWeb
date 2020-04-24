package Servlets;

import Service.IStudentService;
import Service.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ChangeServlet")
public class ChangeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数
        String num = request.getParameter("num");
        String password = request.getParameter("password");
        String answer = request.getParameter("answer");
        String identity = request.getParameter("identity");

        //如果是以GET方式提交，则页面直接重定向到找回密码界面
        if (num == null || answer == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int exist = 0;//默认返回值为1
        if ("student".equals(identity)) {
            //2.创建Service对象
            IStudentService service = new StudentServiceImpl();
            //3.调用Service对象的verify()方法对用户进行验证
            exist = service.verifyStudent(num, password, answer);
        }

        PrintWriter writer = response.getWriter();//创建标准输出流对象
        if (exist != 1) {
            //4.验证没通过，说明密保答案错误、账号未注册、发出提示信息。
            writer.print(exist);
        } else if (exist == 1) {
            //5.验证通过，则跳转到系统主页login.jsp，并通知修改密码成功
            writer.print(request.getContextPath() + "/login.jsp");
        }
        writer.close();
    }
}
