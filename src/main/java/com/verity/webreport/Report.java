package com.verity.webreport;

import java.io.*;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;



/** Simple servlet for testing. Makes use of a helper class. */

@WebServlet("/report")
public class Report extends HttpServlet {
	
	//public static final String hostIP = "http://10.107.133.218:8080";
	
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	try {
		DatabaseConnection dataConn = new DatabaseConnection();
		
		
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    String projectName = request.getParameter("project");
	    String cycleName = request.getParameter("cycle");
	    String display = request.getParameter("display");
	    String title = "Verity Automation Regression Results";
	    
	    ProjectSummary project = dataConn.getProjectSummary(projectName);
	    
	    out.println(
	    	"<!DOCTYPE html>\n" +
	    	"<html>\n" +
	    	"<head><title>" + title + "</title>\n" +
	    		"<link rel=\"stylesheet\"\n" + "href=\"./css/styles.css\"\n"+ "type=\"text/css\"/>\n" +
	    	
	    		//"<link href=\"./js/demo_page.css\" rel=\"stylesheet\" type=\"text/css\" />"+
    	        //"<link href=\"./js/demo_table.css\" rel=\"stylesheet\" type=\"text/css\" />"+
	    	
//				"<style type=\"text/css\" title=\"currentStyle\">"+
//				"@import \"./js/demo_page.css\";"+
//				"@import \"./js/demo_table.css\";"+
//				"@import \"./js/ColReorder.css\";"+
//				"@import \"./js/ColVis.css\";"+
//				
//				"thead input { width: 100% }"+
//				"input.search_init { color: #999 }"+
//				"</style>"+
			
		
//				"<script src=\"//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js\"></script>"+
//				"<script type=\"text/javascript\" charset=\"utf-8\" src=\"./js/jquery.dataTables.min.js\"></script>"+
//				"<script type=\"text/javascript\" charset=\"utf-8\" src=\"./ColReorder.js\"></script>"+
//	    		"<script type=\"text/javascript\" charset=\"utf-8\" src=\"./js/ColVis.js\"></script>"+
//	    		"<script type=\"text/javascript\" charset=\"utf-8\">"+
//	    		"$(document).ready(function() {" +
//				"var oTable;"+
//				"$(\"thead input\").keyup( function () {"+
//					"oTable.fnFilter( this.value, oTable.oApi._fnVisibleToColumnIndex("+ 
//					"oTable.fnSettings(), $(\"thead input\").index(this) ) );"+
//				"} );"+
//
//				"$(\"thead input\").each( function (i) {"+
//					"this.initVal = this.value;"+
//				"} );"+
//				
//				"$(\"thead input\").focus( function () {"+
//					"if ( this.className == \"search_init\" )"+
//					"{"+
//						"this.className = \"\";"+
//						"this.value = \"\";"+
//					"}"+
//				"} );"+
//				
//				"$(\"thead input\").blur( function (i) {"+
//					"if ( this.value == \"\" )"+
//					"{"+
//						"this.className = \"search_init\";"+
//						"this.value = this.initVal;"+
//					"}"+
//				"} );"+
//				
//				"oTable = $('#example').dataTable( {"+
//					"\"sDom\": 'RC<\"clear\">lfrtip',"+
//					"\"aoColumnDefs\": ["+
//						"{ \"bVisible\": false, \"aTargets\": [ 2 ] }"+
//					"],"+
//					"\"oLanguage\": {"+
//						"\"sSearch\": \"Search all columns:\""+
//					"},"+
//					"\"bSortCellsTop\": true"+
//				"} );"+
//			"} );"+
//		"</script>"+	    	
	    	
	    	
		
//  "<script src='http://ajax.microsoft.com/ajax/jquery/jquery-1.4.2.min.js' type='text/javascript'></script>"+    
//  
//  "<script type='text/javascript' src='./js/picnet.table.filter.min.js'></script>"+    
		
// "<script type='text/javascript'>"+
//  "$(document).ready(function() {"+
//     
//    // Initialise Plugin
//      "var options1 = {"+
//          "additionalFilterTriggers: [$('#onlyyes'), $('#onlyno'), $('#quickfind')],"+
//          "clearFiltersControls: [$('#cleanfilters')],"+
//          "matchingRow: function(state, tr, textTokens) {"+
//            "if (!state || !state.id) {"+
//              "return true;"+
//          "}"+
//            "var child = tr.children('td:eq(2)');"+
//            "if (!child) return true;"+
//            "var val = child.text();"+
//            "switch (state.id) {"+
//            "case 'onlyyes':"+
//              "return state.value !== true || val === 'yes';"+
//            "case 'onlyno':"+
//              "return state.value !== true || val === 'no';"+
//            "default:"+
//              "return true;"+
//          "}"+
//        "}"+
//    "};"+
//
//      "$('#demotable1').tableFilter(options1);"+
//
//
//"});"+
//"</script>"+
		

			

		
			"<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js\" type=\"text/javascript\"></script>" +
			//"<script type=\"text/javascript\" charset=\"UTF-8\" src=\"js/lightbox_plus_min.js\"></script>"+
			//"<script type=\"text/javascript\" charset=\"UTF-8\" src=\"js/lightbox_plus.js\"></script>"+

			"<script type=\"text/javascript\"> \n" +
				"$(function() { \n" +
				"$(\"table#projectSummary tr:nth-child(odd)\").addClass(\"odd-row\"); \n"+
				"$(\"table#projectSummary td:first-child, table th:first-child\").addClass(\"first\"); \n" +
				"$(\"table#projectSummary td:last-child, table th:last-child\").addClass(\"last\"); \n" +
				"});\n" +
			"</script> \n"+
		
			"<script src=\"./js/jquery.dataTables.min.js\""+ " type=\"text/javascript\"></script>"+
			//"<script src=\"./js/jquery.dataTables.js\""+ " type=\"text/javascript\"></script>"+
			"<script type=\"text/javascript\">"+ 
				"$(document).ready(function () {"+
				"$(\"#details\").dataTable({"+
//				"\"sPaginationType\": \"full_numbers\","+
//				"\"bJQueryUI\": true"+
				"});"+
				"});"+
			"</script>"+
				
			
//			"<script type=\"text/javascript\">"+ 
//				"$(\"#testCaseID\").mouseover(function() {"+
//			    	"$(this).children(\"#tooltip\").show();"+
//			    	"}).mouseout(function() {"+
//			    	"$(this).children(\"#tooltip\").hide();"+
//				"});"+
//			"</script>"+

//"<script type=\"text/javascript\">"+ 
//"$(\"#testText\").hover("+
//   "function(e){"+
//       "$(\"#tooltip\").show();"+
//   "},"+
//   "function(e){"+
//       "$(\"#tooltip\").hide();"+
//  "});"+
//	"</script>"+
				

			"<script type=\"text/javascript\" src=\"./js/jquery.fancybox.js\"></script>"+	
			"<script type=\"text/javascript\" src=\"./js/jquery.fancybox-buttons.js\"></script>"+
			
			
			"<script type=\"text/javascript\">"+
			"$(document).ready(function() {"+
				"$('.modal').fancybox();"+
			"});"+
			"</script>"+

//"<script type=\"text/javascript\">"+
//"function exportExcel(){"+
//	"alert(\"export excel function\");"+
//"}"+
//"</script>"+			

			
//			"<script type=\"text/javascript\">" +
//				"$(document).ready(function() {"+
//					"$('.modal').fancybox({"+
//						"closeBtn  : false,"+
//						"helpers : {"+
//							"title : {"+
//								"type : 'inside'"+
//							"},"+
//							"buttons	: {}"+
//						"},"+
//					"});"+
//				"});"+
//			"</script>"+
	


	

				
				     
	    	
	    	
	    	"</head>\n"+
	    	"<body><div id=\"mainWrapper\"><a href=\"/ReportServlet\">Home Page</a>\n" +
	       	"<img src=\"images/logo.gif\"/><br/>\n" +
//	       	"<h2>&nbsp; &nbsp; "+ projectName + " " + cycleName +" Regression Results</h2>\n" +
	       	"<div id=\"title\"><h2>"+ projectName + " " + cycleName +" Regression Results</h2></div>\n" +
	//       	"<h3>Environment</h3>\n"+
	//       	"<span>\n" +
	//       	"Host name: " + InetAddress.getLocalHost().getHostName() + "<br/>\n" +
	//       	"OS name: " + System.getProperty("os.name") + "<br/>\n" +
	//       	"OS version: " + System.getProperty("os.version") + "<br/>\n" +
	//       	"</span>\n" +
	
	       
	
			"<table id=\"projectSummary\" class=\"summary\">\n<tbody>" +
	    		"<tr><th colspan=\"2\">Summary</th></tr>"+
	    		//"<tr><td>Cycle name</td><td>" + project.getCycles().get(0).getName() + "</td>\n</tr>\n"+
	    		"<tr><td>Cycle Name</td><td>" + cycleName + "</td>\n</tr>\n");
	    
	    

	    
	    
	    out.print(
	    		"<tr><td>Pass rate</td><td>" + project.getCycles().get(project.indexOfCycleName(cycleName)).getPassRate() + "%</td>\n</tr>\n"+	    		
				"<tr><td>Passed</td><td><a href=\"/ReportServlet/report?project="+ projectName 
				+"&cycle=" + cycleName + "&display=pass\">" + project.getCycles().get(project.indexOfCycleName(cycleName)).getPassed() + "</a></td>\n</tr>\n"+
	    		"<tr><td>Failed</td><td><a href=\"/ReportServlet/report?project="+ projectName 
	    		+"&cycle=" + cycleName + "&display=fail\">" + project.getCycles().get(project.indexOfCycleName(cycleName)).getFailed() + "</a></td>\n</tr>\n"+
	    		"<tr><td>Total</td><td><a href=\"/ReportServlet/report?project="+ projectName 
	    		+"&cycle=" + cycleName + "&display=all\">" + project.getCycles().get(project.indexOfCycleName(cycleName)).getTotal() + "</a></td>\n</tr>\n"+
	    		"<tr><td>Start date</td><td>" + project.getCycles().get(project.indexOfCycleName(cycleName)).getStartDate() + "</a></td>\n</tr>\n"+
	    		"<tr><td>Last run date</td><td>" + project.getCycles().get(project.indexOfCycleName(cycleName)).getLastRunDate() + "</a></td>\n</tr>\n"+
	    		"<tr><td>Export to Excel</td><td>" +
	    			"<select name=\"exportExcel\" onchange=\"location = this.options[this.selectedIndex].value;\">"+
	    				"<option value=\"#\">Select an option</option>"+
	    				"<option value=\"exportExcel?option=all&project="+ projectName +"&cycle="+ cycleName +"\">All</option>"+
	    				"<option value=\"exportExcel?option=fail&project="+ projectName +"&cycle="+ cycleName +"\">Failed only</option>"+
	    				"<option value=\"exportExcel?option=pass&project="+ projectName +"&cycle="+ cycleName +"\">Passed only</option>"+
	    			"</select>"+
	    		"</td></tr>"+
			"</tbody></table>\n"+
	       
	 		"<br/>" +
	 		"<br/>\n");
	 		
	    if(display.equalsIgnoreCase("pass")){
	    	List<TestCaseReport> report = dataConn.getDetailedReport(projectName, cycleName, display);
	    	out.print(
	    			"<table id=\"details\" class=\"display\">"+
	    				"<thead>"+
	    					"<tr>"+
	    					"<th>Test case ID</th>"+
	    					"<th>Description</th>"+
	    					"<th>Status</th>"+
	    					"</tr>"+
	    				"</thead>"+
	    				"<tbody>");
	    	for(int i = 0; i <report.size(); i++){
	    		String ID = report.get(i).getID();
	    		String description = report.get(i).getDescription();
	    		String status = report.get(i).getStatus();
	    		String className = dataConn.getClassName(projectName, cycleName, ID);
	    		out.print(
	    					"<tr>"+
	    						"<td id=\"testCaseID\">"+ ID +"<div class=\"desc\">"+ className +"</div></td>"+
	    						"<td>"+ description +"</td>"+
	    						"<td>"+ status +"</td>");
	    	}
	    					
	    	out.print(				
	    				"</tbody>"+
	    			"</table>");
	    }
	    else{
	    	List<TestCaseReport> report = dataConn.getDetailedReport(projectName, cycleName, display);
	    	
	    	out.print(
	    			"<table id=\"details\" class=\"display\">"+
	    				"<thead>"+
	    					"<tr>"+
	    					"<th>Test case ID</th>"+
	    					"<th>Description</th>"+
	    					"<th>Status</th>"+
	    					"<th>Screenshot</th>"+
	    					"<th>Error Message</th>"+
	    					"</tr>"+
	    				"</thead>"+
	    				"<tbody>");
	    	for(int i = 0; i <report.size(); i++){
	    		String ID = report.get(i).getID();
	    		String description = report.get(i).getDescription();
	    		String status = report.get(i).getStatus();
	    		String screenshot = report.get(i).getScreenshot();
	    		String error = report.get(i).getError();
	    		String className = dataConn.getClassName(projectName, cycleName, ID);
	    		out.print(
	    					"<tr>"+
	    						"<td id=\"testCaseID\">"+ ID +"<div class=\"desc\">" + className+ "</div></td>"+
	    						"<td>"+ description +"</td>"+
	    						"<td>"+ status +"</td>");
	    		
	    		if(screenshot == null)
	    			out.print("<td>"+ screenshot +"</td>");
	    		else if(screenshot.equalsIgnoreCase("null"))
	    			out.print("<td>"+ screenshot +"</td>");
	    		else{

//	    			out.print("<td><a href=\"."+ report.get(i).getScreenshot() +"\" target=\"_blank\">Link</a></td>");
//	    			out.print("<td><a href=\"."+ report.get(i).getScreenshot() + "\" rel=\"lightbox\" target=\"_blank\">Link</a></td>");
	    			out.print("<td><a href=\"."+ report.get(i).getScreenshot() + "\" class=\"modal\" " +
	    					"title=\""+ ID +"\"" +
	    					"target=\"_blank\">View</a></td>");
	    			
	    			
	    			//out.print("<td><a href=\""+ hostIP + "/ReportServlet/" +  report.get(i).getScreenshot() +"\">Link</a></td>");
	    		}
	    		out.print("<td>"+ error +"</td>"+
    					"</tr>");
	    	}
	    					
	    	out.print(				
	    				"</tbody>"+
	    			"</table>");
	    }
	    

			
		out.print(       
			"<br/><br/>\n" +       
	//       "<br/><br/><br/><br/><br/>\n"+
	//       "<span id=\"hover\">Don't move mouse over this</span>\n" +
	//       "<br/><br/><br/><br/><br/>\n"+
	       "</div></body></html>");
	}
	catch(Exception e){
		e.printStackTrace();
		response.sendRedirect("error");
	}
  }
}
