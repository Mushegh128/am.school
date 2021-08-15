package manager;


import db.DBConnectionProvider;
import model.User;
import model.UserType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserManager {

    private static Connection connection = DBConnectionProvider.getInstance().getConnection();

    public User getUserByID(int id) {
        String query = "SELECT * from `user` WHERE `id` =" + id;
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = getUserByResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return user;

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * from `user`";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                userList.add(User.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .surname(resultSet.getString(3))
                        .email(resultSet.getString(4))
                        .password(resultSet.getString(5))
                        .type(UserType.valueOf(resultSet.getString(6).toUpperCase(Locale.ROOT)))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public User getUserByEmailAndPassword(String email, String password) {
        String query = "SELECT * from `user` WHERE `email` = ? AND `password` = ?";
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = getUserByResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return user;
    }

    private static User getUserByResultSet(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return User.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .surname(resultSet.getString(3))
                        .email(resultSet.getString(4))
                        .password(resultSet.getString(5))
                        .type(UserType.valueOf(resultSet.getString(6).toUpperCase(Locale.ROOT)))
                        .build();
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addUser(User user) {
        String query = "INSERT INTO `taskmanagement`.`user` (`name`, `surname`, `email`, `password`, `type`) " +
                "VALUES (?,?,?,?,?); ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, String.valueOf(user.getType()));
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(0));
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;

    }

    public boolean deleteUserByID(int id) {
        String query = "DELETE FROM `user` WHERE id =" + id;

        try {
            Statement statement = connection.createStatement();
            int update = statement.executeUpdate(query);
            if (update > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

}


