package services.languageProcessor;

import java.io.*;
import java.util.ArrayList;

import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.ling.Datum;

public class TextClassifier {

  final static File PROPERTIES_FILE = new File("app/services/languageProcessor/poet.prop");
  final static File TRAIN_FILE = new File("app/services/languageProcessor/poet_1.train");
  final static File TEST_FILE = new File("app/services/languageProcessor/poet_1.test");

  static ColumnDataClassifier columnDataClassifier = new ColumnDataClassifier(PROPERTIES_FILE.getAbsolutePath());

  public static void main(String[] args){

    ArrayList<String> wordList = new ArrayList<String>();
    wordList.add("who");
    wordList.add("is");
    wordList.add("working");
    wordList.add("on");
    wordList.add("poet");

    TestingMethod(wordList);

  }


  public static String TestingMethod(ArrayList<String> wordList){
    System.out.println("I am in TestingMethod :Checkpoint 1");

    //ColumnDataClassifier columnDataClassifier = new ColumnDataClassifier(PROPERTIES_FILE.getAbsolutePath());

    Classifier<String,String> classifier =
      columnDataClassifier.makeClassifier(columnDataClassifier.readTrainingExamples(TRAIN_FILE.getAbsolutePath()));

    //for (String line : ObjectBank.getLineIterator(TEST_FILE.getAbsolutePath(), "utf-8")) {
    //Datum<String,String> d = columnDataClassifier.makeDatumFromLine(line);

      System.out.println("I am in TestingMethod :Checkpoint 2");
      Datum<String,String> d2 = columnDataClassifier.makeDatumFromStrings(arrayToString(wordList));
      System.out.println(classifier.classOf(d2));

      return classifier.classOf(d2).trim();
  }

  //new String[] {"output","What","is","working","on","poet-1"}
  private static String[] arrayToString(ArrayList<String> wordList){
    System.out.println("I am in arrayToString :Checkpoint 1");
    String words[] = new String[wordList.size()];
    wordList.add(0, "output");
    return wordList.toArray(words);
  }
}