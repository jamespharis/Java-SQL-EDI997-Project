# Java-SQL-EDI997-Project





EDI  (Electronic Data Interchange)  is a form of data communications used to allow businesses to seamlessly communicate with each other. EDI utilizes standard "Transactions" to send and receive data between each other. 

Example: 
Walmart sends Electronic Purchase Order via EDI to vendor (Sony) for ordering Playstations. Sony replies back to Walmart with Electronic Order Confirmation that order for the Playstations was received. 
Once the product begins to ship from Sony to Walmart; Sony will send a Shipment EDI Transaction to Walmart with the shipping information. Provide the info you would see in amazon when checking on an order status.

The 997 transaction in our project is a standard transaction as well but it is optional (not required) in most cases. However, it is a valuable transaction as it fulfills a "Handshake" function in that it confirms back to the sender that the original transaction was received. In the example above, if Sony never sends back an Order Confirmation to Walmart, Walmart is not certain if Sony ever received the Order. There could be communication or other issues which prevent the Order from making it into Sony's system. If Sony is configured to respond with 997's then Walmart can verify if the order was ever received or not. 

So the 997 EDI simply confirms back to the sender that the original Electronic Transaction was received. It is a form of verification. 

Our project assumes the receiver company in our database is configured to respond with 997s. We are reading for Transactions received which have not generated a 997 response. We create the 997 response then mark the transaction in the database as "997 generated for this transaction".



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




This application provides support for an existing application processing inbound EDI transmissions and storing them in a Microsoft SQL Database 
by generating receipt confirmation transactions for any transmissions received without a confirmation.  
EDI Transactions allow for (but typically do not require) a “handshake” transmission from the receiving party to verify that the sender EDI Transmission was successfully received. 
This EDI transaction is called a Functional Acknowledgement and is designated by the EDI Transaction set code “997”.

This application reads through the database of previously received transactions which have not been confirmed. 
For each transaction it creates the appropriate 997 Functional Acknowledgement transaction as a text file to be sent to the original transaction sender. 
The application utilizes a JSON config file that contains the file system path where the EDI files will be created and placed and utilizes a Java print writer object to output the necessary EDI data and content. 
As previous transactions have the 997 Functional Acknowledgement transactions created they are flagged in the database as processed so no new functional acknowledgement would not be created for it in the future. 
The application also accesses and increments a counter in the database to reflect each 997 Functional Acknowledgement transaction control number. 
