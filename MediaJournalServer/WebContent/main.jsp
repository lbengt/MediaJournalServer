<html>
<%@ page import="servlet.EntryController" %>
    <head>
        <title>Medientagebuch Auswertung</title>
    </head>
    <body>
    	<% 
    		//create instance and get entries with html table code as String
    		EntryController entriesController = new EntryController(); 
    		String entries = entriesController.getEntriesForTable();
    		out.println("<table border=1 cellspacing=0>");
    		out.println(entries);
    		out.println("</table>");
    	%>	
        <form method="post" action="servlet">
        <input type="hidden" name="usecase"	value="download" />
        <input type="submit" value="download" />
        </form>
    </body>
</html>