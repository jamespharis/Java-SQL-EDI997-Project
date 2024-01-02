import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;

// STEP 5 - FILE CREATION
public class EDIcreator {

	private String dest = null;
	private String EDIcontent = null;
	private int STnumber = 0;
	static private Writer pr;
	
	public EDIcreator(){} //CONSTRUCTOR
	
	//Create getter to return current instances
	public String getEDIcontent() { return EDIcontent; }
	public String getFilePath() { return dest;}
	public int getSTnumber() { return STnumber;}
	
	//Create setters that will be called & assigned variables in SQL_Pathway
	public void setEDIcontent(String EDIcontent2) {this.EDIcontent = EDIcontent2;}
	public void setDest(String dest2) {this.dest = dest2;}
	public void setSTnumber(int STnum) {this.STnumber = STnum;}

		

public void Creation() {	
	System.out.println(dest);
	System.out.println(EDIcontent);
	System.out.println("");
	
	//CREATE FILE
	String EDIfile = dest + "\\" + STnumber + ".txt";
	File eFile = new File(EDIfile);	
			if (eFile.exists()) //------- If the File Already Exists, Delete
			{ eFile.delete();
				try { eFile.createNewFile(); }
				catch (Exception e) { System.out.println("Error Attempting to Create File("+EDIfile+")"); }
			}
			else // Create the File
			{	
				try { eFile.createNewFile(); }
				catch (Exception e) { System.out.println("Error Attempting to Create File("+EDIfile+")"); }  
			}
	
	//CREATE WRITER 
	try {				
		pr=new PrintWriter(new BufferedWriter(new FileWriter(EDIfile, true)));               
		pr.write(EDIcontent);					
		pr.close(); 
		pr = null;  		         
	} 
	catch (java.io.IOException ex) { ex.printStackTrace(); } 			
}	
}

