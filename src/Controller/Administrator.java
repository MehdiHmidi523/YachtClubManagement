package Controller;

import Model.Boat;
import Model.Member;
import Model.MemberRegistry;
import View.DisplayInstructions;

import java.util.InputMismatchException;

import static TechnicalServices.Validation.Validate.isValidBoat;
import static TechnicalServices.Validation.Validate.isValidMember;

/**
 * Created by Void on 28/09/2017 for the YachtClubManagement project.
 */
public class Administrator {

    private MemberRegistry Registry;
    private DisplayInstructions myConsole;

    public Administrator(DisplayInstructions myView){ //initialize()
        System.out.println(" Hello! This is  Secretary Administrator mode");
        setMyDisplay(myView);
        Registry = TechnicalServices.Persistence.MembersDAO.jaxbXMLToObject();
    }

    public void manipulate(){
        try {
            int i=0;
            do {
                int command = myConsole.userSelection();
                if(command ==0){
                    myConsole.exitDisplay();
                    System.exit(-1); //End of Session
                }

                else if(command ==1){//Type of list, handled in the view attempted high cohesion.
                    myConsole.showMemberRegistry(Registry.getMemberList());

                }else if (command ==2){// Show Current Berth allocations.
                    myConsole.displayShowBoats(Registry.getMemberList());

                }else if (command ==3){//Create a new member.
                    createMember();
                    addBoatsToMember();

                }else if(command ==4){//Edit a members/boats information
                    if(Registry.getM_count()!=0){
                            editMember();
                    }else
                        myConsole.displayErrorMessage("Registry is Empty");

                } else if(command ==5){
                    if(Registry.getM_count()!=0){
                      deleteMember();
                    }else
                        myConsole.displayErrorMessage("Registry is Empty");

                }else{
                    myConsole.displayErrorMessage("Comply with Instructions!");
                    i++;
                    myConsole.exitDisplay();
                }
            }while(i!=1);
        }catch (InputMismatchException e){
            myConsole.displayErrorMessage("Input Mismatch! System Exit");
            myConsole.exitDisplay();
        }
        TechnicalServices.Persistence.MembersDAO.jaxbObjectToXML(Registry);
    }

    /*
     * Creates an Instance of a member and gets its Data from the user.
     * Checks Member information is Valid then adds Member to MemberRegistry.
     */
    private void createMember(){
        if(!Registry.addMember(myConsole.displayCreateMember())){
            myConsole.displaySuccessOperation("CREATED A NEW MEMBER");
        }
    }

    /*
     *Add the number of boats to the last member in the member registry.
     */
    private void addBoatsToMember(){
        Member nw = Registry.getMemberList().get(Registry.getM_count()-1);
        int b=nw.getM_numOfBoats();
        for(int i=0;i<b;i++){
            Boat b1 = (myConsole.displayAddBoat(nw));
            if(isValidBoat(b1)){
                nw.addBoat(b1);
                myConsole.displaySuccessOperation("Added Boat");
            }
            else{
                myConsole.displayErrorMessage("Missing or Incorrect Information RESTARTING PROCESS! ");
            }
        }
    }

    private void deleteMember() {
        myConsole.showVerboseList(Registry.getMemberList());  //display registry then display selection option then check validity of member

        Member my=selectMember(Registry);
        if(!isValidMember(my))
            myConsole.displayErrorMessage(" MEMBER NOT FOUND !");
        else{
            myConsole.showMemberInformation(my);
            Member del = myConsole.displayDeleteMember(Registry);
            Registry.deleteMember(del.getM_Id());// then show member info and delete him after confirmation!!!
            myConsole.displaySuccessOperation("Deleted Member");
        }
    }

    private void editMember() {//BREACH OF GRASP SEPARATION  of responsibilities
        myConsole.showVerboseList(Registry.getMemberList());
        Member my = selectMember(Registry);
        if(!isValidMember(my))
            myConsole.displayErrorMessage(" MEMBER NOT FOUND!");
        else{
             myConsole.showMemberInformation(my);
             myConsole.displayEditMember(my);

        }
    }

    private void setMyDisplay(DisplayInstructions myConsole) {
        this.myConsole = myConsole;
    }

    private Member selectMember(MemberRegistry myList){
        int choice= myConsole.selectMember();  // get user input.
        Member m = new Member();
        if(choice==0)
            myConsole.endSearch();      // display end of the selection operation
        else if(choice==1){
            int s= myConsole.getInterestID();
            if(s<0 || s > myList.getM_count()){
                myConsole.displayErrorMessage("Incorrect ID routing to MENU!");
            }else
                m = myList.getMemberList().get(s-1); // id is in the registry interval!

        } else if (choice==2){
            String str= myConsole.getInterestName();
            m = myList.nameMember(str);


        } else if(choice==3)
            m = myList.socialMember( myConsole.getInterestNr());
        else return null;
        return m;
    }


}
