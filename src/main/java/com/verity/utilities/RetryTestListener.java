package com.verity.utilities;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class RetryTestListener extends TestListenerAdapter  {
private int count = 0; 
//private int maxCount = 3; 

@Override
public void onTestFailure(ITestResult result) {     
    Logger log = Logger.getLogger("transcript.test");
    Reporter.setCurrentTestResult(result);

    if(result.getMethod().getRetryAnalyzer().retry(result)) {    
        count++;
        result.setStatus(ITestResult.SKIP);
        log.warn("Error in " + result.getName() + " with status " 
                + result.getStatus()+ " Retrying " + count + " of 3 times");
        log.info("Setting test run attempt status to Skipped");                 
    } 
    else
    {
        count = 0;
        log.error("Retry limit exceeded for " + result.getName());
    }       

    Reporter.setCurrentTestResult(null);
}

@Override
public void onTestSuccess(ITestResult result)
{
    count = 0;
}

}
