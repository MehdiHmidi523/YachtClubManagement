 /**
 * Created by Void on 27/09/2017 for the YachtClubManagement project.
 */

import Controller.Administrator;
import View.Console;

public class Main {
    public static void main(String[] args){
        Console session = new Console();
        session.showWelcomeMessage();
        //login service?
        Administrator secretary = new Administrator(session);  //  TODO: change when implementing authentication in next iteration.
        secretary.manipulate();
    }

}
