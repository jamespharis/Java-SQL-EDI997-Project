import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL_Pathway {

	public static void main(String[] args) {
		
		// STEP 1 - READ JSON FILE TO FIND OUTPUT LOCATION
		JSONhelper configObject = new JSONhelper(); //create configObject that will call JSONhelper
		String Dest = configObject.getDest(); // get 'dest' from our getter in JSONhelper
		//System.out.println(Dest);
		
		//CREATE A CONNECTION WITH SQL SERVER
		String connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS;user=adventureuser;password=adventureuser;encrypt=false;trustSErverCertificate=false;loginTimeout=30";

		//INITIALIZE & DEFINE THESE VARIABLES
		ResultSet resultSet = null; // step 2
		Connection connection = null; // step 2
		Statement statement = null; // step 2
		Statement updateStatement = null; // step 6
		
		String EDIcontent = ""; // step 4
		int keyrrn = 0; // step 6
		
		EDIcreator mover = new EDIcreator(); // step 5
		
		
		
		// ATTEMPT CONNECTION WITH SQL SERVER (Step 2)
		try 
		{
			connection = DriverManager.getConnection(connectionUrl); // step 2
			statement = connection.createStatement(); // step 2
			updateStatement = connection.createStatement(); // step 6
			
			
			// Create & Execute a SELECT SQL statement.;
			String selectSql ="Select key204rrn, GS01, GS06, ST01, ST02, E997Sent from EDIHeader where E997Sent <> 'Y'";
			resultSet = statement.executeQuery(selectSql);
		
			// Print results from select statement
			while(resultSet.next()) {
			
				// Print our values for key204rrn, GS01, GS06, ST01, ST02, & E997Sent
				System.out.println(resultSet.getInt(1) + " - " + resultSet.getString(2) + " " + resultSet.getString(3) 
				+ " " + resultSet.getString(4)  + " " + resultSet.getString(5) + " " + resultSet.getString(6));
				keyrrn = resultSet.getInt("key204rrn"); // can access by column name or number
				
				// STEP 3 - TRANSACTION NUMBER - RETRIEVE NEXT 997 TRANSACTION NUMBER (STnumber) & UPDATE DATABASE
				RetrieveST STconfigObject = new RetrieveST(); //create STconfigObject that will call RetrieveST
				int STnumber = STconfigObject.getSTnumber(); //Get STnumber variable from RetrieveST using STconfigObject
				
				
				// STEP 4 - COMPILATION
				EDIcontent = "ST" + "*" + "997" + "*" + STnumber + "~"; // resultSet.getString(3)
				EDIcontent = EDIcontent + "AK1" + "*" + resultSet.getString(2) + "*" + resultSet.getString(3) + "~";
				EDIcontent = EDIcontent + "AK2" + "*" + resultSet.getString(4) + "*" + resultSet.getString(5) + "~";
				EDIcontent = EDIcontent + "AK5*A" + "~" + "AK9*A*1*1*1" + "~";
				EDIcontent = EDIcontent + "SE" + "*" + "6" + "*" + STnumber;
				//System.out.println(EDIcontent);

				//STEP 5 - FILE CREATION
				//Create setters to send current instances to another class
				mover.setDest(Dest);
				mover.setEDIcontent(EDIcontent);
				mover.setSTnumber(STnumber);
				mover.Creation();

								
				
				// STEP 6 - UPDATE ORIGINAL RECORDS AS 'PROCESSED'
				System.out.println("Last keyrrn read to be updated " + keyrrn);
	            updateStatement.executeUpdate("Update EDIHeader set E997Sent ='Y' where key204rrn =" + keyrrn);
			}
			
		}
		
		catch (SQLException e) { e.printStackTrace(); }	
		
		finally 
		{
            try { resultSet.close(); } catch (Exception e) { /* Ignored */ }
            try { statement.close(); } catch (Exception e) { /* Ignored */ }
            try { connection.close(); } catch (Exception e) { /* Ignored */ }
        }
	}
}
