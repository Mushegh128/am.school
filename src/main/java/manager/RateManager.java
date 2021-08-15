package manager;

import db.DBConnectionProvider;
import model.Rate;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RateManager {
    private static Connection connection = DBConnectionProvider.getInstance().getConnection();

    public static boolean addRate(Rate rate) {
        String query = "INSERT INTO `rate` (`user_id`, `lesson_id`, `rating`) " +
                "VALUES (?,?,?); ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, rate.getUser().getId());
            preparedStatement.setInt(2, rate.getLesson().getId());
            preparedStatement.setInt(3, rate.getRating());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                rate.setId(generatedKeys.getInt(0));
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;

    }

    public List<Rate> getRateByUser(User user) {
        List<Rate> ratesOfUser = new ArrayList<>();
        String query = "SELECT * from `rate` WHERE `user_id` =" + user.getId();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                Rate rate = new Rate();
                rate.setId(resultSet.getInt(1));
                UserManager userManager = new UserManager();
                rate.setUser(userManager.getUserByID(resultSet.getInt(2)));
                rate.setLesson(LessonManager.getLessonByID((resultSet.getInt(3))));
                rate.setRating(resultSet.getInt(4));
                ratesOfUser.add(rate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ratesOfUser;
    }



//    public static User getRateByUser(int id) {
//        String query = "SELECT * from `user` WHERE `id` =" + id;
//        User user = null;
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            user = getUserByResultSet(resultSet);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//        }
//        return user;
//
//    }
//
//    public static User getRateByLesson(int id) {
//        String query = "SELECT * from `user` WHERE `id` =" + id;
//        User user = null;
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            user = getUserByResultSet(resultSet);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//        }
//        return user;
//
//    }


}
