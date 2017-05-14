package com.verity.webreport;

import java.io.*;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;



/** Simple servlet for testing. Makes use of a helper class. */


@WebServlet("/error")
public class Error extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ProjectSummary> projects = new ArrayList<ProjectSummary>();
		
		try {
			
			
			
		    response.setContentType("text/html");
		    PrintWriter out = response.getWriter();
		    String title = "Verity Automation Regression Results";
		    
		    
		    
		    out.println
		      (ServletUtilities.headWithTitle(title) +
		    	"<body>\n" +
	
		    	
		    	
		    	
		    	
		    	"<div id=\"mainWrapper\">\n" +
		       	"<img src=\"images/logo.gif\"/><br/>\n" +
		       	"<h2>&nbsp; &nbsp; Automation Regression Results Summary</h2>\n" + 
		
		       
				"<table id=\"error\" class=\"summary\">\n<tbody>\n"+
				"<tr><th>Slow down, you're breaking up the page! <a href =\"/ReportServlet\">Go back to home page</a></th></tr>");
		
					
			out.println("</tbody></table>\n"+	       
			 		"<br/><br/><br/><br/>\n");
				 
		       
		       
		       
		       
		       
		       
		       
		//       "<br/><br/><br/><br/><br/>\n"+
		//       "<span id=\"hover\">Don't move mouse over this</span>\n" +
		//       "<br/><br/><br/><br/><br/>\n"+
		       out.println("</div></body></html>");
    
	  } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.sendRedirect("error");
		}
  }
}
