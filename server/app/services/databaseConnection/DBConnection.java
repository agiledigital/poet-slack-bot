package services.databaseConnection;

import play.Configuration;
import play.api.Play;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by sabinapokhrel on 11/7/16.
 */


public class DBConnection {
  private Configuration configuration = Play.current().injector().instanceOf(Configuration.class);
  /**
   * Method to connect to database
   * @return
   */
  Connection connectDB() {

    /* These variables must be made configurable */
    String host = configuration.getString("db-conf.host");
    String dbName = configuration.getString("db-conf.dbName");
    String username = configuration.getString("db-conf.username");
    String password = configuration.getString("db-conf.password");

    try {
      /* Dynamically load the JDBC driver */
      Class.forName("org.postgresql.Driver"); //load the driver

      /* Connect to the Postgres database */
      Connection db = DriverManager.getConnection(
        "jdbc:postgresql://" + host + "/" + dbName,
        username,
        password); //connect to the db
      return db;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * SQL statement to add the question to database
   * @param question
   */
  public void addQuestion(String question) {
    DBConnection DBConnection = new DBConnection();
    Connection connection = DBConnection.connectDB();

    DBConnection.createQuestionTable(connection);

    try{ try {
      PreparedStatement stmt = connection.prepareStatement("insert into questions (question) values (?)");
      //stmt.setInt(1, 1);
      stmt.setString(1, question);
      stmt.executeUpdate();
    } finally {
      connection.close();
    }
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates a table questions if it does not already exists
   * @param connection
   */
  public void createQuestionTable(Connection connection){
    try{
      PreparedStatement preparedStatement = connection.prepareStatement(
        "CREATE TABLE questions(qno SERIAL PRIMARY KEY, question TEXT NOT NULL);");
      preparedStatement.executeUpdate();
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String displayQuestions(){
    DBConnection DBConnection = new DBConnection();
    Connection connection = DBConnection.connectDB();

    DBConnection.createQuestionTable(connection);

    try{ try {
      Statement statement = connection.createStatement();
      String sql = "SELECT * FROM questions;";
      ResultSet resultSet = statement.executeQuery(sql);

      ArrayList<String> questionList = new ArrayList<String>();
      while ( resultSet.next() ) {
        String  question = resultSet.getString("question");
          questionList.add(question);
      }
      resultSet.close();
      statement.close();
      return questionList.toString();
    } finally {
      connection.close();
    }
    }catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
