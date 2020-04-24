package Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "outerFilter")
public class outerFilter implements javax.servlet.Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(false);
        HttpServletResponse response = (HttpServletResponse) resp;

        if(session==null){
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String login=(String) session.getAttribute("login");

        if("true".equals(login)){
            chain.doFilter(req, resp);
        }
    }

}
