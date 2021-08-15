package servlet;

import manager.RateManager;
import model.Rate;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/student")
public class StudentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RateManager rateManager = new RateManager();
        List<Rate> userRates = rateManager.getRateByUser((User) req.getSession().getAttribute("user"));
        double sumResultOfRates = 0.0;
        if (userRates != null && !userRates.isEmpty()) {
            for (Rate rate : userRates) {
                sumResultOfRates += rate.getRating();
            }
            sumResultOfRates = sumResultOfRates / userRates.size();
        }
        req.setAttribute("sumResultOfRates", sumResultOfRates);
        req.setAttribute("userRates", userRates);
        req.getRequestDispatcher("/student.jsp").forward(req, resp);
    }
}
