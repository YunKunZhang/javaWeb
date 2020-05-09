package Servlets;

import Service.AdministratorServiceImpl;
import Service.IAdministratorService;
import Service.IStudentService;
import Service.StudentServiceImpl;
import utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class InformationManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        //获取请求中的参数
        String department = req.getParameter("department");

        String[] major = null;
        if (department != null) {
            //创建service对象
            IAdministratorService service = new AdministratorServiceImpl();
            major = service.getMajor(department);
        }

        resp.setHeader("Content-type", "text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        //调用工具类的方法，将课程信息封装为json数组
        String stringify = JsonUtils.stringify(major);
        //创建标准输出流对象
        PrintWriter writer = resp.getWriter();
        writer.print(stringify.equals("[]") ? 0 : stringify);
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        //获取会话
        HttpSession session = req.getSession(false);

        if (session == null || !"true".equals(session.getAttribute("login"))) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String identity = req.getParameter("type");
        String operation = req.getParameter("operation");
        String condition = null;
        String extra = null;
        String input = null;
        String information = null;
        String pageNum = req.getParameter("pageNum");
        if ("query".equals(operation)) {
            condition = req.getParameter("condition");
            extra = req.getParameter("extra");
            input = req.getParameter("input");
        } else if ("addPerson".equals(operation)) {
            information = req.getParameter("information");
        }

        Object[] outcome = null;
        Integer num = null;
        if (identity != null && operation != null) {
            if ("query".equals(operation)) {
                num = getAmount(identity, condition, extra, input);//如果是查询操作就先获取符合条件的数量
                //如果数量大于0就进一步获取信息
                outcome = num > 0 ? queryOperation(identity, condition, extra, input, pageNum) : null;
            } else if ("selectAll".equals(operation)) {
                num = getAmount(identity);
                outcome = num > 0 ? selectOperation(identity, pageNum) : null;
            } else if ("remove".equals(operation)) {
                num = removeOperation(identity, req.getParameter("num"));
            } else if ("addPerson".equals(operation)) {
                //对请求中的参数进行处理
                String[] split = information.split("&");
                for (int i = 0; i < split.length; i++) {
                    int tem = split[i].indexOf("=") + 1;
                    split[i] = split[i].substring(tem);
                }
                num = addOperation(identity, split);
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        resp.setHeader("Content-type", "text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        //调用工具类的方法，将课程信息封装为json数组
        String stringify = JsonUtils.stringify(outcome);
        //创建标准输出流对象
        PrintWriter writer = resp.getWriter();
        writer.println(num);
        writer.print(stringify.equals("[]") ? 0 : stringify);
        writer.close();

    }

    protected int getAmount(String identity) {//查询某一个客户端的全部人数
        //创建service对象
        IAdministratorService service = new AdministratorServiceImpl();
        return service.getAllPersonAmount(identity);
    }

    protected int getAmount(String identity, String condition, String extra, String input) {//条件查询时获取符合条件的人数
        //创建service对象
        IAdministratorService service = new AdministratorServiceImpl();
        //调用service对象的getQueryStudentAmount()方法获取符合条件的学生（学生）总数
        return service.getQueryPersonAmount(identity, condition, extra, input);
    }

    protected Object[] selectOperation(String identity, String pageNum) {//查询功能(查询全部)
        //创建service对象
        IAdministratorService service = new AdministratorServiceImpl();
        //查询全部
        //调用service对象的getAllStudentInfo()方法查询所有学生（教师）信息
        return service.getAllPersonInfo(identity, Integer.parseInt(pageNum));
    }

    protected Object[] queryOperation(String identity, String condition, String extra, String input, String pageNum) {
        //创建service对象
        IAdministratorService service = new AdministratorServiceImpl();
        //分页条件查询
        //调用service对象的getQueryPersonInfo()方法查询所有学生（教师）信息
        return service.getQueryPersonInfo(identity, condition, extra, input, Integer.parseInt(pageNum));
    }

    protected int addOperation(String identity, String[] information) {
        //创建service对象
        IAdministratorService service = new AdministratorServiceImpl();
        return service.addPerson(identity, information);
    }

    protected int removeOperation(String identity, String num) {
        //创建service对象
        IAdministratorService service = new AdministratorServiceImpl();
        return service.removePersonInfo(identity, num);
    }
}
