package com.verity.utilities;
/**
 * Framework -QA CoE Test Framework
 * Version - 0.1
 * Creation Date - Feb, 2013
 * Author - Ramesh Tejavath
 * Description: This is a common methods used for Web  apps including Opening Browser, Quit
 *  **/


import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.internal.TestResult;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.io.File;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.verity.reporting.ReportHandler;
import com.verity.reporting.ResultsToDB;


public class WebAccelerator {
  	public  WebDriver driver;
  	public  Actions builder;
  	public  ReadPropertyFile ReadPropertyFile;
  	public  WebDriverWait wait;
	public  ReportHandler log;
	public  String OSName=System.getProperty("os.name");
	public  boolean bExeResult=false;
	public  Logger logger= Logger.getLogger(WebAccelerator.class);	
	public  static Set<String> windowHandles;
	public  static String rootWindow = null;
	//public  Selenium selenium;
	
	private static int pass = 0;
	private static int fail = 0;
	private static String startTime;
	private static String endTime;
	
	
	//@Parameters({"remoteBrowserType"})
	//@BeforeClass(alwaysRun=true)
	@SuppressWarnings("static-access")
	public WebDriver openBrowser(String remoteBrowserType) throws Exception
	{		
		ReadPropertyFile =new ReadPropertyFile();
		DesiredCapabilities Capabilities = new DesiredCapabilities();
		String browser =ReadPropertyFile.getConfigPropertyVal("BrowserType");
		if(OSName.toLowerCase().contains("windows")){
			File IEfile = new File(".\\drivers\\IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", IEfile.getAbsolutePath());		
			File chromedriver = new File(".\\drivers\\chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", chromedriver.getAbsolutePath());
			
			logger.info("Browser = " + browser);
			
			if (browser.equalsIgnoreCase("FireFox")) {
	       	 driver = new FirefoxDriver();
	       	// driver.manage().deleteAllCookies();
			}
			else if (browser.equalsIgnoreCase("Safari")) {
				 //Assert.assertTrue(isSupportedPlatform());
		       	 driver = new SafariDriver();
		       	 //driver.manage().deleteAllCookies();
				}
			else if(browser.equalsIgnoreCase("IE")) {	
	      	    Capabilities = DesiredCapabilities.internetExplorer();
	      	    Capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	      	    driver = new InternetExplorerDriver(Capabilities);      	    
			} 
			else if(browser.equalsIgnoreCase("Chrome")) {
	       		Capabilities = DesiredCapabilities.chrome();
	       		Capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
	       		driver = new ChromeDriver(Capabilities);       	
			}
			else if(browser.equalsIgnoreCase("Remote")) {
				logger.info("Browser is=" + remoteBrowserType );
				
				if (remoteBrowserType.equalsIgnoreCase("FireFox"))
					driver= new RemoteWebDriver(DesiredCapabilities.firefox());			
	          	else if(remoteBrowserType.equalsIgnoreCase("IE")){
	          		Capabilities = DesiredCapabilities.internetExplorer();
	          		Capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	          		driver= new RemoteWebDriver(Capabilities);
	          	}
	         	else if(remoteBrowserType.equalsIgnoreCase("Chrome")) 
	      	    	driver= new RemoteWebDriver(DesiredCapabilities.chrome());
				//driver = new Augmenter().augment(driver);  
				else if(remoteBrowserType.equalsIgnoreCase("Safari")) 
					//Assert.assertTrue(isSupportedPlatform());
	      	    	driver= new RemoteWebDriver(DesiredCapabilities.safari());
				driver = new Augmenter().augment(driver);  
			}
		}else if(OSName.toLowerCase().contains("mac")){
			if(browser.equalsIgnoreCase("Remote")){ 
				//Capabilities = DesiredCapabilities.safari();
				driver = new RemoteWebDriver(DesiredCapabilities.safari());
			}else if(browser.equalsIgnoreCase("Chrome")) {
				File chromedriver = new File("./drivers/chromedriver");
				System.setProperty("webdriver.chrome.driver", chromedriver.getAbsolutePath());
	       		Capabilities = DesiredCapabilities.chrome();
	       		Capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
	       		driver = new ChromeDriver(Capabilities);       	
			}
			else{
				Capabilities = DesiredCapabilities.safari();
				driver = new SafariDriver(Capabilities);
			}
				
		}
        
		driver.manage().deleteAllCookies();    
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
        driver.manage().window().maximize();   
        builder=new Actions(driver);
      //  selenium = new WebDriverBackedSelenium(driver, ReadPropertyFile.getConfigPropertyVal("URL"));
        logger.info("Executed Before Class Method Successfully");
        
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		System.out.println("version: " + caps.getVersion());
		String browserName=caps.getBrowserName();
        String browserVersion=caps.getVersion();
        //String OStype=Capabilities.toString();
        logger.info("\n"+"Browser="+browserName+"\n"+"BrowserVersion="+browserVersion+"\n"+"OS="+System.getProperty("os.name")+"\n"+"OSVersion="+System.getProperty("os.version"));
//       driverScreenshot = new Augmenter().augment(driver);
        return driver;
	}
	
