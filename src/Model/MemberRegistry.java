package Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

import static TechnicalServices.Validation.Validate.isValidMember;

/**
 * Created by Void on 28/09/2017 for the YachtClubManagement project.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MemberRegistry {

    @XmlElement(name="member")
    private ArrayList<Member> memberList=new ArrayList<Member>();

    private int count=memberList.size();

    public MemberRegistry(){}
    public MemberRegistry(ArrayList persistentModel){
        memberList = persistentModel;
    }

    public ArrayList<Member> getMemberList(){
        return memberList;
    }

    public boolean addMember(Member person){
        if(isValidMember(person)){
            person.setM_Id(memberList.size()+1);
            memberList.add(person);
            TechnicalServices.Persistence.MembersDAO.jaxbObjectToXML(this);
            return true;
        }else
            return false;
    }

    public void deleteMember(int member_id){
        if (!memberList.remove(memberList.get((member_id-1))))
            System.err.println("Member not found!");
    }

    public int getM_count() {
        return memberList.size();
    }

    public Member nameMember(String interestName) {
        for (Member m : memberList) {
            if (m.getM_name().equals(interestName)){
                return m;
            }
        }
        return null;
    }

    public Member socialMember(String interestNr) {
        for (Member m : memberList) {
            if (m.getM_personal_number().equals(interestNr)){
                return m;
            }
        }
        return null;
    }
}