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
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/teacher")
public class TeacherServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserManager userManager = new UserManager();
        List<User> allUsers = userManager.getAllUsers();
        List<User> students = new ArrayList<>();
        if (allUsers != null && !allUsers.isEmpty()){
            for (User user : allUsers) {
                if (user.getType().equals(UserType.STUDENT)){
                    students.add(user);
                }
            }
        }
        req.setAttribute("students",students);
        req.getRequestDispatcher("/teacher.jsp").forward(req,resp);

    }
}
