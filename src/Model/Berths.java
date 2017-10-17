package Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by Void on 16/10/2017 for the YachtClubManagement-master project.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Berths {

    @XmlElement(name="Boat")
    ArrayList<Boat> boatList = new ArrayList<>();

    public Berths(MemberRegistry registry){
        populate(registry);
    }

    private void populate(MemberRegistry registry) {
        Member created;
        for(int i =0; i<registry.getM_count(); i++){
            created=registry.getMemberList().get(i);
            for(int j=0;j<created.getM_boats().size();j++)
            boatList.add(created.getM_boats().get(j));
        }

    }




}
