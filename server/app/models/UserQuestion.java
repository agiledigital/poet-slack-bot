package models;

/**
 * This class is a model for UserQuestions.
 * Member variables are questionNo and question.
 * Created by sabinapokhrel on 3/24/17.
 */

public class UserQuestion {
  public String getQuestion() {
    return question;
  }
  public int getQuestionNo() {
    return questionNo;
  }


  public void setQuestion(String question) {
    this.question = question;
  }
  public void setQuestionNo(int questionNo) {
    this.questionNo = questionNo;
  }

  private String question;
  private int questionNo;

  public UserQuestion(String question){
    this.question = question;
  }
  public UserQuestion(int questionNo,String question){
    this.question = question;
    this.questionNo = questionNo;
  }


}