package dataAccess;

import java.sql.*;
import java.util.ArrayList;
import javax.inject.Inject;

import play.db.*;

/**
 * Created by sabinapokhrel on 11/7/16.
 */


public class QuestionsDB {
  private Database db;

  @Inject
  public QuestionsDB(@NamedDatabase("poetdb") Database db) {
    this.db = db;
  }

  /**
   * SQL statement to add the question to database
   *
   * @param question
   */
  public void addQuestion(String question) {

    try (Connection connection = db.getConnection()) {
      PreparedStatement stmt = connection.prepareStatement("insert into questionsasked (question) values (?)");
      stmt.setString(1, question);
      stmt.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public String displayQuestions() {

    try (Connection connection = db.getConnection()) {
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