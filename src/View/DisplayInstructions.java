package View;

import Model.Boat;
import Model.Member;
import java.util.ArrayList;

/**
 * Created by Mehdi on 28/09/2017 for the YachtClubManagement project.
 */
public interface DisplayInstructions {

    void showWelcomeMessage();
    int  userSelection();
    void exitDisplay();
    void showMemberRegistry(ArrayList<Member> memberList);
    int  selectListType();
    void showCompactList(Iterable<Member> m_it); 	// “Compact List”; name, member id and number of boats
    void showVerboseList(Iterable<Member> m_it); 	// “Verbose List”; name, personal number, member id and boats with boat information

    void showMemberInformation(Member my);
    void displayEditMember();                       // Edit Boat Info is only allowed when a Member is chosen.
    int selectMember();
    int getInterestID();
    String getInterestName();
    String getInterestNr();

    Member displayCreateMember();
    void displayDeleteMember();

    Boat displayAddBoat(Member nw);
    void displayShowBoats(ArrayList<Member> members);

    void displayDeleteBoat();
    void displayConfirmationAlert(String toBeConfirmedString);

    void displaySuccessOperation(String str);
    void displayErrorMessage(String str);


}
