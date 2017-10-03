package Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by Void on 28/09/2017 for the YachtClubManagement project.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MemberRegistry {

    @XmlElement(name="member")
    ArrayList<Member> memberList=new ArrayList<Member>();;

    private int count=memberList.size();

    public MemberRegistry(){}
    public MemberRegistry(ArrayList persistentModel){
        memberList = persistentModel;
    }

    public ArrayList<Member> getMemberList(){
        return memberList;
    }

    public void addMember(Member person){
            person.setM_Id(count+1);
            memberList.add(person);
    }
    public void deleteMember(int member_id){
        if (!memberList.remove(idMember(member_id)))
            System.err.println("Member not found!");
    }
    public int getM_count() {
        return count;
    }

    public Member idMember(int id) {

        for (Member m : memberList) {
            if (m.getM_Id() == id){
                return m;
            }
        }
        return null;
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