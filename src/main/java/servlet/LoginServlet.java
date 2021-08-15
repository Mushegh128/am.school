package servlet;

import manager.UserManager;
import model.User;
import model.UserType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private static UserManager userManager = new UserManager();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = new UserManager().getUserByEmailAndPassword(email, password);
        if (user != null) {
            req.getSession().setAttribute("user",user);
            switch (user.getType()) {
                case STUDENT:
                    req.getRequestDispatcher("/student").forward(req,resp);
                    break;
                case TEACHER: {
                    req.getRequestDispatcher("/teacher").forward(req,resp);
                    break;
                }
                case DIRECTOR: {
                    req.getRequestDispatcher("/director").forward(req,resp);
                    break;
                }
            }
        } else {
            resp.sendRedirect("/");
        }

    }
}
