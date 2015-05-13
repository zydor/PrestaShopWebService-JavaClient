/*
 * www.zydor.pl
 *
 *
 *
 */
package pswebservice.examples;

import java.io.IOException;
import java.util.HashMap;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.CDATASection;
import org.w3c.dom.CharacterData;
import org.w3c.dom.NodeList;
import pswebservice.PSWebServiceClient;
import pswebservice.PrestaShopWebserviceException;


/**
 *
 * @author zydor
 */
public class PSWebServiceExample {

    /**
     * @param args the command line arguments
     * @throws pswebservice.PrestaShopWebserviceException
     * @throws javax.xml.transform.TransformerConfigurationException
     */  
    public static void main(String[] args) throws PrestaShopWebserviceException, TransformerConfigurationException, TransformerException, IOException {
        
        PSWebServiceWrapper ws = new PSWebServiceWrapper();
        ws.addProductExample();
    }
    
}


class PSWebServiceWrapper {

    private final String shopUrl = "http://www.example.com";
    private final String key = "HXR3JDWFIYQWSDLWQQJFEQ1Z87UWXKXYZM5JDRE8BWDS";
    private final boolean debug = false;
    private PSWebServiceClient ws;
    
    public PSWebServiceWrapper(){
        this.ws = new PSWebServiceClient(shopUrl,key,debug);
    }
    
    public void addProductExample() throws PrestaShopWebserviceException, TransformerException, IOException{
        
        HashMap<String,Object> getSchemaOpt = new HashMap();
        getSchemaOpt.put("url",shopUrl+"/api/products?schema=blank");       
        Document schema = ws.get(getSchemaOpt);     
        
        schema.getElementsByTagName("id_category_default").item(0).setTextContent("2");  
        schema.getElementsByTagName("price").item(0).setTextContent("100"); 
        schema.getElementsByTagName("active").item(0).setTextContent("1"); 
        schema.getElementsByTagName("available_for_order").item(0).setTextContent("1"); 
        schema.getElementsByTagName("show_price").item(0).setTextContent("1"); 
        schema.getElementsByTagName("indexed").item(0).setTextContent("1");
       
        Element category = schema.createElement("category");
        Element catId = schema.createElement("id");
        catId.setTextContent("2");
        category.appendChild(catId);
        schema.getElementsByTagName("categories").item(0).appendChild(category);
        
        Element name = (Element) schema.getElementsByTagName("name").item(0).getFirstChild();
        name.appendChild(schema.createCDATASection("name"));
        name.setAttribute("id", "1");
        name.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+1);
               
        Element description = (Element) schema.getElementsByTagName("description").item(0).getFirstChild();
        description.appendChild(schema.createCDATASection("name"));
        description.setAttribute("id", "1");
        description.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+1);        
        
        Element description_short = (Element) schema.getElementsByTagName("description_short").item(0).getFirstChild();
        description_short.appendChild(schema.createCDATASection("name"));
        description_short.setAttribute("id", "1");
        description_short.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+1);  
        
        Element link_rewrite = (Element) schema.getElementsByTagName("link_rewrite").item(0).getFirstChild();
        link_rewrite.appendChild(schema.createCDATASection("name"));
        link_rewrite.setAttribute("id", "1");
        link_rewrite.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+1);    
        
        Element meta_title = (Element) schema.getElementsByTagName("meta_title").item(0).getFirstChild();
        meta_title.appendChild(schema.createCDATASection("name"));
        meta_title.setAttribute("id", "1");
        meta_title.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+1);    
        
        Element meta_description = (Element) schema.getElementsByTagName("meta_description").item(0).getFirstChild();
        meta_description.appendChild(schema.createCDATASection("name"));
        meta_description.setAttribute("id", "1");
        meta_description.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+1);   
        
        Element meta_keywords = (Element) schema.getElementsByTagName("meta_keywords").item(0).getFirstChild();
        meta_keywords.appendChild(schema.createCDATASection("name"));
        meta_keywords.setAttribute("id", "1");
        meta_keywords.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+1);    
        
        Element available_now = (Element) schema.getElementsByTagName("available_now").item(0).getFirstChild();
        available_now.appendChild(schema.createCDATASection("name"));
        available_now.setAttribute("id", "1");
        available_now.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+1);   
        
        Element available_later = (Element) schema.getElementsByTagName("available_later").item(0).getFirstChild();
        available_later.appendChild(schema.createCDATASection("available later"));
        available_later.setAttribute("id", "1");
        available_later.setAttribute("xlink:href", this.shopUrl+"/api/languages/"+1);           
              
        System.out.println(ws.DocumentToString(schema));
        
	HashMap<String,Object> productOpt = new HashMap();
        productOpt.put("resource", "products");
        productOpt.put("postXml", ws.DocumentToString(schema));
        Document product = ws.add(productOpt);     
        
        System.out.println(ws.DocumentToString(product));
        
        Document productImg = ws.addImg("http://example/exampleImg.jpg", Integer.valueOf(getCharacterDataFromElement((Element) product.getElementsByTagName("id").item(0))));

        System.out.println(ws.DocumentToString(productImg));
        
    }
    
    public static String getCharacterDataFromElement(Element e) {

        NodeList list = e.getChildNodes();
        String data;

        for(int index = 0; index < list.getLength(); index++){
            if(list.item(index) instanceof CharacterData){
                CharacterData child = (CharacterData) list.item(index);
                data = child.getData();

                if(data != null && data.trim().length() > 0)
                    return child.getData();
            }
        }
        return "";
    }
}
