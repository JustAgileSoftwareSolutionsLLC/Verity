package com.verity.webreport;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



public class DatabaseConnection {
	private String connectionUrl = "jdbc:sqlserver://LABSQL33110VM;" +
			"databaseName=TCOERegressiondB;";
	private final String Username="sv-apptregression";
	private final String Password="2utRawRa";
	private Connection con = null;
	private Statement stmt = null;
	
	
	public DatabaseConnection() throws SQLException{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		con = DriverManager.getConnection(connectionUrl,Username,Password);   
	}
	
	public void updateTable(String query) throws SQLException{
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if (stmt != null)
				stmt.close();
		}
		
	}
	
	public ResultSet getValues(String query) throws SQLException{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs;       
	}
	
	public ResultSet getAllApplications() throws SQLException{
		return getValues("select distinct Application from Results order by Application");	
	}
	
	public int getProjectsCount() throws SQLException{
		String query = "select count(distinct Application) from Results";
		ResultSet rs= getValues(query);
		return rs.getInt(1);
	}
	
	
	public ProjectSummary getProjectSummary(String projectName) throws SQLException{
		PreparedStatement statement = null, getPassStatement = null, getFailStatement = null, getStartDateStatement = null;
		
		
		try{
			ProjectSummary project = new ProjectSummary(projectName);
			
			String preparedQuery = "select DISTINCT CycleName, max(RunDateTimeStamp) from results where Application = ? " +
										"group by CycleName order by max(RunDateTimeStamp) desc";
			statement = con.prepareStatement(preparedQuery);
			statement.setString(1, projectName);
			ResultSet rs = statement.executeQuery();
			
			//get all cycle name
//			String query = "select DISTINCT CycleName, max(RunDateTimeStamp) from results where Application='" + projectName +
//					"' group by CycleName order by max(RunDateTimeStamp) desc";
			
			
			
			
			//ResultSet rs = getValues(query);
			while(rs.next()){
				try{
					
				}
				catch(Exception e){
					
				}
				String startDateString;
//				String getPassQuery = "select count(TestCaseID) from results where Results='Pass' and cycleName ='" +
//						rs.getString(1) +"' and Application='"+ projectName +"'";
//				String getFailQuery = "select count(TestCaseID) from results where Results='Fail' and cycleName ='" +
//						rs.getString(1) +"' and Application='"+ projectName +"'";
				
				String getPassQuery = "select count(TestCaseID) from results where Results='Pass' and cycleName =? and Application = ?";
				String getFailQuery = "select count(TestCaseID) from results where Results='Fail' and cycleName =? and Application = ?";
				getPassStatement = con.prepareStatement(getPassQuery);
				getPassStatement.setString(1, rs.getString(1));
				getPassStatement.setString(2, projectName);
				
				getFailStatement = con.prepareStatement(getFailQuery);
				getFailStatement.setString(1, rs.getString(1));
				getFailStatement.setString(2, projectName);
				
				ResultSet tempRS = getPassStatement.executeQuery();
				tempRS.next();
				int pass = tempRS.getInt(1);
				tempRS = getFailStatement.executeQuery();
				tempRS.next();
				int fail = tempRS.getInt(1);
				
//				String getstartDateQuery = "select startdate from Cycles where application='"+ projectName +"' and cyclename='" + rs.getString(1) + "'";
				String getstartDateQuery = "select startdate from Cycles where application = ? and cyclename = ?";
				getStartDateStatement = con.prepareStatement(getstartDateQuery);
				getStartDateStatement.setString(1, projectName);
				getStartDateStatement.setString(2, rs.getString(1));
				
				ResultSet getStartDate = getStartDateStatement.executeQuery();
				getStartDate.next();
				try{
					Date startDate = getStartDate.getDate(1);
					startDateString = convertDate(startDate);
				}
				catch(Exception e){
					startDateString = "N/A";
				}
				
				//getting lastRunDate from Results instead of Cycles because BeforeSuite and AfterSuite are not invoked if running
				//1 test case seperately, so when starting regression suite, running config will trigger start date correctly
				//but last run will be used from the latest time stamp of all of test cases in the cycle 
				project.addCycle(new CycleSummary(rs.getString(1), pass, fail, convertDate(rs.getDate(2)), startDateString));
			}
			
			
			
			return project;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			statement.close();
			getPassStatement.close();
			getFailStatement.close();
			getStartDateStatement.close();
		}
		return null;
	}
	
	public CycleSummary getCycleSummary(String project, String cycle) throws SQLException{
		PreparedStatement getLastRunStatement = null, getPassStatement = null, getFailStatement = null, getStartDateStatement = null;
		try{
			String startDateString;
			//String passQuery="select count(TestCaseID) from results where application='"+ project +"' and cyclename='"+ cycle +"' and results='pass'";
			String passQuery="select count(TestCaseID) from results where application = ? and cyclename = ? and results='pass'";
			getPassStatement = con.prepareStatement(passQuery);
			getPassStatement.setString(1, project);
			getPassStatement.setString(2, cycle);
			ResultSet getPass = getPassStatement.executeQuery();
			getPass.next();
			int pass = getPass.getInt(1);
			
			//String failQuery="select count(TestCaseID) from results where application='"+ project +"' and cyclename='"+ cycle +"' and results='fail'";
			String failQuery="select count(TestCaseID) from results where application = ? and cyclename = ? and results='fail'";
			getFailStatement = con.prepareStatement(failQuery);
			getFailStatement.setString(1, project);
			getFailStatement.setString(2, cycle);
			ResultSet getFail = getFailStatement.executeQuery();
			getFail.next();
			int fail = getFail.getInt(1);
			
			//String lastRunQuery ="select max(RunDateTimeStamp) from results where Application='"+ project +"' and cyclename='"+ cycle +"'";
			String lastRunQuery ="select max(RunDateTimeStamp) from results where Application = ? and cyclename = ?";
			getLastRunStatement = con.prepareStatement(lastRunQuery);
			getLastRunStatement.setString(1, project);
			getLastRunStatement.setString(2, cycle);
			ResultSet getLastRun = getLastRunStatement.executeQuery();
			getLastRun.next();
			Date lastRun = getLastRun.getDate(1);
			
			//String startDateQuery = "select startdate from Cycles where application='"+ project +"' and cyclename='" + cycle + "'";
			String startDateQuery = "select startdate from Cycles where application = ? and cyclename ?";
			getStartDateStatement = con.prepareStatement(startDateQuery);
			getStartDateStatement.setString(1, project);
			getStartDateStatement.setString(2, cycle);
			ResultSet getStartDate = getStartDateStatement.executeQuery();
			getStartDate.next();
			try{
				Date startDate = getStartDate.getDate(1);
				startDateString = convertDate(startDate);
			}
			catch(Exception e){
				startDateString = "N/A";
			}
			
			return new CycleSummary(cycle, pass, fail, convertDate(lastRun), startDateString);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			getLastRunStatement.close();
			getPassStatement.close();
			getFailStatement.close();
			getStartDateStatement.close();
		}
		return null;
	}
	
	
	/* Query should look like this
	 * 		String query = "select TestCaseID, TestScriptName, Results,Screenshot,ExceptionMessage from Results where Application='"
	    		+ projectName
	    		+"' and CycleName='" + cycleName
	    OR
	    	String query = "select TestCaseID, TestScriptName, Results,Screenshot,ExceptionMessage from Results where Application='"
	    		+ projectName
	    		+"' and CycleName='" + cycleName
	    		+"' and Results='Fail'";
	 */
	public List<TestCaseReport> getDetailedReport(String projectName, String cycleName, String display) throws SQLException{
		PreparedStatement statement = null;
		try{
			List<TestCaseReport> report = new ArrayList<TestCaseReport>();
			if(display.equalsIgnoreCase("fail") || display.equalsIgnoreCase("pass")){
//		    	String query = "select TestCaseID, TestScriptName, Results,Screenshot,ExceptionMessage from Results where Application='"
//		    			+ projectName
//		    			+"' and CycleName='" + cycleName
//		    			+"' and Results='"+ display +"'";
				String query = "select TestCaseID, TestScriptName, Results,Screenshot,ExceptionMessage from Results where " +
						"Application=? and CycleName=? and Results=? ";
				statement = con.prepareStatement(query);
				statement.setString(1, projectName);
				statement.setString(2, cycleName);
				statement.setString(3, display);
				
				ResultSet rs = statement.executeQuery();
				while(rs.next()){
					report.add(new TestCaseReport(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
				}
			}
			else{
		    	String query = "select TestCaseID, TestScriptName, Results,Screenshot,ExceptionMessage from Results where " +
		    			"Application=? and CycleName=?";
				statement = con.prepareStatement(query);
				statement.setString(1, projectName);
				statement.setString(2, cycleName);
				
				ResultSet rs = statement.executeQuery();
				while(rs.next()){
					report.add(new TestCaseReport(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
				}
			}

			
			return report;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			statement.close();
		}
		return null;
	
	}
	
	public String getClassName(String project, String cycle, String testCaseID) throws SQLException{
		PreparedStatement statement = null;
		try{
//			String query = "select ClassName from results where application='"+ project +"' and CycleName='" + cycle + "'  and TestCaseID='" + testCaseID + "'";
			String query = "select ClassName from results where application=? and CycleName=?  and TestCaseID=?";
			statement = con.prepareStatement(query);
			statement.setString(1, project);
			statement.setString(2, cycle);
			statement.setString(3, testCaseID);
			ResultSet rs = statement.executeQuery();
			rs.next();
			return rs.getString(1);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			statement.close();
		}
		return null;
	}
	
	private String convertDate(Date date){
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy"); 
		return df.format(date);
	}
	

}