	public WebDriver getDriver(){
		return driver;
	}
	
//	@AfterClass(alwaysRun=true)
	public  void tearDown() {
	try{
		logger.info("Closing browser...");
		driver.quit();
		logger.info("Browser closed.");
	}
	catch(WebDriverException e){
		logger.info("Browser is already closed.");
	}
	rootWindow = null;
	windowHandles.clear();
	windowHandles = null;
	
	}
	
	public void navigateToSite(String remoteBrowserType) throws Exception{
		try {
			navigateURL();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			logger.info("URL Navigation");
	     }catch(Exception e){
		   logger.info("No browser found. NewBrowser Opening");
/*		   if(driver != null) {
			   driver.quit();
	        }*/		
		   openBrowser(remoteBrowserType);
		   navigateURL();
		   driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		   logger.info("URL Navigation");
	        }
	}
	
	public void getPage(String URL){
		if(rootWindow != null){
			driver.switchTo().window(rootWindow);
			driver.get(URL);
			logger.info("URL navigating to ="+URL);
		}
		else{
			driver.get(URL);
			logger.info("URL navigating to ="+URL);
			rootWindow = driver.getWindowHandle();
			windowHandles = driver.getWindowHandles();
		}
	}
	public void navigateURL(){
		getPage(getURL("URL"));	
	}
	
	public void navigateURL(String ConfigFileURL){
		String getURL=getURL(ConfigFileURL);
		getPage(getURL);	
	}
	
	/*public void navigateURL(String ConfigFileURL,String extendedUrl){
		String getURL=getURL(ConfigFileURL);
		String URL=getURL+extendedUrl;
		logger.info("URL is="+URL);
		driver.get(URL);	
	}*/
	
	public void navigateURL(String remoteBrowserType,String ConfigFileURL) throws Exception{
		String getURLfromConfig=getURL(ConfigFileURL);	
		logger.info("URL is="+getURLfromConfig);
		try {
			getPage(getURLfromConfig);	
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			logger.info("URL Navigation");
	     }catch(Exception e){
		   logger.info("No browser found. NewBrowser Opening");
/*		   if(driver != null) {
			   driver.quit();
	        }*/		
		   openBrowser(remoteBrowserType);
		   getPage(getURLfromConfig);	
		   driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		   logger.info("URL Navigation");
	        }
		
		
	}
	
	public String getURL(String ConfigURL){
		//if (appUrl == null) {
		String appUrl=null;
			try {
		       appUrl =ReadPropertyFile.getConfigPropertyVal(ConfigURL); //ReadPropertyFile.ReadFile(PropertyFilePath.ConfigPathLocation, URL);
		       logger.info("URL = " + appUrl);
		       //driver.get(appUrl);
			}catch (Exception ex) {
				appUrl = "www.google.com";
				logger.info("URL not found in COnfig.properties file. So opening default site = " + appUrl);
			}
			//assert appUrl.contains("http");
	//	}
		return appUrl; 
	}
	
	
	/*public static boolean verifyText(String text) {
	    try {
	        WebElement Text = driver.findElement(By.xpath("//*[contains(text(),'" + text + "')]"));
	        return Text.isDisplayed();
	    } catch(NoSuchElementException nsee) {
	    }
	    return false;
	}
	
	public void selectDropdownById(String element, String value)
	{
		Select droplist = new Select(driver.findElement(By.id(element)));
		droplist.selectByVisibleText(value);
	}
	
	public void selectDropdownByXpath(String element, String value)
	{
		Select droplist = new Select(driver.findElement(By.xpath(element)));
		droplist.selectByVisibleText(value);
	}
	
 	public void verifyTextPresent(String sValue)
	{
	    driver.getPageSource().contains(sValue);
	}
	
	

	private static boolean CheckErrorOnPage()
	{
		try
		{
			driver.findElement(By.xpath("/html/body/form/div/div"));
			WebElement ErrorMessage=driver.findElement(By.xpath("/html/body/form/div/div"));
			System.out.println("Error: " + ErrorMessage);
			 return true;	
		}
		catch (NoSuchElementException e)
		{
			return false;
		}
	}
	
	
	*/
	
