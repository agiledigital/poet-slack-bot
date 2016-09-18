package services.languageProcessor;
import java.lang.reflect.*;

class TaskMap{

    /*
    This method calls appropriate method based on the parameters passed
     */
    public static String questionMapping(String methodName, String argName) throws NoSuchMethodException,
            InvocationTargetException,IllegalAccessException{

        TaskMap tm = new TaskMap();
        //call the method at runtime according to the argument "methodName"
        Method m = TaskMap.class.getMethod(methodName, String.class);
        String returnVal = (String) m.invoke(tm, argName);
        return returnVal;
    }


    /*
    This method requests ticket info and returns it to the calling method
     */
    public String description_of_ticket(String ticket){
        String answer = "Description of the ticket " + ticket;
        System.out.println(answer);
        //String ans = JIRAconnector.getDescription(ticket);
        return answer;
    }

    /*
    This method requests assignee of ticket and returns it to the calling method
     */
    public String assignee_of_ticket(String ticket){
        String answer = "The person working on this ticket "+ ticket +" is ";
        System.out.println(answer);
        //String assignee = JIRAconnector.getAssignee(ticket);
        return answer;
    }
}