package services.languageProcessor;
import java.lang.reflect.*;

class TaskMap{

  /**
   * This method calls appropriate method on run time based on the
   * parameters (methodName and argName) passed and returns a value
   * returned by that method.
   * @param methodName
   * @param argName
   * @return
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
    public static String questionMapping(String methodName, String argName) throws NoSuchMethodException,
            InvocationTargetException,IllegalAccessException{

        TaskMap tm = new TaskMap();

        //call the method at runtime according to the argument "methodName"
        Method m = TaskMap.class.getMethod(methodName, String.class);
        String returnVal = (String) m.invoke(tm, argName);
        return returnVal;
    }

  /**
   * This method requests ticket info and returns it to the calling method
   * @param ticket
   * @return
   */
    public String description_of_ticket(String ticket){
        String answer = "Description of the ticket " + ticket;
        System.out.println(answer);
        //String ans = JIRAconnector.getDescription(ticket);
        return answer;
    }

  /**
   * This method requests assignee of ticket and returns it to the calling method
   * @param ticket
   * @return
   */
  public String assignee_of_ticket(String ticket){
        String answer = "The person working on "+ ticket +" is ";
        System.out.println(answer);
        return answer;
    }
}