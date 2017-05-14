package com.verity.webreport;

public class TestCaseReport {
	String id;
	String description;
	String status;
	String screenshot;
	String error;
	
	public TestCaseReport(){
		id = "N/A";
		description = "N/A";
		status = "N/A";
		screenshot = "N/A";
		error = "N/A";
	}
	
	public TestCaseReport(String id, String description, String status,String screenshot, String error){
		this.id = id;
		this.description = description;
		this.status = status;
		this.screenshot = screenshot;
		this.error = error;
	}
	
	public TestCaseReport(String id, String description, String status){
		this.id = id;
		this.description = description;
		this.status = status;
		screenshot = "N/A";
		error = "N/A";
	}
	
	public String getID(){
		return id;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getStatus(){
		return status;
	}
	
	public String getScreenshot(){
		return screenshot;
	}
	
	public String getError(){
		return error;
	}
}
