package com.verity.sampletests;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.internal.Locatable;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.verity.utilities.CommonFunctions;
import com.verity.utilities.WebAccelerator;


public class SampleTest {

	//public static WebDriver driver;
	
	private WebDriver driver;
	private CommonFunctions CF;
	private WebAccelerator accelerator;
	
	@Parameters({ "remoteBrowserType"})
	@BeforeClass(alwaysRun=true)
	public void setup(String remoteBrowserType) throws Exception {
		accelerator = new WebAccelerator();
		driver = accelerator.openBrowser(remoteBrowserType);
		CF = new CommonFunctions(driver);
		//accelerator.navigateToSite(remoteBrowserType);
	}
	
	@Parameters({ "remoteBrowserType"})
	@BeforeMethod(alwaysRun=true)
	public void navigate(String remoteBrowserType) throws Exception {
		accelerator.navigateToSite(remoteBrowserType);
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
	
	
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		
//		File chromeDriver = new File("C:\\Selenium\\drivers\\chromedriver.exe");
//		System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
//		driver = new ChromeDriver();
		//System.out.println(convertDateString("M/d/yyyy", "MM/dd/yyyy", "6/28/2013"));


	}
	


}
