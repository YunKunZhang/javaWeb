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
import java.util.Iterator;
import java.util.Map;

@WebServlet(name = "ModifyInfoServlet")
public class ModifyInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //1.获取请求参数
        String data = request.getParameter("data");
        String birthday = request.getParameter("birthday");
        HttpSession session = request.getSession(false);

        if (session != null) {
            String identity = (String) session.getAttribute("identity");
            String num = (String) session.getAttribute("num");

            int outcome = 0;

            PrintWriter writer = response.getWriter();
            if ("student".equals(identity)) {
                //2.创建service对象
                IStudentService service = new StudentServiceImpl();

                //3.调用service对象的modify方法修改信息
                String[] split = data.split("&");
                for(int i=0;i<split.length;i++){
                    int tem=split[i].indexOf("=")+1;
                    split[i]=split[i].substring(tem);
                }
                split[split.length]=birthday;
                outcome = service.modify(split,num);
            }
            writer.print(outcome);
            writer.close();
        }

    }
}
