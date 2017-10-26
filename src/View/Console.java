package View;

import Controller.Administrator;
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

    private Scanner sc;
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
        System.out.println("2.) Show Current Berth allocations ");
        System.out.println("3.) Create a new member");
        System.out.println("4.) Edit a members/boats information");
        System.out.println("5.) Delete a Member");
        System.out.println("6.) Search members");
        return getChoice(0,6);
    }

    @Override
    public void exitDisplay() {
        System.out.println(" _____________________________________________");
        System.out.println("|                                             |");
        System.out.println("| ҉҉҉҉҉҉        End of Session         ҉҉҉҉҉҉ |") ;
        System.out.println("|_____________________________________________|\n");

    }

    /*
     * LIST JOLLY PIRATE's MEMBERS
     * @param memberList;
     *
     */
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
                System.out.println(++count +".) \n\t Member-ID: " + m.getM_Id());
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
                System.out.println(", REGISTERED Boats: ");
                DisplayMemberBoatInfo(m);
            }
        }
    }

    private void DisplayMemberBoatInfo(Member b) {
        for(Boat boats : b.getM_boats()){
            System.out.println("\t ID: "+boats.getB_id()+ " .) Type: "+boats.getType()+" : Name "+boats.getB_Name()+". LENGTH: "+boats.getLength());
        }
    }

    private int getChoice(int first, int last) {
        try{
            int choice;
            do{
                System.out.println("Please select Instruction Number");
                choice = sc.nextInt();
            }while(choice>last || choice<first); // logical OR || so that the loop continues if one of the conditions is not correct
            return choice;
        }catch(InputMismatchException i){
            //System.err.println("ERROR is in getChoice() CONTINUE ");
            return -99;
        }
    }

    @Override
    public void showMemberInformation(Member my) {
        System.out.println("\tMember ID : " + my.getM_Id());
        System.out.println("\tName: " + my.getM_name());
        System.out.println("\tPersonal-number: " + my.getM_personal_number() + "");
        DisplayMemberBoatInfo(my);
    }

    @Override
    public Member displayEditMember(Member my) {
        System.out.println("-----------Select Member Info to EDIT ! -----------");
        System.out.println("0.) Exit");
        System.out.println("1.) Name");
        System.out.println("2.) Personal Number");
        System.out.println("3.) Boat");
        int in=getChoice(0,3);
        if(in==0){
            System.out.println(" Exit Search !");
            return null;
        }
        else if(in==1){
            editMemberName(my);
        }else if(in==2){
            editMemberPersonalNumber(my);
        }else if(in==3){
            editBoat(my);
        }
        return null;
    }

    @Override
    public void editMemberName(Member editMember){
        System.out.println("Please enter the new first name and last name");
        Scanner newMan = new Scanner(System.in);
        String name;
        do{
          name= newMan.nextLine();
          if(name.equals(""))
              System.out.println("Write Something!!");

        }while(name.equals(""));
        editMember.setM_name(name);
        displaySuccessOperation("*****  CHANGED MEMBER NAME *****");
    }

    @Override
    public void editBoat(Member b) {
        DisplayMemberBoatInfo(b);

        System.out.println("Which Boat do you Want to Edit ?? \nWrite Down Boat ID: ");

        int choice = getChoice(0,b.getM_boats().size() );
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
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Please enter the new personal number in this form YYMMDD-XXXX");
        String personal_number = sc1.nextLine();
        editMember.setM_personal_number(personal_number);
        displaySuccessOperation("***** CHANGED PERSONAL-NUMBER *****");
        sc1.close();
    }

    @Override
    public int selectMember() {
        System.out.println("-----------Select Member -----------");
        System.out.println("\tFind member using:");
        System.out.println("0.) Exit");
        System.out.println("1.) Member id");
        System.out.println("2.) Name");
        System.out.println("3.) Personal Number");
        return getChoice(0,3);
    }

    @Override
    public int getInterestID() {
        System.out.println("********* Input Member id >0 *********");
        try{
           int myId;
           do{
               myId= sc.nextInt();
           }while (myId < 0);
           return myId;
        }catch(InputMismatchException e){
           displayErrorMessage(": SYNTAX ERROR!! Follow input type correctly !!"+ e.getMessage());
        }
           return 0;
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
        String swedId;
        System.out.println("********* Input Member's Personal Number *********");
        do{
            swedId= sc.nextLine();
        }while(swedId.length()>=10 && isCorrect(swedId, getFirstPart(swedId), getSecondPart(swedId)));
        return swedId;
    }

    @Override
    public Member displayDeleteMember(MemberRegistry myList) {
        System.out.println("\tChoose Member to DELETE or DELETE his/her Boats !");
        System.out.println("0.) Exit");
        System.out.println("1.) Id member to delete");
        System.out.println("2.) Id member to delete its boat(s)");

        if(getChoice(0,2)==1){
            return myList.getMemberList().get(getInterestID()-1);
        }else if(getChoice(0,2)==2){
            displayDeleteBoat(myList.getMemberList().get(getInterestID()-1));
        }else
            displayErrorMessage(" Exit Deletion !");
    return null;
    }

    @Override
    public void displayDeleteBoat(Member b) {

        DisplayMemberBoatInfo(b);
        System.out.println("Which Boat do you Want to Delete ?? \nWrite Down Boat ID: ");
        int choice = getChoice(0,b.getM_boats().size() );
        b.getM_boats().remove(choice);
        System.out.println(b.getM_name()+"'s updated Boats list : ");
        DisplayMemberBoatInfo(b);
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
                 /* Check Validation */
                if(isCorrect(swedId,getFirstPart(swedId),getSecondPart(swedId))){
                    System.out.println(swedId+ " Valid Personal Number");
                    nw.setM_personal_number(swedId);
                    end=true;
                } else
                    System.out.println(swedId+"Invalid Personal Number!! Again:");
            }while(!end);
            //Create Boat is nested in Create Member
            System.out.println("How many Boats is Member "+nw.getM_name()+ " registering ?");
            int nm;
            do{
                nm=sc.nextInt();
                if(nm>0)
                    nw.setM_numOfBoats(nm);
                else
                    System.out.println("a positive number! : ");
            }while(nm<0);

            return nw;
        }catch(InputMismatchException e ){
            displayErrorMessage(": SYNTAX ERROR!! Follow input type correctly !!"+ e.getMessage());
            return null;
        }catch(IndexOutOfBoundsException o){
            displayErrorMessage(" SYNTAX ERROR!! Follow Personal number INPUT type correctly !!");
            return null;
        }
    }

    @Override
    public Boat displayAddBoat(Member nw) {
        Boat new_Boat = new Boat(nw) ;
        System.out.println("*****    Add Boat to Member: " + nw.getM_name()+"   *****") ;
        try{
            System.out.println("\tSelect Boat's type:");
            for (int i = 0; i< Boat.Boatstype.values().length; i++ ){
                System.out.println(i + ".) " + Boat.Boatstype.values()[i]);
            }
            int choice = getChoice(0, Boat.Boatstype.values().length-1);
            if(choice!=-99){
                Boat.Boatstype myBoat = Boat.Boatstype.values()[choice];
                new_Boat.setType(myBoat);
            }else
                return new_Boat;

            float len;
            System.out.println("\tSpecify Boat's Length");
            do{
                len = sc.nextFloat();
                    if(len>0)
                        new_Boat.setLength(len);
                    else
                        System.out.println("Input a positive number and be realistic too ! : ");
            } while(len<1);

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
        if(members.size()!=0){
            for (Member m : members){
                System.out.print(" //Owner: "+ m.getM_name());
                DisplayMemberBoatInfo(m);
            }
        }else
            displayErrorMessage("The Yacht Club has no members!");
    }

    @Override
    public void displaySuccessOperation(String str) {
        System.out.println("_______ SUCCESSFULLY "+str+" ______");
    }

    @Override
    public void displayErrorMessage(String str) {
        System.err.println("_______ ERROR! "+str+" ______");
    }

    @Override
    public void endSearch(){
        System.out.println(" _____________________________________________");
        System.out.println("|                                             |");
        System.out.println("| ҉҉҉҉҉҉        End of Search        ҉҉҉҉҉҉   |") ;
        System.out.println("|_____________________________________________|\n");
    }

    @Override
    public void showAuthentication() {
        System.out.println(" _________________________________________________________________");
        System.out.println("|                                                                 |");
        System.out.println("| ҉҉҉҉҉҉       Login and Authentication Operation      ҉҉҉҉҉҉   |") ;
        System.out.println("|_________________________________________________________________|\n");
    }

    @Override
    public String authenticateUsername() {
        System.out.println("********* Input Member Name (admin for now) *********");
        String str="";
        while (str.isEmpty() && sc.hasNext()){
            str = sc.nextLine();
        }
        return str;
    }

    @Override
    public String authenticatePassword() {
        System.out.println("********* Input Member Password (admin for now) *********");
        String str="";
        while (str.isEmpty() && sc.hasNext()){
            str = sc.nextLine();
        }
        return str;
    }

    @Override
    public void showSuccessfulLogin() {
        displaySuccessOperation("   LOGGED IN   ");
    }

    @Override
    public void showInvalidLogin() {
       displayErrorMessage(" Wrong username or password -----");
    }

// will be attempted in grade 4.
    @Override
    public boolean proceed() {
            System.out.println("Press a character to continue or 'q' to quit");
            String inst = sc.nextLine();
            return (Character.compare(inst.charAt(0), 'q') != 0);
    }

    @Override
    public int selectSearch() {
        System.out.println("----------- Search -----------");
        System.out.println("1.) By name prefix");
        System.out.println("2.) By minimum age");
        System.out.println("3.) By birth-month");
        System.out.println("4.) By Boatstype");
        System.out.println("5.) NestedSearch: (month||(name & minimumAge))");
        return getChoice(1,5);
    }

    @Override
    public String getSearchParam(Administrator.ValidationType t) {
        System.out.println("Please enter search-argument:");
        String str="";
        while (str.isEmpty() && sc.hasNext()){
            str = sc.nextLine();
        }
        return str;
    }

    public int selectBoatsType(){
        System.out.println("----------- Select Boatstype -----------");
        for (int i = 0; i< Boat.Boatstype.values().length; i++ ){
            System.out.println("\t" + i + ".) " + Boat.Boatstype.values()[i]);
        }
        return getChoice(0, Boat.Boatstype.values().length-1);

    }

    public int selectMonth(){
        System.out.println("----------- Select Month -----------");
        System.out.println("1.) January");
        System.out.println("2.) February");
        System.out.println("3.) March");
        System.out.println("4.) April");
        System.out.println("5.) May");
        System.out.println("6.) June");
        System.out.println("7.) July");
        System.out.println("8.) August");
        System.out.println("9.) September");
        System.out.println("10.) October");
        System.out.println("11.) November");
        System.out.println("12.) December");
        return getChoice(1,12);
    }

    /************************ Personal Number Helper Methods ***************************/
    private static String getFirstPart(String swedId){
        return swedId.substring(0,6);
    }

    private static String getSecondPart(String swedId){
       return swedId.substring(7);
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
        int checksum = (10-(sum%10))%10;
        return checksum == Character.getNumericValue(theId.charAt(theId.length()-1));
    }
}
