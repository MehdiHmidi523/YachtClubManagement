import Controller.Administrator;
import View.Console;

public class Main {
    public static void main(String[] args){
        Console session = new Console();
        session.showWelcomeMessage();
        Administrator user = new Administrator(session);
        user.manipulate();
    }
}
