package servlet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.sql.*;


public class EntryController {

	public EntryController(){
		
	}
	
	public String getEntriesForTable() throws SQLException{
		try {
			//setup database connection
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/mediajournal");
			//connect to database
			Connection myConn = ds.getConnection();
			//create a statement
			Statement myStatement = myConn.createStatement();
			//execute SQL query which returns every entry of participants table
			ResultSet myRs = myStatement.executeQuery("select * from participants");
			//create String with whole table as html code
			String participants = "<tr><td>ID</td><td>Alter</td><td>Datum</td>"
					+ "<td>Kategorie</td><td>Interaktion</td><td>Plattform</td></tr>";
			while (myRs.next()) {
				participants = participants + " \n" + " <tr> <td>"+ myRs.getString(2) + " </td><td> " + myRs.getString(3)
						+ " </td><td> " + myRs.getString(4) + " </td><td> " + myRs.getString(5) 
						+ " </td><td> " + myRs.getString(6) + " </td><td> " + myRs.getString(7) + " </td></tr> ";
			}
			return participants;
		}
		catch (Exception exc) {
			exc.printStackTrace();
			return "unable to get entries";
		}
	}
	
	public String getEntriesForDownload() throws SQLException{
		try {
			//setup database connection
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/mediajournal");
			//connect to database
			Connection myConn = ds.getConnection();
			//create a statement
			Statement myStatement = myConn.createStatement();
			//execute SQL query which returns every entry of participants table
			ResultSet myRs = myStatement.executeQuery("select * from participants");
			//create String with whole table as csv code
			String participants = "ID, Alter, Datum, Kategorie, Interaktion, Plattform";
			while (myRs.next()) {
				participants = participants + " \n" + myRs.getString(2) + "," + myRs.getString(3)
						+ "," + myRs.getString(4) + "," + myRs.getString(5) 
						+ "," + myRs.getString(6) + "," + myRs.getString(7);
			}
			return participants;
		}
		catch (Exception exc) {
			exc.printStackTrace();
			return "unable to get entries";
		}
	}
	
}

