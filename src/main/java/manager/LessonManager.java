package manager;

import db.DBConnectionProvider;
import model.Lesson;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonManager {
    private static Connection connection = DBConnectionProvider.getInstance().getConnection();

    public static Lesson getLessonByID(int id) {
        String query = "SELECT * from `lesson` WHERE `id` =" + id;
        Lesson lesson = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            lesson = getLessonByResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return lesson;

    }

    public static List<Lesson> getAllLessons() {
        List<Lesson> lessonList = new ArrayList<>();
        String query = "SELECT * from `lesson`";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                lessonList.add(Lesson.builder()
                        .id(resultSet.getInt(1))
                        .title(resultSet.getString(2))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessonList;
    }

    private static Lesson getLessonByResultSet(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return Lesson.builder()
                        .id(resultSet.getInt(1))
                        .title(resultSet.getString(2))
                        .build();
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addLesson(Lesson lesson) {
        String query = "INSERT INTO `lesson` (`title`) VALUES (?); ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, lesson.getTitle());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                lesson.setId(generatedKeys.getInt(0));
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;

    }

    public static boolean deleteLessonByID(int id) {
        String query = "DELETE FROM `lesson` WHERE id =" + id;
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
