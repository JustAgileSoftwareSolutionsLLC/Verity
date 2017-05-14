package com.verity.webreport;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class CycleSummary {
	private String name;
	private float passRate;
	private int passed;
	private int failed;
	private int total;
	private String lastRun;
	private String startDate;
	
	public CycleSummary(){
		name = "N/A";
		passed = 0;
		failed = 0;
		total = 0;
		passRate = 0;
		lastRun = "N/A";
		startDate = "N/A";
	}
	
	public CycleSummary(String name, int passed, int failed, String lastRun, String startDate){
		this.startDate = startDate;
		this.lastRun = lastRun;
		this.name = name;
		this.passed = passed;
		this.failed = failed;
		total = passed+failed;
		if(passed == 0)
			passRate = 0;
		else if(failed == 0)
			passRate = 100;
		else
			passRate = ((float)passed/(float)total) * 100 ;	
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean setPassed(int passed){
		if(passed >=0){
			this.passed = passed;
			total = passed + failed;
			passRate = ((float)passed/(float)total)*100;
			return true;
		}
		return false;
	}
	
	public int getPassed(){
		return passed;
	}
	
	public boolean setFailed(int failed){
		if(passed >=0){
			this.failed = failed;
			total = passed + failed;
			passRate = ((float)passed/(float)total)*100;
			return true;
		}
		return false;
	}
	
	public int getFailed(){
		return failed;
	}
	
	public int getTotal(){
		return total;
	}
	
	public float getPassRate(){
	    BigDecimal bd = new BigDecimal(Float.toString(passRate));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
		
	}
	
	public String getFormatedPassRate(){
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(passRate);
	}
	
	public String getLastRunDate(){ 
		return lastRun;
	}
	
	public String getStartDate(){
		return startDate;
	}
}
