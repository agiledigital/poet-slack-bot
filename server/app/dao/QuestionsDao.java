package dao;

import java.sql.*;
import java.util.ArrayList;

import play.db.*;
import services.models.UserQuestion;

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
   * @return
   */
  public String getQuestions() {

    try (Connection connection = DB.getConnection()) {
      Statement statement = connection.createStatement();
      String sql = "SELECT * FROM questionsasked;";
      ResultSet resultSet = statement.executeQuery(sql);

      ArrayList<String> questionList = new ArrayList<String>();
      while (resultSet.next()) {
        String question = resultSet.getString("question");
        questionList.add(question);
      }
      resultSet.close();
      statement.close();
      return questionList.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}