package View;

import Model.Boat;
import Model.Member;
import Model.MemberRegistry;

import java.util.ArrayList;

/**
 * Created by Void on 28/09/2017 for the YachtClubManagement project.
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
    void displayEditMember(Member my);
    void editBoat(Member b);

    void editMemberPersonalNumber(Member editMember);

    int selectMember();
    int getInterestID();
    String getInterestName();
    String getInterestNr();

    void displayDeleteMember(MemberRegistry myList);
    void displayDeleteBoat(Member b);

    Member displayCreateMember();
    Boat displayAddBoat(Member nw);
    void displayShowBoats(ArrayList<Member> members);

    void displaySuccessOperation(String str);
    void displayErrorMessage(String str);


}
