package services;

import dao.QuestionsDao;
import services.models.UserQuestion;

/**
 * Created by sabinapokhrel on 3/29/17.
 */
public class QuestionsDBServiceProvider {

  /** This methos gets all the questions from the database
   *
   * @return
   */
  public String getAllStoredQuestions() {
    QuestionsDao questionsDao = new QuestionsDao();
    String storedQuestions = questionsDao.getQuestions().replace("[", "Here is a list of all saved questions:\n").replaceAll(",", "\n•").replace("]","");
    return storedQuestions;
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