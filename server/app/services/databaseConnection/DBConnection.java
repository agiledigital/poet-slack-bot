package services.databaseConnection;

import java.sql.*;

/**
 * Created by sabinapokhrel on 11/7/16.
 */


public class DBConnection {



  /**
   * Method to connect to database
   * @return
   */
  Connection connectDB() {

    /* These variables must be made configurable */
    String host = "localhost";
    String database = "poetdb";
    String username = "sabina";
    String password = "12345";

    try {
      /* Dynamically load the JDBC driver */
      Class.forName("org.postgresql.Driver"); //load the driver

      /* Connect to the Postgres database */
      Connection db = DriverManager.getConnection(
        "jdbc:postgresql://" + host + "/" + database,
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
  public static void addQuestionToDB(String question) {
    DBConnection DBConnection = new DBConnection();
    Connection connection = DBConnection.connectDB();

    DBConnection.createDatabase(connection);

    try{ try {
      System.out.println("Adding question to db");
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
  void createDatabase(Connection connection){
    try{
      PreparedStatement stmt = connection.prepareStatement(
        "CREATE TABLE questions(qno SERIAL PRIMARY KEY, question TEXT NOT NULL);");
      stmt.executeUpdate();
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

}
