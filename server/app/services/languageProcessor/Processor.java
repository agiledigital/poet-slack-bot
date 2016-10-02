package services.languageProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.lang.reflect.*;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;

public class Processor {
  /**
   *
   * @param question
   * @return question mapping as a String array
   */
  public static String[] processQuestion(String question) {

    DecisionTree decisionTree = new DecisionTree();

    //Create NLP pipeline
    StanfordCoreNLP pipeline = createNlpPipeline();

    // Annotate the question.
    System.out.println("Question: " + question);
    Annotation annotation = new Annotation(question);
    pipeline.annotate(annotation);

    //POS tagging
    HashMap<String, String> posTagging = posTagging_(annotation);

    //Get ID (TicketID, issueID, ProjectID)
    String uniqueID = getUniqueID(posTagging);

    ArrayList<String> wordList = tokenizeQuestion(annotation);


    //get wh-question
    String wh_question = getWhQuestion(posTagging);

    //Create an arrayList for keywords
    ArrayList<String> keywordList = getKeywords(wh_question, uniqueID);

    //Question mapping
    System.out.println("\nQuestion mapping: " + decisionTree.traverse(keywordList) + "(" + uniqueID + ")");

    String questionMapping[] = new String[2];
    questionMapping[0] = decisionTree.traverse(keywordList);
    questionMapping[1] = uniqueID;

    if (questionMapping[0] == null ){
      questionMapping[0] = "NoQuestionFound";
    }
    if (questionMapping[1] == null){
      questionMapping[1] = "NoIdFound";
    }

    System.out.println("questionMapping[0] -> "+ questionMapping[0]);
    return questionMapping;
  }

