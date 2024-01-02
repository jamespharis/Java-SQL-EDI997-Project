import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//STEP 1 - READ JSON FILE TO FIND OUTPUT LOCATION
public class JSONhelper {

	private String dest;
	@SuppressWarnings("unchecked")
	
	//Constructor
	public JSONhelper() 
	{
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		
		try (FileReader reader = new FileReader("EDI997config.json"))
		{
            Object obj = jsonParser.parse(reader); // Create object that will read JSON file

            JSONObject configList = (JSONObject) obj; // create new JSON Object "configList"
            //System.out.println(configList); // print entire json file
            
            //Retrieve values from config.json
            this.dest = (String) configList.get("Outputfolder");
            //System.out.println(getDest());
		}
		catch (FileNotFoundException e) { e.printStackTrace();} 
		catch (IOException e) { e.printStackTrace(); } 
		catch (ParseException e) { e.printStackTrace(); }
		}
	
	//Create getter to return current instance
	public String getDest() { return dest; }	
}
