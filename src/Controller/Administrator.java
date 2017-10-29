package Controller;

import Model.Boat;
import Model.Member;
import Model.MemberRegistry;
import Model.Authenticate;
import Model.Search.*;
import View.DisplayInstructions;

import java.util.InputMismatchException;

public class Administrator {

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
            do{
                int command = myConsole.userSelection();
                if(command ==0){
                    myConsole.exitDisplay();
                }
                else if(command ==1){//Type of list, handled in the view attempted high cohesion.
                    myConsole.showMemberRegistry(Registry.getMemberList());
                }else if (command ==2){// Show Current Berth allocations.
                    myConsole.displayShowBoats(Registry.getMemberList());
                }else if (command ==3){//Create a new member.
                    if (auth.isLogged() || login()){
                        createMember();
                    }
                }else if(command ==4){//Edit a members/boats information
                    if (auth.isLogged() || login()){
                        if(Registry.getM_count()!=0)
                            editMember();
                        else
                            myConsole.displayErrorMessage("Registry is Empty");
                    }
                } else if(command ==5){
                    if (auth.isLogged() || login()){
                        if(Registry.getM_count()!=0)
                            deleteMember();
                        else
                            myConsole.displayErrorMessage("Registry is Empty");
                    }
                } else if(command ==6){
                    MemberRegistry search_result = searchMembers();
                    myConsole.showMemberRegistry(Registry.getMemberList());
                    if (search_result.getMemberList().iterator().hasNext()){
                        Member id = selectMember(Registry);
                        myConsole.showMemberInformation(id);
                    }
                }else{
                    myConsole.displayErrorMessage("Comply with Instructions!");
                    i++;
                    myConsole.exitDisplay();
                }
            }while(i!=1 || myConsole.proceed());
        }catch (InputMismatchException e){
            myConsole.displayErrorMessage("Input Mismatch! System Exit");
            myConsole.exitDisplay();
        }
        myConsole.displaySuccessOperation("Saved Session!");
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
            if(isValidBoat(b1)&& nw.addBoat(b1)){
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
            if(Registry.deleteMember(del.getM_Id()))// then show member info and delete him after confirmation!!!
                myConsole.displaySuccessOperation("Deleted Member");
            else
                myConsole.displayErrorMessage("MEMBER NOT FOUND!");
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

    private MemberRegistry searchMembers(){
        int i = myConsole.selectSearch();
        MemberRegistry search_list = new MemberRegistry();

        switch (i){
            case 1: {
                SearchCriteria byName = new NamePrefixCriteria(myConsole.getInterestName());
                search_list = byName.meetCriteria(Registry);
            }
            break;
            case 2: {
                SearchCriteria byAge = new MinimumAgeCriteria(myConsole.getInteresAge());
                search_list = byAge.meetCriteria(Registry);
            }
            break;
            case 3:	{
                SearchCriteria byBirthMonth = new BirthMonthCriteria(myConsole.selectMonth());
                search_list = byBirthMonth.meetCriteria(Registry);
                break;
            }
            case 4: {
                SearchCriteria byBoatsType = new BoatsTypeCriteria(Boat.Boatstype.values()[myConsole.selectBoatsType()]);
                search_list = byBoatsType.meetCriteria(Registry);
                break;
            }
            case 5: {     	// (month||(name & minimumAge)
                SearchCriteria byBirthMonth = new BirthMonthCriteria(myConsole.selectMonth());
                SearchCriteria byName = new NamePrefixCriteria(myConsole.getInterestName());
                SearchCriteria byAge = new MinimumAgeCriteria(Integer.parseInt(myConsole.getInteresAge()));
                SearchCriteria byNameAndAge = new AndCriteria(byName,byAge);
                SearchCriteria byBirthMonthOrNestedNameAndAge = new OrCriteria(byBirthMonth, byNameAndAge);
                search_list = byBirthMonthOrNestedNameAndAge.meetCriteria(Registry);
            }
        }
        return search_list;
    }


    public static boolean isValidMember(Member man) {
        return man != null && man.getM_name() != null && man.getM_personal_number() != null  && man.getM_numOfBoats() != 0;
    }

    public static boolean isValidBoat(Boat machine) {
        return machine != null && machine.getB_Name() != null && machine.getLength() != 0 && machine.getType() != null;
    }
}
