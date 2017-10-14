package TechnicalServices.Validation;

import Model.Boat;
import Model.Member;

/**
 * Created by Void on 12/10/2017 for the YachtClubManagement project.
 */
public class Validate {
    public enum ValidationType {
        SwedishID,
        PositiveDouble,
        Character,
        Integer,
        String
    }

    public static boolean isValidMember(Member man) {
        return man.getM_name() != null || man.getM_personal_number() != null || man.getM_boats()!= null || man.getM_numOfBoats() != 0;
    }

    public static boolean isValidBoat(Boat machine){
        return machine.getB_Name() != null || machine.getLength() != 0 || machine.getType() !=null;
    }
}
