# Java-SQL-EDI997-Project

PsuedoCode

//Step 1 - JSON READER [JSONhelper.java]
  read JSON config file (only one time) to get the output path 

// Step 2 - CHECK [SQL_Pathway.java]
  LOOP (CHECK records to process)
	if (no records) {END PROGRAM}
	else (available records)
	{
 
//Step 3 - TRANSACTION NUMBER [RetrieveST.java]
  	- Retrieve the next 997 transaction number (STnumber)
   	- Update the database with the last 997 transaction number	
		
//Step 4 - COMPILATION [SQL_Pathway.java]
	- Compile elements for the 997 document
			
//Step 5 - FILE CREATION [EDIcreator.java]
	- Use the information we have received to create the 997 file
	- pass the output path to it (JSON) [STEP 1]
	- pass it the content from compilation [STEP 4]		
			
//Step 6 - UPDATE [SQL_Pathway.java]
	- Update the original records as "processed" 

}
