package Servlets;

import Service.*;
import beans.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "indexServlet")
public class indexServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取会话变量中存储的信息
        HttpSession session = request.getSession(false);

        if(session==null){
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return;
        }
        request.setAttribute("name", session.getAttribute("name"));
        request.getRequestDispatcher("/index.jsp").forward(request,response);

    }
}
