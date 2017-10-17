package TechnicalServices.Persistence;

import Model.Berths;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Void on 16/10/2017 for the YachtClubManagement-master project.
 */
public class BoatsDAO {

    //private static final String File = "./resources/members.xml";			// in eclipse
    private static final String File1 = "/resources/berths.xml";				// as .jar

    public static Berths jaxbXMLToObject() {
        try {
            JAXBContext context = JAXBContext.newInstance(Berths.class);
            Unmarshaller un = context.createUnmarshaller();
            return (Berths) un.unmarshal(new File(File1));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void jaxbObjectToXML(Berths md_list) {

        try {
            JAXBContext context = JAXBContext.newInstance(Berths.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(md_list, new File(File1));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
