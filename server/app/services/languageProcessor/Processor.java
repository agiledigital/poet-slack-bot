package services.languageProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

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
    HashMap<String, String> posTagging = posTagging(annotation);

    //Get ID (TicketID, issueID, ProjectID)
    String uniqueID = getUniqueID(posTagging);

    ArrayList<String> wordList = tokenizeQuestion(annotation);


    //Create an arrayList for keywords
    ArrayList<String> keywordList = getKeywords(annotation);

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
   * @param annotation
   * @return
   */
  public static ArrayList<String> getKeywords(Annotation annotation){
    ArrayList<String> keywords_found = new ArrayList<String>();

    //Analysis of the question
    System.out.println("\nAnalysis of the question:");
    String topic = "ticket";
    keywords_found.add(topic);

    ArrayList<String> wordList = tokenizeQuestion(annotation);
    keywords_found = QuestionMapping(wordList);

    System.out.println("\nKeywords found:");
    System.out.println(keywords_found.toString());


    System.out.println("\nFinal keyword list:");
    System.out.println(keywords_found.toString());
    return keywords_found;
  }




  /**
   * This method extracts the uniqueID for issue, project.
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
    if (keywords.contains("what")) {
      keywords.add("description");
    }
    if (keywords.contains("who")) {
      keywords.add("person");
    }

    if (keywords.contains("describe") || keywords.contains("about") ) {
      if(!keywords.contains("description")) {
        keywords.add("description");
      }
      if(!keywords.contains("what")) {
        keywords.add("what");
      }
    }

    if (keywords.contains("give") && keywords.contains("details") ) {
      if(!keywords.contains("description")) {
        keywords.add("description");
      }
      if(!keywords.contains("what")) {
        keywords.add("what");
      }
    }

    if (keywords.contains("person")){
      if(!keywords.contains("who")){
        keywords.add("who");
      }
    }

    if (keywords.contains("assignee")){
      if(!keywords.contains("who")){
        keywords.add("who");
      }
      if(!keywords.contains("person")){
        keywords.add("person");
      }
    }

    if (keywords.contains("person") && keywords.contains("ticket")) {
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
  public static HashMap<String, String> posTagging(Annotation annotation){
    System.out.println("\nPOS tagging:");

    HashMap<String, String> posTagging = new HashMap<String, String>();

    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
    for (CoreMap sentence : sentences) {
      for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
        String word = token.get(CoreAnnotations.TextAnnotation.class);

        // this is the POS tag of the token
        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

        posTagging.put(pos, word);
      }
    }

    return posTagging;
  }
}