package Controller;

import Model.Boat;
import Model.Member;
import Model.MemberRegistry;
import TechnicalServices.Logging.Authenticate;
import View.DisplayInstructions;

import java.util.InputMismatchException;

/**
 * Created by Void on 28/09/2017 for the YachtClubManagement project.
 */
public class Administrator {

    public enum ValidationType {   // for grade 3 & 4 this is irrelevant at this moment.
        SwedishID,
        PositiveDouble,
        Character,
        Integer,
        String
    }
    private MemberRegistry Registry;
    private DisplayInstructions myConsole;
    private Authenticate auth = new Authenticate();
    public Administrator(DisplayInstructions myView){ //initialize()
        setMyDisplay(myView);
        Registry = TechnicalServices.Persistence.MembersDAO.jaxbXMLToObject();
    }

    public void manipulate(){
        try {
            int i=0;
            if(auth.isLogged() || login())
            do{
                int command = myConsole.userSelection();
                if(command ==0){
                    myConsole.exitDisplay();
                    System.exit(-1); //End of Session
                    TechnicalServices.Persistence.MembersDAO.jaxbObjectToXML(Registry);
                }
                else if(command ==1){//Type of list, handled in the view attempted high cohesion.
                    myConsole.showMemberRegistry(Registry.getMemberList());
                }else if (command ==2){// Show Current Berth allocations.
                    myConsole.displayShowBoats(Registry.getMemberList());
                }else if (command ==3){//Create a new member.
                    createMember();
                }else if(command ==4){//Edit a members/boats information
                    if(Registry.getM_count()!=0)
                        editMember();
                    else
                        myConsole.displayErrorMessage("Registry is Empty");
                } else if(command ==5){
                    if(Registry.getM_count()!=0)
                        deleteMember();
                    else
                        myConsole.displayErrorMessage("Registry is Empty");
                }else{
                    myConsole.displayErrorMessage("Comply with Instructions!");
                    i++;
                    myConsole.exitDisplay();
                }
            }while(i!=1 && myConsole.proceed());
            else{


            }

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
        Member me = myConsole.displayCreateMember();
        if(!Registry.addMember(me) || addBoatsToMember(me)){
            myConsole.displaySuccessOperation("CREATED A NEW MEMBER");
        }
    }

    /*
     *Add the number of boats to the last member in the member registry.
     */
    private boolean addBoatsToMember(Member nw){
        int b=nw.getM_numOfBoats();
        for(int i=0;i<b;i++){
            Boat b1 = (myConsole.displayAddBoat(nw));
            if(isValidBoat(b1)){
                nw.addBoat(b1);
                myConsole.displaySuccessOperation("Added Boat");
            }
            else{
                myConsole.displayErrorMessage("Missing or Incorrect Information RESTARTING PROCESS! ");
                myConsole.displayErrorMessage("This member was not added to the REGISTRY.");
                return false;
            }
        }
        return true;
    }

    private void deleteMember() {
        myConsole.showVerboseList(Registry.getMemberList());  //display registry then display selection option then check validity of member

        Member my=selectMember(Registry);
        if(my==null ||!isValidMember(my))
            myConsole.displayErrorMessage(" MEMBER NOT FOUND !");
        else{
            myConsole.showMemberInformation(my);
            Member del = myConsole.displayDeleteMember(Registry);
            Registry.deleteMember(del.getM_Id());// then show member info and delete him after confirmation!!!
            myConsole.displaySuccessOperation("Deleted Member");
        }
    }

    private void editMember() {// a much better separation of MV has been achieved in this iteration.
        myConsole.showVerboseList(Registry.getMemberList());
        Member my = selectMember(Registry);
        if(my==null || !isValidMember(my))
            myConsole.displayErrorMessage(" MEMBER NOT FOUND!");
        else{
             myConsole.showMemberInformation(my);
             myConsole.displayEditMember(my);
             myConsole.displaySuccessOperation("Edited Member");
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


        } else if(choice==3) {
            String pnum = myConsole.getInterestNr();
            if(!pnum.equals("")){
               // pnum = pnum.replace("-", "");
                m = myList.socialMember(pnum);
            }else {
                m=null;
            }
        }
        return m;
    }

    private boolean login(){
        myConsole.showAuthentication();
        String username = myConsole.authenticateUsername();
        String password = myConsole.authenticatePassword();
        if (auth.authenticate(username, password)) myConsole.showSuccessfulLogin();
        else myConsole.showInvalidLogin();
        return auth.isLogged();
    }


    public static boolean isValidMember(Member man) {
        if(man==null)
            return false;
        else
            return man.getM_name() != null && man.getM_personal_number() != null && man.getM_boats()!= null && man.getM_numOfBoats() != 0;
    }

    public static boolean isValidBoat(Boat machine){
        if(machine==null)
            return false;
        else
            return machine.getB_Name() != null && machine.getLength() != 0 && machine.getType() !=null;
    }
}
