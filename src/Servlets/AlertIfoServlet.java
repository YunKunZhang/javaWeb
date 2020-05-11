package Servlets;

import Service.*;
import beans.Administrator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AlertIfoServlet")
public class AlertIfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //1.获取请求参数
        String data = request.getParameter("data");
        HttpSession session = request.getSession(false);

        //防止用户以Get方式提交
        if (data == null || session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String identity = (String) session.getAttribute("identity");
        String num = (String) session.getAttribute("num");

        if (identity != null && num != null) {
            int outcome = 0;//默认没有修改
            //对请求中的参数进行处理
            String[] split = data.split("&");
            for (int i = 0; i < split.length; i++) {
                int tem = split[i].indexOf("=") + 1;
                split[i] = split[i].substring(tem);
            }

            if ("student".equals(identity)) {
                //2.创建service对象
                IStudentService service = new StudentServiceImpl();
                //3.调用service对象的modifyStudentInfo()方法修改信息
                outcome = service.modifyStudentInfo(split, num);

            } else if ("teacher".equals(identity)) {
                //2.创建service对象
                ITeacherService service = new TeacherServiceImpl();
                //3.调用service对象的modifyTeacherInfo()方法修改信息
                outcome = service.modifyTeacherInfo(split, num);

            } else if ("administrator".equals(identity)) {
                //2.创建service对象
                IAdministratorService service = new AdministratorServiceImpl();
                //3.调用service对象的modifyAdministratorInfo()方法修改信息
                outcome = service.modifyAdministratorInfo(split, num);

            }

            //创建标准输出流
            PrintWriter writer = response.getWriter();
            writer.print(outcome);
            writer.close();
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        //获取请求中的参数
        String control = req.getParameter("alertStatus");
        String nowStatus = req.getParameter("nowStatus");
        int outcome = 0;
        if (control == null || nowStatus == null) {
            return;
        } else {
            //创建Service对象
            IAdministratorService service = new AdministratorServiceImpl();
            outcome = service.control(control, nowStatus);
        }
        //创建标准输出流
        PrintWriter writer = resp.getWriter();
        writer.print(outcome);
        writer.close();
    }
}
