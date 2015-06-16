package servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;


public class Input extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
    	//if post method is used
    	//get body of request
    	BufferedReader buffreader = request.getReader();
        StringBuffer inputString = new StringBuffer();
        String line = buffreader.readLine();
        while (line != null) {
            inputString.append(line + "\n");
            line = buffreader.readLine();
            System.out.println(line);
        }
        buffreader.close();
        //recreate the json object, get the entries and send them to database
        try {
			JSONObject jObj = new JSONObject(inputString.toString());
			String userid = (String) jObj.get("userid");
			System.out.println(userid);
			String age = (String) jObj.get("age");
			System.out.println(age);
			String date = (String) jObj.get("date");
			System.out.println(date);
			String category = (String) jObj.get("category");
			System.out.println(category);
			String interaction = (String) jObj.get("interaction");
			System.out.println(interaction);
			String platform = (String) jObj.get("platform");
			System.out.println(platform);
			try {
				setEntry(userid, age, date, category, interaction, platform);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
	
	public void setEntry(String userid, String age, String date, String category, String interaction, String platform) 
			throws SQLException{
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
			//execute SQL statement which inserts values
			myStatement.executeUpdate("insert into participants (userid, age, date, category,"
					+ " interaction, platform) values ('" + userid + "', '" + age + "', '"
					+ date + "', '" + category + "', '" + interaction + "', '" + platform + "')");
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}
