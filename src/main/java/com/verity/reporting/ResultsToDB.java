package com.verity.reporting;

/**
 * Framework -QA CoE Test Framework
 * Version - 0.1
 * Creation Date - Feb, 2013
 * Author - Ramesh Tejavath
 * Description: Reporting to DB
 *  **/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.verity.utilities.ReadPropertyFile;

public class ResultsToDB {
	static String connectionUrl = "jdbc:sqlserver://LABSQL33110VM;" +
			"databaseName=TCOERegressiondB;";
	static String Username="sv-apptregression";
	static String Password="2utRawRa";
	static Connection con = null;
	static Statement stmt = null;
	static ReadPropertyFile SetUp=new ReadPropertyFile();
	public static Logger logger= Logger.getLogger(ResultsToDB.class);
	
//	public static void WriteToDB(String TestcaseID, 
//			String Result, 
//			//Date RunDate, 
//			String Machine, 
//			String Exception 
//			//int RunTime
//			) throws SQLException{
//		
//		String Application =SetUp.getConfigPropertyVal("Application"); //ReadPropertyFile.ReadFile("C:/Selenium/Mobile/Config.properties", "Application");
//		String CycleName = SetUp.getConfigPropertyVal("Cycle"); //ReadPropertyFile.ReadFile("C:/Selenium/Mobile/Config.properties", "Cycle");
//		String SQL;
//		SQL = "INSERT INTO [dbo].[Results_test]" +
//			"([Application],[CycleName],[TestCaseID],[Results],[RunDateTimeStamp],[TestMachine],[ExceptionMessage])" +
//			"VALUES('" 
//			+ Application +"','"
//			+ CycleName +"','"
//			+ TestcaseID + "','"
//			+ Result + "',"
//            //+ RunDate + ","
//			+ "CURRENT_TIMESTAMP,'"
//            + Machine + "','"
//            + Exception+ "')";
//          //  + RunTime + "')";
//		System.out.println(SQL);
//		connectToAndUpdateDatabase(SQL);
//		
//	}
	
	
	
