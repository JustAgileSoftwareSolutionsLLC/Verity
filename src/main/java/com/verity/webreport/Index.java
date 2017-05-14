package com.verity.webreport;

import java.io.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;


/** Simple servlet for testing. Makes use of a helper class. */


@WebServlet("/index.html")
public class Index extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ProjectSummary> projects = new ArrayList<ProjectSummary>();
		
		try {
			DatabaseConnection dataConn = new DatabaseConnection();
			ResultSet rs = dataConn.getAllApplications();
			while(rs.next()){
				projects.add(dataConn.getProjectSummary(rs.getString(1)));
			}
		
			
			
		    response.setContentType("text/html");
		    PrintWriter out = response.getWriter();
		    String title = "Verity Automation Regression Results";
		    
		    
		    
		    out.println
		      (ServletUtilities.headWithTitle(title) +
		    	"<body>\n" +
		    		  
//		    	"<script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js\"></script> \n" +
//		    	"<script type=\"text/javascript\"> \n" +
//		    	"$(function() { \n" +
//		    	"$(\"table tr:nth-child(odd)\").addClass(\"odd-row\"); \n"+
//		    	"$(\"table td:first-child, table th:first-child\").addClass(\"first\"); \n" +
//		    	"$(\"table td:last-child, table th:last-child\").addClass(\"last\"); \n" +
//				"});\n" +
//				"</script> \n"+
				
				
    			"<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js\" type=\"text/javascript\"></script>" +
    			"<script type=\"text/javascript\">" +  
    			"$(document).ready(function(){" +
//    			"$(\"#report tr:odd\").addClass(\"odd\");" +
    			"$(\"#report tr:not(#latest)\").hide();"+
    			"$(\"#report tr:first-child\").show();"+
            
//            	"$(\"#report tr#latest\").click(function(){" +
            	"$(\"#report img#arrow\").click(function(){" +
            	"$hasNext = $(this.parentNode.parentNode);" +
                "while($hasNext.next(\"tr\").length && !($hasNext.next(\"tr\").attr(\"id\")==\"latest\")){" +
                	"$hasNext = $hasNext.next(\"tr\");" +
                	"$hasNext.toggle();"+
                "}"+
                
                
//                "hasNext.find(\".arrow\").toggleClass(\"up\");" +
            	"});"+
        		"});"+
        		"</script>" +  
		    	
		    	
		    	
		    	
		    	"<div id=\"mainWrapper\">\n" +
		       	"<img src=\"images/logo.gif\"/>\n" +
		       	"<div id=\"title\"><h2>Automation Regression Results Summary</h2></div>\n" + 
		
		       
				"<table id=\"report\" class=\"summary\">\n<tbody>\n"+
				"<tr><th>Project</th><th>Cycle Name</th><th>Pass Rate</th><th>Passed</th><th>Failed</th><th>Total</th><th>Start date</th><th>Last run date</th></tr>");
				
		    for(int i = 0; i <projects.size(); i++){
		    	out.println(
		    			"<tr class=\"main-row\" id=\"latest\">	" +
//		    				"<td><a href = \"/ReportServlet/report?project=" + projects.get(i).getName()
//		    						+ "&cycle=" + projects.get(i).getCycles().get(0).getName()
//		    						+ "&display=all"
//		    						+"\">"+ projects.get(i).getName() +"</a></td>" +
							"<td>"+ projects.get(i).getName() +"</td>");
		    	if(projects.get(i).getCycles().size() <= 1){
		    		out.println(
		    				"<td><a href=\"/ReportServlet/report?project="  
								+ projects.get(i).getName()
								+ "&cycle=" + projects.get(i).getCycles().get(0).getName()
								+ "&display=all\">"
								+ projects.get(i).getCycles().get(0).getName()
								+ "</a>" + "</td>");
		    	}
		    	else{
		    		out.println(
		    				"<td><a href=\"/ReportServlet/report?project="  
								+ projects.get(i).getName()
								+ "&cycle=" + projects.get(i).getCycles().get(0).getName()
								+ "&display=all\">"
								+ projects.get(i).getCycles().get(0).getName()
								+ "</a> <img id=\"arrow\" src=\"images/arrows.png\"/>" + "</td>");
		    	}
				out.println(
		    				"<td>" + projects.get(i).getCycles().get(0).getPassRate() +"%</td>" +
		    				"<td>" + projects.get(i).getCycles().get(0).getPassed() +"</td>" +
		    				"<td>" + projects.get(i).getCycles().get(0).getFailed() +"</td>" +
		    				"<td>" + projects.get(i).getCycles().get(0).getTotal() +"</td>" +
		    				"<td class=\"dateCol\">" + projects.get(i).getCycles().get(0).getStartDate() +"</td>" +
		    				"<td class=\"dateCol\">" + projects.get(i).getCycles().get(0).getLastRunDate() +"</td>" +
		    			"</tr>\n");
		    	
		    	for(int j = 1; j<projects.get(i).getCycles().size(); j++){
			    	out.println(
			    			"<tr>" +
			    				"<td>"+ "</td>" +
			    				"<td><a href=\"/ReportServlet/report?project="  
									+ projects.get(i).getName()
									+ "&cycle=" + projects.get(i).getCycles().get(j).getName()
									+ "&display=all\">"
									+ projects.get(i).getCycles().get(j).getName()
									+ "</a>" + "</td>" +
			    				"<td>" + projects.get(i).getCycles().get(j).getPassRate() +"%</td>" +
			    				"<td>" + projects.get(i).getCycles().get(j).getPassed() +"</td>" +
			    				"<td>" + projects.get(i).getCycles().get(j).getFailed() +"</td>" +
			    				"<td>" + projects.get(i).getCycles().get(j).getTotal() +"</td>" +
			    				"<td>" + projects.get(i).getCycles().get(j).getStartDate() +"</td>" + 
			    				"<td>" + projects.get(i).getCycles().get(j).getLastRunDate() +"</td>" +
			    			"</tr>\n");
		    	}
		    	
		    	
		    }
//		    out.println("<tr><td>Host name</td><td>" + InetAddress.getLocalHost().getHostName() + "</td>\n</tr>\n"+
//					"<tr><td>OS name</td><td>" + System.getProperty("os.name") + "</td>\n</tr>\n"+
//					"<tr><td>OS version</td><td>" + System.getProperty("os.version") + "</td>\n</tr>\n");
					
					
					
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
