package Model;

import java.util.ArrayList;

import static Controller.Administrator.isValidBoat;


/**
 * Created by Void on 28/09/2017 for the YachtClubManagement project.
 */
public class Member {

    private String m_name;
    private String m_personal_number;
    private int m_Id;
    private ArrayList<Boat> boats = new ArrayList<Boat>();
    private int m_numOfBoats=0;
    public Member(){
    }
    public int getM_numOfBoats() {
        return m_numOfBoats;
    }
    public void setM_numOfBoats(int m_numOfBoats) {
        this.m_numOfBoats = m_numOfBoats;
    }

    public String getM_name() {
        return m_name;
    }
    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_personal_number() {
        return m_personal_number;
    }
    public void setM_personal_number(String m_personal_number) { this.m_personal_number = m_personal_number; }

    public int getM_Id() {
        return m_Id;
    }
    public void setM_Id(int m_Id) {
        this.m_Id = m_Id;
    }

    public ArrayList<Boat> getM_boats(){
        return boats;
    }
    public Boat getBoat(int b_id){
        for (int i=0;i<boats.size();i++){
            if (boats.get(i).getB_id() == b_id)
                return boats.get(i);
        }
        return null;
    }

    public boolean addBoat(Boat myBoat){
        if(isValidBoat(myBoat)){
            myBoat.setB_id(boats.size());
            boats.add(myBoat);
            return true;
        }else
        return false;
    }

}
