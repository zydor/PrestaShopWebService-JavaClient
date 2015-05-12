/*
 * www.zydor.pl
 *
 *
 *
 */
package pswebservice.examples;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import pswebservice.PSWebServiceClient;
import pswebservice.PrestaShopWebserviceException;


/**
 *
 * @author zydor
 */
public class PSWebService {

    /**
     * @param args the command line arguments
     * @throws pswebservice.PrestaShopWebserviceException
     * @throws javax.xml.transform.TransformerConfigurationException
     */
    public static void main(String[] args) throws PrestaShopWebserviceException, TransformerConfigurationException, TransformerException {
        
        PSWebServiceClient ws = new PSWebServiceClient("http://www.spiceupyournight.com","PVRT4VUWLTERJZC4S53G4R9NUU3KNGSB",false);
        HashMap<String,Object> opt = new HashMap();
        opt.put("resource","orders");
        opt.put("id",1);        
        Document doc = ws.get(opt);  
        System.out.println(ws.DocumentToString(doc));
    }
    
}