	public static void WriteToResultsTable(String TestcaseID, 
			String TestDescription,
			String Result, 
			String Machine, 
			String Exception,
			String Screenshot,
			String ClassName
			) throws SQLException{
		
		String Application =SetUp.getConfigPropertyVal("Application"); //ReadPropertyFile.ReadFile("C:/Selenium/Mobile/Config.properties", "Application");
		String CycleName = SetUp.getConfigPropertyVal("Cycle"); //ReadPropertyFile.ReadFile("C:/Selenium/Mobile/Config.properties", "Cycle");
		String SQL;
//		SQL = "INSERT INTO [dbo].[Results_test]" +
//			"([Application],[CycleName],[TestCaseID],[TestScriptName],[Results],[RunDateTimeStamp],[TestMachine],[ExceptionMessage])" +
//			"VALUES('" 
//			+ Application +"','"
//			+ CycleName +"','"
//			+ TestcaseID + "','"
//			+ TestDescription + "','"
//			+ Result + "',"
//			+ "CURRENT_TIMESTAMP,'"
//            + Machine + "','"
//            + Exception+ "')";

		
//		SQL = "UPDATE [dbo].[Results_test] " +
//				  "SET " 
//				  	+ "[TestScriptName] = '" + TestDescription +"', "
//				  	+ "[Results] = '" + Result +"', "
//				  	+ "[RunDateTimeStamp] = CURRENT_TIMESTAMP, "
//				  	+ "[TestMachine] = '" + Machine +"', "
//				  	+ "[ExceptionMessage] = '" + Exception +"' " +
//				  "WHERE" 
//				  	+ "[CycleName]='" + CycleName + "' AND [TestCaseID]='" + TestcaseID + "' ";
		
		
		SQL = "IF (NOT EXISTS(SELECT * FROM [dbo].[Results] WHERE [CycleName]='" + CycleName + "' AND [TestCaseID]='" + TestcaseID + "')) " +
				"BEGIN " + 
				"INSERT INTO [dbo].[Results]" +
					"([Application],[CycleName],[TestCaseID],[TestScriptName],[Results],[RunDateTimeStamp],[TestMachine],[Screenshot],[ClassName],[ExceptionMessage])" +
					"VALUES('" 
					+ Application +"','"
					+ CycleName +"','"
					+ TestcaseID + "','"
					+ TestDescription + "','"
					+ Result + "',"
					+ "CURRENT_TIMESTAMP,'"
			        + Machine + "','"
			        + Screenshot + "','"
			        + ClassName + "','"
			        + Exception+ "') " +
			"END " +
			"ELSE " +
				"BEGIN " +
				"UPDATE [dbo].[Results] " +
				"SET " 
					+ "[TestScriptName] = '" + TestDescription +"', "
					+ "[Results] = '" + Result +"', "
					+ "[RunDateTimeStamp] = CURRENT_TIMESTAMP, "
					+ "[TestMachine] = '" + Machine +"', "
					+ "[Screenshot] = '" + Screenshot +"', "
					+ "[ClassName] = '" + ClassName +"', "
					+ "[ExceptionMessage] = '" + Exception +"' " +
				"WHERE" 
					+ "[CycleName]='" + CycleName + "' AND [TestCaseID]='" + TestcaseID + "' " +
			"END";
		

		
		System.out.println(SQL);
		connectToAndUpdateDatabase(SQL);
		
		
		
	}
	
	
	public static void WriteStartDateToCyclesTable() throws SQLException{
		
		String Application =SetUp.getConfigPropertyVal("Application"); //ReadPropertyFile.ReadFile("C:/Selenium/Mobile/Config.properties", "Application");
		String CycleName = SetUp.getConfigPropertyVal("Cycle"); //ReadPropertyFile.ReadFile("C:/Selenium/Mobile/Config.properties", "Cycle");
		String SQL;		
		
		SQL = "IF (NOT EXISTS(SELECT * FROM [dbo].[Cycles] WHERE [CycleName]='" + CycleName + "' AND [Application]='" + Application + "')) " +
				"BEGIN " + 
				"INSERT INTO [dbo].[Cycles]" +
					"([Application],[CycleName],[StartDate])" +
					"VALUES('" 
					+ Application +"','"
					+ CycleName +"',"
					+ "CURRENT_TIMESTAMP" +") " +
			"END ";	
	
		System.out.println(SQL);
		connectToAndUpdateDatabase(SQL);
		
	}
	
	
	public static void WriteEndDateToCyclesTable() throws SQLException{
		
		String Application =SetUp.getConfigPropertyVal("Application"); //ReadPropertyFile.ReadFile("C:/Selenium/Mobile/Config.properties", "Application");
		String CycleName = SetUp.getConfigPropertyVal("Cycle"); //ReadPropertyFile.ReadFile("C:/Selenium/Mobile/Config.properties", "Cycle");
		String SQL;		
		
		SQL = "IF (NOT EXISTS(SELECT * FROM [dbo].[Cycles] WHERE [CycleName]='" + CycleName + "' AND [Application]='" + Application + "')) " +
				"BEGIN " + 
				"INSERT INTO [dbo].[Cycles]" +
					"([Application],[CycleName],[EndDate])" +
					"VALUES('" 
					+ Application +"','"
					+ CycleName +"',"
					+ "CURRENT_TIMESTAMP" +")" +
			"END " +
			"ELSE " +
				"BEGIN " +
				"UPDATE [dbo].[Cycles] " +
				"SET " 
					+ "[EndDate] = " + "CURRENT_TIMESTAMP" +" " +
					
				"WHERE" 
					+ "[CycleName]='" + CycleName + "' AND [Application]='" + Application + "' " +
			"END";	
	
		System.out.println(SQL);
		connectToAndUpdateDatabase(SQL);
		
	}
	
	private static void connectToAndUpdateDatabase(String SQL) throws SQLException{
		try{

			logger.info("conecting to DB server for reporting result");
			//Class.forName("net.sourceforge.jtds.jdbc.Driver");
			//System.setProperty("java.net.preferIPv6Addresses", "true");
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection(connectionUrl,Username,Password);
					       //  "jdbc:sqlserver://LABSQL33110VM:1433;DatabaseName=TCOERegressiondB;integratedSecurity=true;",
	                     //    "sv-apptregression",
	                       //  "2utRawRa");	    				
			logger.info("Connection Successful!");  
			//System.out.print(SQL);
             
			stmt = con.createStatement();
			stmt.executeUpdate(SQL);
		}catch(SQLException e){
			logger.info(e.getMessage());
			logger.info("Connection NOT Successful!");

		} //catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}	
		finally {
			
	    		if (stmt != null) try { stmt.close(); } catch(Exception e) {}
	    		if (con != null) try { con.close(); } catch(Exception e) {}
		}
	}
	
	
}
