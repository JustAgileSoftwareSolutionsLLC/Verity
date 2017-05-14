package com.verity.utilities;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{
		/*private int retryCount = 0;
		private int maxRetryCount = 3;

	public boolean retry(ITestResult result) {
	
	if(retryCount < maxRetryCount) {
		retryCount++; return true;
		}
	return false; }*/
	
	private int count = 0; 
	// this number is actually twice the number
	// of retry attempts will allow due to the retry
	// method being called twice for each retry
	private int maxCount = 6; 
	protected Logger log;
	private static Logger testbaseLog;

	static {
	    PropertyConfigurator.configure("./log4j.properties");
	    testbaseLog = Logger.getLogger("testbase.testng");
	}

	public void RetryAnalyzer()
	{
	    testbaseLog.trace( " ModeledRetryAnalyzer constructor " + this.getClass().getName() );
	    log = Logger.getLogger("transcript.test");
	}

	public boolean retry(ITestResult result) { 
	    testbaseLog.trace("running retry logic for  '" 
	            + result.getName() 
	            + "' on class " + this.getClass().getName() );
	        if(count < maxCount) {                     
	                count++;
	                result.setStatus(ITestResult.SKIP);
	                return true; 
	        } 
	        return false; 
	}
	
	
} 

