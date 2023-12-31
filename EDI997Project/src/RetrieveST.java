import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//STEP 3 - TRANSACTION NUMBER - RETRIEVE NEXT 997 TRANSACTION NUMBER (STnumber) & UPDATE DATABASE WITH THE LAST 997 TRANSACTION NUMBER
public class RetrieveST {

	//CREATE A CONNECTION WITH SQL SERVER
	String connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS;user=adventureuser;password=adventureuser;encrypt=false;trustSErverCertificate=false;loginTimeout=30";
	
	//INITIALIZE & DEFINE THESE VARIABLES
	Connection STconnection = null; // step 3.5
	ResultSet STresultSet = null; // step 3.5
	Statement STstatement = null; // step 3.5
	Statement STupdateStatement = null; // step 3.5
	
	private int STnumber = 0; // step 3.5
	
	public RetrieveST() {
	
		try {
			STconnection = DriverManager.getConnection(connectionUrl); // step 3.5
			STstatement = STconnection.createStatement(); // connect to database
			STupdateStatement = STconnection.createStatement(); // connect to database
			STresultSet = STstatement.executeQuery("Select transno FROM EDItransNos where segmenttype='ST'"); // retrieve 997 transaction number
			if(STresultSet.next()) 
			{
				this.STnumber = STresultSet.getInt(1)+1; // add 1 to transaction number
				STupdateStatement.executeUpdate("Update EDItransNos set transno = " + STnumber + " where segmenttype = 'ST'"); // update transaction number to our new value
				//System.out.println(STnumber);	
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally 
		{
			try { STresultSet.close(); } catch (Exception e) { /* Ignored */ }
			try { STstatement.close(); } catch (Exception e) { /* Ignored */ }
			try { STupdateStatement.close(); } catch (Exception e) { /* Ignored */ }
			try { STconnection.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	//Create getter to return current instances
	public int getSTnumber() { return STnumber; }
}