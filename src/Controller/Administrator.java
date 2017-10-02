package Controller;

import Model.Member;
import Model.MemberRegistry;
import View.DisplayInstructions;

/**
 * Created by Mehdi on 28/09/2017 for the YachtClubManagement project.
 */
public class Administrator {

    private MemberRegistry Registry = new MemberRegistry();
    private DisplayInstructions myConsole;
    private int command;

    public Administrator(DisplayInstructions myView){ //initialize()
        System.out.println(" Hello! This is  Secretary Administrator mode");
        setMyDisplay(myView);
        //Registry((ArrayList) dao.MembersDAO.jaxbXMLToObject());  //TODO: to implement later.

    }
    public void manipulate(){
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
                    Member my = selectMember(Registry);
                    myConsole.showMemberInformation(my);
                    editMember(my);
            }

            else{
               /* showList(md_list);
                if (md_list.getMemberList().iterator().hasNext()){
                    int m_id = selectMember(md_list);
                    showMember(m_id);
                    deleteMember(m_id);
                }*/
                // call the registry and tell it to check if this member is present using the Id provided from the console.
                // check the boolean value from the registry if done is true then success display or error display
                // TODO: check if i have to update ID of all members or not ? no such scenario is not based on reality EXPAND HERE !!!!
                //Delete a Member
            }
        }
    }

    private void editMember(Member my) {
        myConsole.displayEditMember();
    }

    public void setMyDisplay(DisplayInstructions myConsole) {
        this.myConsole = myConsole;
    }

    private Member selectMember(MemberRegistry myList){
        int choice= myConsole.selectMember();
        Member m;
        if(choice==1){
            m = myList.idMember(myConsole.getInterestID());
            return m==null? m:selectMember(myList);         // stack overflow and number of tries will not be an issue for the current iteration.
        }
        else if (choice==2){
            m = myList.nameMember(myConsole.getInterestName());
            return m==null? m:selectMember(myList);
        }
        else{
            m = myList.socialMember( myConsole.getInterestNr());
            return m==null? m:selectMember(myList);
        }
    }

}
