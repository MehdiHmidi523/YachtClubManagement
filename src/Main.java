import Controller.Administrator;
import View.Console;

public class Main {
    public static void main(String[] args){
        Console session = new Console();
        session.showWelcomeMessage();
        Administrator user = new Administrator(session);
        user.manipulate();
    }
   /*
    //Class Diagram
    There are dependencies not pictured here (such as the view using the MemberRegistry)  +

    //Sequence Diagrams
    Your sequence diagrams are not accurate. For example, in the Create Member diagram,
    it shows that the Console class calls "createMember()" on the Administrator class
     but this is obviously not possible as the createMember() function is private to the Administrator
     class and is actually called by the Administrator class on itself.

    //Code:

    There is no data validation in the model - it should care about maintaining its integrity.

    Returning internal data structures (such as the boat list in Member) is generally bad practice and breaks the Expert pattern. The functions that use these should instead call functions on Member that handle this in a better way or return immutable types that cannot be otherwise edited.

    The model should never print to console (e.g. in Member). If you need to show errors, it should use an Exception which is caught in the view and acted on there.
*/
}
