package TechnicalServices.Persistence;

import Model.Berths;
import Model.MemberRegistry;
import com.sun.jndi.ldap.Ber;

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
    private static final String File = "../resources/members.xml";				// as .jar

    public static Berths jaxbXMLToObject() {
        try {
            JAXBContext context = JAXBContext.newInstance(Berths.class);
            Unmarshaller un = context.createUnmarshaller();
            Berths md_list = (Berths) un.unmarshal(new File(File));
            return md_list;
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
            m.marshal(md_list, new File(File));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
