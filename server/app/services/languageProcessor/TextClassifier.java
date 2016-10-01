package services.languageProcessor;

import java.io.*;

import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.classify.LinearClassifier;
import edu.stanford.nlp.ling.Datum;
import edu.stanford.nlp.objectbank.ObjectBank;
import edu.stanford.nlp.util.ErasureUtils;

class TextClassifier {

  final static File PROPERTIES_FILE = new File("server/app/services/languageProcessor/poet.prop");
  final static File TRAIN_FILE = new File("server/app/services/languageProcessor/poet_1.train");
  final static File TEST_FILE = new File("server/app/services/languageProcessor/poet_1.test");

  public static void main(String[] args) throws Exception {


    ColumnDataClassifier cdc = new ColumnDataClassifier(PROPERTIES_FILE.getAbsolutePath());
    Classifier<String,String> cl =
      cdc.makeClassifier(cdc.readTrainingExamples(TRAIN_FILE.getAbsolutePath()));
    for (String line : ObjectBank.getLineIterator(TEST_FILE.getAbsoluteFile(), "utf-8")) {
      // instead of the method in the line below, if you have the individual elements
      // already you can use cdc.makeDatumFromStrings(String[])
      Datum<String,String> d = cdc.makeDatumFromLine(line);
      System.out.println(line + "  ==>  " + cl.classOf(d));
    }

    demonstrateSerialization();
  }

  public static void TestingMethod() {


    File propertiesFile = new File("server/app/services/languageProcessor/examples/cheese2007.prop");
    File trainFile = new File("server/app/services/languageProcessor/examples/cheese2007.train");
    File testFile = new File("server/app/services/languageProcessor/examples/cheese2007.test");

    ColumnDataClassifier cdc = new ColumnDataClassifier(propertiesFile.getAbsolutePath());

    Classifier<String,String> cl =
      cdc.makeClassifier(cdc.readTrainingExamples(trainFile.getAbsolutePath()));
    for (String line : ObjectBank.getLineIterator(testFile.getAbsolutePath(), "utf-8")) {
      // instead of the method in the line below, if you have the individual elements
      // already you can use cdc.makeDatumFromStrings(String[])
      Datum<String,String> d = cdc.makeDatumFromLine(line);
      System.out.println(line + "  ==>  " + cl.classOf(d));
    }
    try {
      demonstrateSerialization();
    }
    catch(Exception e){

    }
  }


  public static void demonstrateSerialization()
    throws IOException, ClassNotFoundException {

    System.out.println("Demonstrating working with a serialized classifier");
    ColumnDataClassifier cdc = new ColumnDataClassifier(PROPERTIES_FILE.getAbsolutePath());
    Classifier<String,String> cl =
      cdc.makeClassifier(cdc.readTrainingExamples(TRAIN_FILE.getAbsolutePath()));

    // Exhibit serialization and deserialization working. Serialized to bytes in memory for simplicity
    System.out.println(); System.out.println();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(cl);
    oos.close();
    byte[] object = baos.toByteArray();
    ByteArrayInputStream bais = new ByteArrayInputStream(object);
    ObjectInputStream ois = new ObjectInputStream(bais);
    LinearClassifier<String,String> lc = ErasureUtils.uncheckedCast(ois.readObject());
    ois.close();
    ColumnDataClassifier cdc2 = new ColumnDataClassifier(PROPERTIES_FILE.getAbsolutePath());

    // We compare the output of the deserialized classifier lc versus the original one cl
    // For both we use a ColumnDataClassifier to convert text lines to examples
    for (String line : ObjectBank.getLineIterator(TEST_FILE.getAbsolutePath(), "utf-8")) {
      Datum<String,String> d = cdc.makeDatumFromLine(line);
      Datum<String,String> d2 = cdc2.makeDatumFromLine(line);
      System.out.println(line + "  =origi=>  " + cl.classOf(d));
      System.out.println(line + "  =deser=>  " + lc.classOf(d2));
    }
  }

}

