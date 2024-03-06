package oopGUIExam;

import java.util.LinkedList;
import java.util.List;

public class Log {
    private List<String> logMessages;
    public Log() {
        this.logMessages = new LinkedList<>();
    }
    public void isOver() {
        String message = "isOver";
        logMessages.add(message);
        System.out.println(message);
    }
    public void newMark(Position position) {
        String message = "newMark: " + position.x() + ", " + position.y();
        logMessages.add(message);
        System.out.println(message);
    }
    public void moved() {
        String message = "moved";
        logMessages.add(message);
        System.out.println(message);
    }
    public List<String> getLogMessages() {
        return logMessages;
    }
    public void printLogs() {
        logMessages.forEach(System.out::println);
    }

}