	@BeforeSuite(alwaysRun=true)
	public void startRegressionSuite() throws SQLException{			
		//	ResultsToDB.WriteStartDateToCyclesTable();
			logger.info("Cycle Start Date inserted");			
	}
	
	@SuppressWarnings("static-access")
	@AfterSuite(alwaysRun=true)
	public void endRegressionSuite() throws SQLException{	
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		endTime = dateFormat.format(date);	
		logger.info("Starting AfterSuite");
	
		String message = "Total test cases ran: " + String.valueOf((pass+fail)) + "\n <br />";
		message += "Passed: " + String.valueOf(pass) + "\n <br />";
		message += "Failed: " + String.valueOf(fail) + "\n <br />";
		message += "Start time: " + startTime + "\n <br />";
		message += "End time: " + endTime + "\n <br />";
		message += "For more information please see the full report here: ";
		String reportLink = "<a href=\"http://10.107.133.49:8080/ReportServlet/report?project=" + ReadPropertyFile.getConfigPropertyVal("Application")
				+ "&cycle=" + ReadPropertyFile.getConfigPropertyVal("Cycle") + "&display=all\">Full Report</a> <br />";
		message += reportLink + "\n <br /> \n <br />";
		message += "This is email is generated automatically, please do not reply. \n <br />";
	//	ResultsToDB.WriteEndDateToCyclesTable();
		logger.info("Cycle End Date inserted");		
	//	SendEmails smtp = new SendEmails();
	//	try {
	//		smtp.send(message);
	//	} catch (Exception e) {
	//		e.printStackTrace();
	//	}
	}
	
	public void closeBrowser()
	{
		driver.quit();
	}
	
	
	 
	 public void postResults( ITestResult it) throws SQLException{
			logger.info("Test description: " + it.getMethod().getDescription());
			logger.info("getMethod name:" +it.getMethod());
			logger.info("getName name:" +it.getName()); //tcID
			logger.info("getTestClass name:" +it.getTestClass()); //null
			logger.info("getThrow name:" +it.getThrowable());
			String TestCaseID = it.getName(); 
			String HostID=System.getenv().get("COMPUTERNAME");
			logger.info(HostID);
			ReadPropertyFile =new ReadPropertyFile();
			String reportResultsToDB =ReadPropertyFile.getConfigPropertyVal("ResultsToDB");
			String className = it.getTestClass().toString().replace("[TestClass name=class", "");
			className = className.replace("]", "");
			
			if((pass+fail) == 0){
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				Date date = new Date();
				startTime = dateFormat.format(date);	
				logger.info("Start time: " + startTime);
			}
			
			if (it.isSuccess()){
				pass++;
				try {	
					logger.info("Pass");
					if(reportResultsToDB.equalsIgnoreCase("Yes")){	
						ResultsToDB.WriteToResultsTable(TestCaseID,it.getMethod().getDescription(), "Pass",HostID, "NULL", "NULL", className.trim());
					} else {
						logger.info("Results are not posted to DB because ResultsToDB=No. Please change to Yes for DB results");	
					}
						//	closeBrowser();
					}catch(Exception ex){
						System.out.print(ex.getMessage());
					}
						
			}else{
				fail++;
				try
				{
					if(it.getStatus() == ITestResult.SKIP){
						logger.info("Skipped");
						if(reportResultsToDB.equalsIgnoreCase("Yes")){	
							ResultsToDB.WriteToResultsTable(TestCaseID,it.getMethod().getDescription(), "Fail",HostID, "SKIPPED", "null", className.trim());
						} else {
						logger.info("Results are not posted to DB because ResultsToDB=No. Please change to Yes for DB results");	
						}
					}
					else{
						logger.info("Fail");
						ScreenCapture screenCapture=new ScreenCapture(driver);
						String imgPath = screenCapture.takeScreenShoot(it.getMethod());
						if(reportResultsToDB.equalsIgnoreCase("Yes")){	
							ResultsToDB.WriteToResultsTable(TestCaseID,it.getMethod().getDescription(), "Fail",HostID, it.getThrowable().getMessage().replace("'", "\""), imgPath, className.trim());
						} else {
						logger.info("Results are not posted to DB because ResultsToDB=No. Please change to Yes for DB results");	
						}
										
						logger.info("screenshot captured for: " +it.getMethod()+ " Failed TestCase");
					}
								
					
					//closeBrowser();
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}


				
		}	

	
	 

	 
/*jambur84: 05/22/2013 Method(s) created to navigate browser BACK/FORWARD */
	 
	 public void navigateBrowserback(){
		 driver.navigate().back();
	 }
	 
	 public void navigateBrowserFW(){
		 driver.navigate().forward();
	 }
	 

}
