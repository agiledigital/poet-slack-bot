package dao;

import java.sql.*;
import java.util.ArrayList;

import play.db.*;
import models.UserQuestion;

/**
 * Created by sabinapokhrel on 11/7/16.
 */


public class QuestionsDao {
  /**
   * SQL statement to add the question to questionsasked table.
   *
   * @param question
   */
  public void addQuestion(UserQuestion question) {

    try (Connection connection = DB.getConnection()) {
      PreparedStatement stmt = connection.prepareStatement("insert into questionsasked (question) values (?)");
      stmt.setString(1, question.getQuestion());
      stmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * This method contains the SQL statement to get stored data from questionsasked table.
   * It returns an arrayList of UserQuestion objects.
   * @return
   */
  public ArrayList<UserQuestion> getQuestions() {

    try (Connection connection = DB.getConnection()) {
      Statement statement = connection.createStatement();
      String sql = "SELECT * FROM questionsasked;";
      ResultSet resultSet = statement.executeQuery(sql);

      ArrayList<UserQuestion> questionList = new ArrayList<UserQuestion>();
      while (resultSet.next()) {
        String question = resultSet.getString("question");
        int questionNo = resultSet.getInt("questionNo");
        UserQuestion userQuestion = new UserQuestion(questionNo, question);
        questionList.add(userQuestion);
      }
      resultSet.close();
      statement.close();
      return questionList;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}