  public static ArrayList<String> tokenizeQuestion(Annotation annotation) {

    ArrayList<String> wordList = new ArrayList<String>();

    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
    for (CoreMap sentence : sentences) {
      for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
        String word = token.get(CoreAnnotations.TextAnnotation.class);
        wordList.add(word.toLowerCase());

      }
      wordList.add("ticket");
    }
    System.out.println("I am in tokenizeQuestion :" + wordList.toString());
    return wordList;
  }

  /**
   * This method creates a list of keywords found in the questions,
   * analyses the list, and adds some more words to the list if required.
   * @param wh_question
   * @param uniqueID
   * @return
   */
  public static ArrayList<String> getKeywords(String wh_question, String uniqueID){
    ArrayList<String> keywords_found = new ArrayList<String>();

    //Analysis of the question
    System.out.println("\nAnalysis of the question:");
    String topic = "ticket";
    keywords_found.add(topic);

    if (wh_question != null) {
      String key = QuestionTypeMapping(wh_question).toLowerCase();
      keywords_found.add(wh_question);
      keywords_found.add(key);
    }

    System.out.println("\nKeywords found:");
    System.out.println(keywords_found.toString());

    keywords_found = QuestionMapping(keywords_found);

    System.out.println("\nFinal keyword list:");
    System.out.println(keywords_found.toString());
    return keywords_found;
  }


  /**
   * This method extracts the uniqueID for ticket, issue, project.
   * If it is found, return an error message
   * saying the question cannot be understood.
   * It means that no ticket number is found.
   * @param posTagging
   * @return
   */
  public static String getUniqueID(HashMap<String, String> posTagging){
    int numberOfIdFound = infoExtraction(posTagging, "NN").size();
    if(numberOfIdFound == 0) {
      return null;
    }

    String keyword = infoExtraction(posTagging, "NN").get(numberOfIdFound-1);

    return keyword;
  }

  /**
   * This method returns a Wh-question (if any)
   * First it check for Wh-pronoun (WP) - Who, what,
   * then for Wh-adverb (WRB)  - when/ where,
   * and then Wh-determiner (WDT)  - which
   * @param posTagging
   * @return
   */
  public static String getWhQuestion(HashMap<String, String> posTagging){
    String wh_question = null;

    if (infoExtraction(posTagging, "WP").size() != 0) {
      wh_question = infoExtraction(posTagging, "WP").get(0).toLowerCase();
    }
    else if (infoExtraction(posTagging, "WRB").size() != 0) {
      wh_question = infoExtraction(posTagging, "WRB").get(0).toLowerCase();
    }
    else if (infoExtraction(posTagging, "WDT").size() != 0) {
      wh_question = infoExtraction(posTagging, "WDT").get(0).toLowerCase();
    }

    return wh_question;
  }

  /**
   * This method creates the Stanford CoreNLP pipeline
   * and returns it to the calling method
   * @return
   */
  public static StanfordCoreNLP createNlpPipeline(){
    Properties props = PropertiesUtils.asProperties("annotators", "tokenize,ssplit,pos");
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    return pipeline;
  }



  /**
   *
   * @param keywords
   * @return
   */
  public static ArrayList<String> QuestionMapping(ArrayList<String> keywords){
    ArrayList<String> ticket_ = new ArrayList<>();
    ticket_.add("ticket");


    ArrayList<String> about_ = new ArrayList<>();
    about_.add("about");

    ArrayList<String> show_ = new ArrayList<>();
    show_.add("show");

    ArrayList<String> give_ = new ArrayList<>();
    give_.add("give");

    ArrayList<String> assignee_of_ticket = new ArrayList<>();
    assignee_of_ticket.add("person");
    assignee_of_ticket.add("ticket");

    if (keywords.containsAll(about_)) {
      keywords.add("description");
    }

    if (keywords.containsAll(assignee_of_ticket)) {
      keywords.add("assignee");
    }


    return keywords;
  }

  /**
   * This method extracts words with certain tag
   * For e.g words with tag NOUN or words with tag VERB
   * @param posTagging
   * @param tag
   * @return
   */
  public static ArrayList<String> infoExtraction(HashMap<String, String> posTagging, String tag){

    ArrayList<String> list = new ArrayList<String>();

    if (posTagging.containsKey(tag)){
      list.add(posTagging.get(tag));
    }
    return list;
  }

  /**
   * This method performs POS tagging.
   * It takes annotation as an argument and returns ArrayList<String[]>
   * @param annotation
   * @return
   */
  public static ArrayList<ArrayList<String>> posTagging(Annotation annotation){

    ArrayList<ArrayList<String>> posTagging = new ArrayList<ArrayList<String>>();

    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
    for (CoreMap sentence : sentences) {
      for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
        String word = token.get(CoreAnnotations.TextAnnotation.class);

        // this is the POS tag of the token
        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
        String[] POSS = new String[2];
        POSS[0] = word;
        POSS[1] = pos;

        ArrayList<String> arr = new ArrayList<String>();
        arr.add(word);
        posTagging.add(arr);

      }
    }

    //Print POS tag along with the words
    for (ArrayList<String> e: posTagging)
      System.out.println(e.get(0) + " -> " + e.get(1));

    return posTagging;
  }

  /**
   * This method performs POS tagging.
   * It takes annotation as an argument and returns ArrayList<String[]>
   * @param ann
   * @return
   */
  public static HashMap<String, String> posTagging_(Annotation ann){
    System.out.println("\nPOS tagging:");

    HashMap<String, String> posTagging = new HashMap<String, String>();

    List<CoreMap> sentences = ann.get(CoreAnnotations.SentencesAnnotation.class);
    for (CoreMap sentence : sentences) {
      for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
        String word = token.get(CoreAnnotations.TextAnnotation.class);
        // this is the POS tag of the token
        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);


        //Problem here is tat when another noun is found, it replaces the old value
        posTagging.put(pos, word);
        //posTagging.merge(pos, word, String::concat); //find a merging function
      }
    }

    //Print POS tag along with the words
    Iterator it = posTagging.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry)it.next();
      System.out.println(pair.getKey() + " -> " + pair.getValue());
      //it.remove(); // avoids a ConcurrentModificationException
    }

    return posTagging;
  }


  /**
   *
   * @param keyword
   * @return
   */
  public static String QuestionTopicMapping(String keyword){
    //System.out.println("This question is about "+ keyword);
    String topic = "ticket";
    return topic;
  }


  /**
   *
   * @param keyword
   * @return
   */
  public static String QuestionTypeMapping(String keyword){
    String keyy = null;

    switch (keyword){
      case "who": keyy = "person"; break;
      case "what": keyy = "description"; break;
      case "when": keyy = "date/time"; break;
      default: break;
    }
    return keyy;
  }
}