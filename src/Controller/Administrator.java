package Controller;

import Model.Member;
import Model.MemberRegistry;
import View.DisplayInstructions;

import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Created by Void on 28/09/2017 for the YachtClubManagement project.
 */
public class Administrator {

    private MemberRegistry Registry = new MemberRegistry();
    private DisplayInstructions myConsole;
    private int command;

    public Administrator(DisplayInstructions myView){ //initialize()
        System.out.println(" Hello! This is  Secretary Administrator mode");
        setMyDisplay(myView);
        Registry= dao.MembersDAO.jaxbXMLToObject();  //TODO: to implement correctly later.

    }

    public void manipulate(){
       try{
           while(true){
               command = myConsole.userSelection();
               if(command==0){
                   myConsole.exitDisplay();
                   System.exit(-1); //End of Session
               }
               else if(command==1){
                   myConsole.showMemberRegistry(Registry.getMemberList()); //Type of list, handled in the view attempted high cohesion.
               }else if (command==2){
                   myConsole.displayShowBoats(Registry.getMemberList()); // Show Current Berth allocations.
               }else if (command==3){
                   Registry.addMember(myConsole.displayCreateMember());    //Create a new member.
                   Member nw = Registry.getMemberList().get(Registry.getM_count());
                   for(int i=0;i<nw.getM_numOfBoats();i++){
                       nw.addBoat(myConsole.displayAddBoat(nw));
                       myConsole.displaySuccessOperation("Added Boat");
                   }
                   myConsole.displaySuccessOperation("CREATED A NEW MEMBER");
               }else if(command==4){
                   Member my=selectMember(Registry);
                   if(my==null)
                       myConsole.displayErrorMessage(" MEMBER NOT FOUND !");
                   else{
                       myConsole.showMemberInformation(my);
                       editMember(my);
                   }

               } else{
                   System.out.println("Choose Member to delete or its Boats !");
                   Member my=selectMember(Registry);
                   if(my==null)
                       myConsole.displayErrorMessage(" MEMBER NOT FOUND !");
                   else{
                       myConsole.showMemberInformation(my);
                       deleteMember();
                   }
               }
               dao.MembersDAO.jaxbObjectToXML(Registry);
           }
       }catch (InputMismatchException e){
           System.err.println("Input Mismatch Exception  CHECK YOURSELF !");
       }

    }

    private void deleteMember() {
        myConsole.displayDeleteMember(Registry);
    }

    private Member editMember(Member my) {
        myConsole.displayEditMember(my);
        return my;
    }

    public void setMyDisplay(DisplayInstructions myConsole) {
        this.myConsole = myConsole;
    }

    private Member selectMember(MemberRegistry myList){
        int choice= myConsole.selectMember();
        Member m = new Member();
        if(choice==0){
            System.out.println(" _____________________________________________");
            System.out.println("| ҉҉҉҉҉҉        End of Search        ҉҉҉҉҉҉   |") ;
            System.out.println("|_____________________________________________|\n");
            manipulate();
        }
        else if(choice==1){
            m = myList.idMember(myConsole.getInterestID());

        }
        else if (choice==2){
            m = myList.nameMember(myConsole.getInterestName());

        }
        else if(choice==3){
            m = myList.socialMember( myConsole.getInterestNr());

        }else return null;
        return m;
    }


}
