package com.verity.sampletests;



import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.verity.utilities.CommonFunctions;
import com.verity.utilities.WebAccelerator;


public class SampleTest {

	//public static WebDriver driver;
	
	private WebDriver driver;
	private CommonFunctions CF;
	private WebAccelerator accelerator;
	
	
	@BeforeClass(alwaysRun=true)
	public void setup() throws Exception {
		accelerator = new WebAccelerator();
		driver = accelerator.openBrowser();
		CF = new CommonFunctions(driver);
		//accelerator.navigateToSite(remoteBrowserType);
	}
	

	@BeforeMethod(alwaysRun=true)
	public void navigate() throws Exception {
		accelerator.navigateToSite();
		driver = accelerator.getDriver();
		CF.setDriver(driver);
	}
	
	@AfterMethod(alwaysRun=true) 
	public void after(ITestResult it) throws Exception{
		accelerator.postResults(it);
	    driver.manage().deleteAllCookies(); 
		accelerator.tearDown();
	}	
	
	@Test
	public void testingGrid(){
		System.out.println("just testing");
	
	}	
	

}
