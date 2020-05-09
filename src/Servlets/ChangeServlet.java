package Servlets;

import Service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ChangeServlet")
public class ChangeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数
        String num = request.getParameter("num");
        String password = request.getParameter("password");
        String answer = request.getParameter("answer");
        String newAnswer = request.getParameter("newAnswer");
        String identity = request.getParameter("identity");

        //如果是以GET方式提交，则页面直接重定向到找回密码界面
        if (num == null || answer == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        identity = identity == null ? (String) request.getSession(false).getAttribute("identity") : identity;

        int exist = 0;//默认返回值为0
        if ("student".equals(identity)) {
            //2.创建Service对象
            IStudentService service = new StudentServiceImpl();
            if (password != null) {
                //3.调用Service对象的modifyStudentPassword()方法
                exist = service.modifyStudentPassword(num, password, answer);
            } else if (newAnswer != null) {
                //3.调用Service对象的modifyStudentAnswer()方法
                exist = service.modifyStudentAnswer(num, answer, newAnswer);
            }
        } else if ("teacher".equals(identity)) {
            //2.创建Service对象
            ITeacherService service = new TeacherServiceImpl();
            if (password != null) {
                //3.调用Service对象的modifyTeacherPassword()方法
                exist = service.modifyTeacherPassword(num, password, answer);
            } else if (newAnswer != null) {
                //3.调用Service对象的modifyTeacherAnswer()方法
                exist = service.modifyTeacherAnswer(num, answer, newAnswer);
            }
        } else if ("administrator".equals(identity)) {
            //2.创建Service对象
            IAdministratorService service = new AdministratorServiceImpl();
            if (password != null) {
                //3.调用Service对象的modifyAdministratorPassword()方法
                exist = service.modifyAdministratorPassword(num, password, answer);
            } else if (newAnswer != null) {
                //3.调用Service对象的modifyAdministratorAnswer()方法
                exist = service.modifyAdministratorAnswer(num, answer, newAnswer);
            }
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
