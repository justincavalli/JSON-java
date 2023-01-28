import org.json.*;

import java.io.FileReader;
import java.io.IOException;

public class Test {
    public static void main(String[] args){
//       JSONObject jo = new JSONObject("{ \"abc\" : \"def\" }");
//       System.out.println(jo.toString());
        try {
            // change path of file
            FileReader reader = new FileReader("C:/Users/JustinCavalli/Documents/MSWE - UCI/Winter Q2/Programming Styles/JSON-java/src/main/java/Milestone2/data.xml");
            JSONPointer path = new JSONPointer("/page/revision");
            JSONObject subObject = new JSONObject();
            subObject.put("name", "Justin");
            subObject.put("hometown", "Alameda");
            subObject.put("grade", "A+");
            System.out.println(XML.toJSONObject(reader, path, subObject));
//            XML.toJSONObject(reader, path, subObject);
//            System.out.println(conversion.toString());
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}