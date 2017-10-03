package View;

import Model.Boat;
import Model.Member;
import Model.MemberRegistry;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Void on 28/09/2017 for the YachtClubManagement project.
 */
public class Console implements DisplayInstructions {

    Scanner sc;
    public Console(){
        sc  = new Scanner(System.in);
    }

    @Override
    public void showWelcomeMessage() {
        System.out.println(" _____________________________________________");
        System.out.println("|                                             |");
        System.out.println("| ҉҉҉҉҉҉ Welcome to the Jolly Pirate   ҉҉҉҉҉҉ |") ;
        System.out.println("|_____________________________________________|\n");

    }
    @Override
    public int userSelection() {
        System.out.println("----------- Selection -----------");
        System.out.println("0.) Exit");
        System.out.println("1.) Show members");
        System.out.println("2.) Show Current Berth allocations");   // TODO: keep in mind for grade 3 and 4
        System.out.println("3.) Create a new member");
        System.out.println("4.) Edit a members/boats information");
        System.out.println("5.) Delete a Member");
        return getChoice(0,5);
    }
    @Override
    public void exitDisplay() {
        System.out.println(" _____________________________________________");
        System.out.println("|                                             |");
        System.out.println("| ҉҉҉҉҉҉        End of Session         ҉҉҉҉҉҉ |") ;
        System.out.println("|_____________________________________________|\n");
        // Could implement a timer and add time spent in the session
    }

    /**********************   LIST JOLLY PIRATE's MEMBERS   *******************************/
    @Override
    public void showMemberRegistry(ArrayList<Member> memberList) {
        if(memberList.size()==0){
            displayErrorMessage("The Yacht Club has no members!");
        }else{
            System.out.println("-----------Member Registry-----------");
            if(selectListType()==1)
                showCompactList(memberList);
            else
                showVerboseList(memberList);
        }

    }
    @Override
    public int selectListType() {
        System.out.println("-----------List Type-----------");
        System.out.println("1.) Compact list (name, member id and number of boats) " +
                         "\n2.) Verbose list (name, personal number, member id and boats with boat information)");
        return getChoice(1,2);
    }
    @Override
    public void showCompactList(Iterable<Member> members) { // the reason behind iterable is to
        if (!members.iterator().hasNext())
            System.err.println("________ EMPTY MEMBER LIST ________");
        else{
            int count = 0;
            for (Member m : members){
                System.out.println(++count +".) \t Member-ID: " + m.getM_Id());
                System.out.println("\t Name: " + m.getM_name());
                int boats = m.getM_numOfBoats();
                System.out.println("\t  Number of Boats: " + boats);
            }
        }
    }
    @Override
    public void showVerboseList(Iterable<Member> members) {
        if (!members.iterator().hasNext())
            System.out.println("________ EMPTY MEMBER LIST ________"); //in case this method was called directly without going through showMemberRegistry.
        else{
            int counter = 0;
            for (Member m : members){
                System.out.print(++counter + ".)");
                System.out.print("||||| Name: " + m.getM_name());
                System.out.print(", Personal-number: " + m.getM_personal_number());
                System.out.print(", Member-ID: " +m.getM_Id());
                System.out.println(", Registered Boats: ");
                DisplayMemberBoatInfo(m.getM_boats());
            }
        }
    }

    private void DisplayMemberBoatInfo(ArrayList<Boat> m_boats) {
        for(Boat b : m_boats){
            System.out.println(" ID: "+b.getB_id()+ " .) Type: "+b.getType()+" : Name "+b.getB_Name()+". //LENGTH: "+b.getLength());
        }
    }

    /***************************************************************************/

    private int getChoice(int first, int last) {
        int choice;
        do{
            System.out.println("Please select Instruction");
            choice = sc.nextInt();
        }while(choice>=last && choice<=first);
        return choice;
    }

    @Override
    public void showMemberInformation(Member my) {
        System.out.println("\tMember ID : " + my.getM_Id());
        System.out.println("\tName: " + my.getM_name());
        System.out.println("\tPersonal-number: " + my.getM_personal_number() + "");
        DisplayMemberBoatInfo(my.getM_boats());
    }

