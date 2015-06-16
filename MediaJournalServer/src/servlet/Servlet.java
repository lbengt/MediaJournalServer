package servlet;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
import servlet.EntryController;

public class Servlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private EntryController entryCon;
	//HALLO
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//every post method has parameter usecase so Servlet knows what to do
    	String usecase = request.getParameter("usecase");
    	if(usecase.equals("login")){
    		//get name and password and validate if this combination is in database
    		response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            
            try {
    			if(validationBoolean(name, password))
    			{
    				//go to main.jsp if name and password is correct
    			    RequestDispatcher rs = request.getRequestDispatcher("main.jsp");
    			    rs.forward(request, response);
    			}
    			else
    			{
    				//go back to index.jsp if name and password is not correct
    				out.println("Username or Password incorrect");
    				RequestDispatcher rs = request.getRequestDispatcher("index.jsp");
    				rs.include(request, response);
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}

    	if(usecase.equals("download")){
        	String entries = "";
        	//get entries of the database table with csv code
        	try {
        		entryCon = new EntryController();
        		entries = entryCon.getEntriesForDownload();
        	}
        	catch (SQLException exc){
        		exc.printStackTrace();
        	}
        	//create download
    	    response.setContentType("text/csv");
    	    response.setHeader("Content-Disposition", "attachment; filename=\"Medientagebuch.csv\"");
    	    OutputStream outputStream = response.getOutputStream();
    	    outputStream.write(entries.getBytes());
    	    outputStream.flush();
    	    outputStream.close();
    	}

    } 
    
    public static boolean validationBoolean(String name,String password) throws SQLException{
    	boolean validation = false;
    	try{
			//setup database connection
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/mediajournal");
			//connect to database
			Connection myConn = ds.getConnection();
			//create a statement
			Statement myStatement = myConn.createStatement();
			//execute SQL query which proves if combination of name and passsword is in database
			ResultSet myRs = myStatement.executeQuery("select * from user where Name='"
					+ name + "' and Password='" + password + "'");
			validation = myRs.next();
       
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
        return validation;                 
    }  
}
