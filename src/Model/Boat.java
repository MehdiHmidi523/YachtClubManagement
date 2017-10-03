package Model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@XmlAccessorType(XmlAccessType.FIELD)
public class Boat {
    public enum Boatstype{
        SAILBOAT,
        MOTORBOAT,
        KAYAK,
        CANOE,
        OTHER
    }
    private int b_id;
    private String b_Name;
    private Boatstype b_type;
    private double length;

    public Boatstype getType() {
        return b_type;
    }
    public void setType(Boatstype type) {
        this.b_type = type;
    }
    public double getLength() { return length; }
    public void setLength(double length) {
        this.length = length;
    }
    public int getB_id() {
        return b_id;
    }
    public void setB_id(int id) {
        this.b_id = id;
    }
    public String getB_Name() {
        return b_Name;
    }
    public void setB_Name(String boatName) {
        this.b_Name = boatName;
    }
}