    @Override
    public void displayEditMember(Member my) {
        System.out.println("-----------Select Member Info to EDIT ! -----------");
        System.out.println("0.) Exit");
        System.out.println("1.) Name");
        System.out.println("2.) Personal Number");
        System.out.println("3.) Boat");
        if(getChoice(0,3)==1){
            editMemberName(my); // put member in brackets.
        }else if(getChoice(0,3)==2){
            editMemberPersonalNumber(my);
        }else if(getChoice(0,3)==3){
            editBoat(my);
        }else
            System.out.println(" Exit Search !");
    }

    public void editMemberName(Member editMember){
        System.out.println("Please enter the new first name and last name");
        String name = sc.nextLine();
        editMember.setM_name(name);
        displaySuccessOperation("*****  CHANGED MEMBER NAME *****");
    }

    @Override
    public void editBoat(Member b) {
        DisplayMemberBoatInfo(b.getM_boats());

        System.out.println("Which Boat do you Want to Edit ?? \nWrite Down Boat ID: ");

        int choice = getChoice(1,b.getM_boats().size() );
        Boat myBoat = b.getM_boats().get(choice);
        System.out.println(" *********** EDIT BOAT OPERATION ***********");
        System.out.println("-----------  Change Boat's type or keep it as it is ! -----------");
        for (int i = 0; i< Boat.Boatstype.values().length; i++ ){
            System.out.println("\t" + i + ".) " + Boat.Boatstype.values()[i]);
        }
        int choix = getChoice(0, Boat.Boatstype.values().length-1);
        Boat.Boatstype t = Boat.Boatstype.values()[choix];
        myBoat.setType(t);

        System.out.println("Change Boat's length:" + myBoat.getLength());
        System.out.println("Please enter the new length! ");
        myBoat.setLength(sc.nextDouble());

        System.out.println("Change Boat's Name:" + myBoat.getB_Name());
        System.out.println("Please enter the new Name! ");
        myBoat.setB_Name(sc.nextLine());
        displaySuccessOperation("Edited Boat " +myBoat.getB_id());
    }

    @Override
    public void editMemberPersonalNumber(Member editMember){
        System.out.println("Please enter the new personal number in this form YYMMDD-XXXX");
        String personal_number = sc.nextLine();
        editMember.setM_personal_number(personal_number);
        displaySuccessOperation("***** CHANGED PERSONAL-NUMBER *****");

    }

    @Override
    public int selectMember() {
        System.out.println("-----------Select Member -----------");
        System.out.println("Find member using:");
        System.out.println("0.) Exit");
        System.out.println("1.) Member id");
        System.out.println("2.) Name");
        System.out.println("3.) Personal Number");
        return getChoice(1,3);
    }
    @Override
    public int getInterestID() {
        System.out.println("********* Input Member id *********");
        int myId;
        do{
            myId= sc.nextInt();
        }while (myId > 0);
        return myId;
    }
    @Override
    public String getInterestName() {
        System.out.println("********* Input Member Name *********");
        String str="";
        while (str.isEmpty() && sc.hasNext()){
            str = sc.nextLine();
        }
        return str;
    }
    @Override
    public String getInterestNr() {
        System.out.println("********* Input Member Personal Number *********");
        String swedId= sc.nextLine();
        while(!isCorrect(swedId,getFirstPart(swedId),getSecondPart(swedId))){
           swedId= sc.nextLine();
        }
        return swedId;
    }

    @Override
    public void displayDeleteMember(MemberRegistry myList) {
        System.out.println("Choose Member to delete its information or one of its Boats");
        System.out.println("0.) Exit");
        System.out.println("1.) Id member to delete");
        System.out.println("2.) Id member to delete its boat(s)");

        showVerboseList(myList.getMemberList());
        if(getChoice(0,2)==1){
            myList.deleteMember(getInterestID());
        }else if(getChoice(0,2)==2){
            displayDeleteBoat(myList.idMember(getInterestID()));
        }else
            System.out.println(" Exit Deletion !");
        //TODO: IMPLEMENT
    }

    @Override
    public void displayDeleteBoat(Member b) {

        DisplayMemberBoatInfo(b.getM_boats());
        System.out.println("Which Boat do you Want to Delete ?? \nWrite Down Boat ID: ");
        int choice = getChoice(1,b.getM_boats().size() );
        b.getM_boats().remove(choice);
        System.out.println(b.getM_name()+"'s updated Boats list : ");
        DisplayMemberBoatInfo(b.getM_boats());
        displaySuccessOperation(" DELETED BOAT FROM MEMBER AND BERTHS! ");
    }

