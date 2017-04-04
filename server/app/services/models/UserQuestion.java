package services.models;

/**
 * Created by sabinapokhrel on 3/24/17.
 */
public class UserQuestion {
  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  private String question;

  public UserQuestion(String question){
    this.question = question;
  }
}
