package services;

import dao.QuestionsDao;
import models.UserQuestion;
import java.util.*;

/**
 * Created by sabinapokhrel on 3/29/17.
 */
public class QuestionsDBServiceProvider {

  /** This methos gets all the questions from the database as an arrayList of UserQuestion objects,
   * and returns a string in a specific format for better presentation.
   * @return
   */
  public String getAllStoredQuestions() {
    QuestionsDao questionsDao = new QuestionsDao();
    ArrayList<UserQuestion> storedQuestions = questionsDao.getQuestions();
    StringBuffer userResponse = new StringBuffer("Here is a list of all saved questions:");

    // Iterate through the list and add the questions to userResponse StringBuffer
    for (UserQuestion temp : storedQuestions) {
      userResponse.append(new StringBuffer("\n• "));
      userResponse.append(new StringBuffer(temp.getQuestion()));
    }

    return userResponse.toString();
  }

  /** This method inserts new question into the database
   *
   * @param question
   */
  public void addQuestion(String question) {
    UserQuestion userQuestion = new UserQuestion(question);
    QuestionsDao questionsDao = new QuestionsDao();
    questionsDao.addQuestion(userQuestion);
  }
}