    @Override
    public Member displayCreateMember() {
        System.out.println("*****   Create a new member   *****") ;
        Member nw = new Member();
        try{
            nw.setM_name(getInterestName());
            boolean end=false;
            do{
                System.out.println("Enter "+nw.getM_name()+"'s Personal Number: yymmdd-xxxx ");
                String swedId= sc.nextLine();
                 /* Check Encapsulation */
                if(isCorrect(swedId,getFirstPart(swedId),getSecondPart(swedId))){
                    System.out.println(swedId+ " Valid Personal Number");
                    nw.setM_personal_number(swedId);
                    end=true;
                } else
                    System.out.println(swedId+"Invalid Personal Number!! Again:");
            }while(!end);

            System.out.println("How many Boats is Member "+nw.getM_name()+ " registering ?");
            int nm=sc.nextInt();
            do{
                if(nm>0)
                    nw.setM_numOfBoats(nm);
            }while(nm<0);

            return nw;
        }catch(InputMismatchException e){
            displayErrorMessage(": SYNTAX ERROR!! Follow input type correctly !!"+ e.getMessage());

        }
        return nw;
    }

    @Override
    public Boat displayAddBoat(Member nw) {
        Boat new_Boat = new Boat() ;
        System.out.println("*****    Add Boat to Member: " + nw.getM_name()+"   *****") ;
        try{
            System.out.println("\tSelect Boat's type:");
            for (int i = 0; i< Boat.Boatstype.values().length; i++ ){
                System.out.println(i + ".) " + Boat.Boatstype.values()[i]);
            }
            int choice = getChoice(0, Boat.Boatstype.values().length-1);
            Boat.Boatstype myBoat = Boat.Boatstype.values()[choice];
            new_Boat.setType(myBoat);

            System.out.println("\tSpecify Boat's Length");
            Double len = sc.nextDouble();
            new_Boat.setLength(len);
            Scanner name = new Scanner(System.in);
            System.out.println("\tSpecify Boat's Name");
            String nm = name.nextLine();
            new_Boat.setB_Name(nm);

            return new_Boat;
        }catch(InputMismatchException e){
         displayErrorMessage(": SYNTAX ERROR!! Follow input type correctly !!");
            return new_Boat;
        }
    }

    @Override
    public void displayShowBoats(ArrayList<Member> members) {
        if(members.size()==0){
            displayErrorMessage("The Yacht Club has no members!");
        }else
        for (Member m : members){
            System.out.print(" //Owner: "+ m.getM_name());
            DisplayMemberBoatInfo(m.getM_boats());
        }
    }

    @Override
    public void displaySuccessOperation(String str) {
        System.out.println("_______ SUCCESSFULLY "+str+" ______");
    }

    @Override
    public void displayErrorMessage(String str) {
        System.err.println("_______ ERROR! "+str+" ______");
    }


    /************************ Personal Number Helper Methods ***************************/
    private static String getFirstPart(String swedId){
        String fPart = swedId.substring(0,6);
        return fPart;
    }

    private static String getSecondPart(String swedId){
        String sPart = swedId.substring(7);
        return sPart;
    }

    private static boolean isCorrect(String id,String fPart,String sPart){
        int month=Integer.parseInt(fPart.substring(2,4));
        int day=Integer.parseInt(fPart.substring(4));
        if(month>12 || month ==0)
            System.out.println(id+"(Invalid month)");
        if(day>31 || day ==0)
            System.out.println(id+"(Invalid day)");

        for(int i=0;i<sPart.length();i++)
            if(Character.isLetter(sPart.charAt(i)))
                System.out.println("Error, Second part should contain only digits! or is it a temporary number? ....");
        return checksum(id);
    }

    private static boolean checksum(String theId){

        theId = theId.replace("-", ""); // convert to a string filled with integers.
        int x;
        int sum=0;

        for(int i=0;i<9;i++){//9 is the length of the new string.
            if(i%2==0){
                x=2*(Character.getNumericValue(theId.charAt(i)));
                if(x>=10)
                    x=(x%10)+(x/10);
            }
            else
                x=Character.getNumericValue(theId.charAt(i));
            sum+=x; //the resulting integers are added as the array is filled
        }
        int checksum =10-(sum%10);
        if(checksum == Character.getNumericValue(theId.charAt(theId.length()-1)))
            return true;

        return false;
    }
}
