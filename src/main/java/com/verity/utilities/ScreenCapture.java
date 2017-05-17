/**
 * Framework -QA CoE Test Framework
 * Version - 0.1
 * Creation Date - Feb, 2013
 * Author - Ramesh Tejavath
 * Description: Capture Screenshot
 *  **/
package com.verity.utilities;



import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestNGMethod;
import org.testng.Reporter;
import org.apache.log4j.Logger;






public class ScreenCapture {

	// captures whole screen including address bar
	// java methods..
	
	private WebDriver driver;
	private ReadPropertyFile propFile=new ReadPropertyFile();
	public  Logger logger= Logger.getLogger(ScreenCapture.class);
	//private static final String SOLUTIONDELIVERY_AUTOMATION = "\\\\04vfile002\\adm\\Solution Delivery\\Automation\\TestExecution";
	private static final String SCREENSHOT_FOLDER = "\\\\001D8BA7ADM\\screenshots";
			
	
	public ScreenCapture(WebDriver driver){
		this.driver = driver;
	}
	

        public String  takeScreenShoot(ITestNGMethod testMethod) throws Exception {
        	String browser =ReadPropertyFile.getConfigPropertyVal("BrowserType");
        	File screenshot;
        /*	if(browser.equalsIgnoreCase("Remote")){
        		WebDriver augmentedDriver = new Augmenter().augment(driver);
        		screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
        		augmentedDriver.close();
        	}
        	else*/
        		screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    		//WebDriver augmentedDriver = new Augmenter().augment(driver);
        	//WebDriver driverScreenshot=new Augmenter().augment(driver);
        	
    		 //String nameScreenshot = testMethod.getXmlTest().getParameter("browser").toUpperCase() + "_" + testMethod.getTestClass().getRealClass().getSimpleName() + "_" + testMethod.getMethodName();
    		String nameScreenshot = testMethod.getTestClass().getRealClass().getSimpleName() + "_" + testMethod.getMethodName();
    	    logger.info("Screen Name:=" + nameScreenshot);
    		String path = getPath(nameScreenshot);
             //String imgPath="/Mobile/screenshots" + getFileName(nameScreenshot) ;
             //System.out.println("Path:= " + imgPath);
    		 FileUtils.copyFile(screenshot, new File(path));
    		//Reporter.setEscapeHtml(true);
    		 System.setProperty("org.uncommons.reportng.escape-output", "false");
    		 Reporter.log(testMethod.getMethodName() + " Failed. Link to Screenshot: ");
    		// Reporter.log("screenshot for failed Test Case: " + nameScreenshot +"<div style=\"height:400px; width: 750px; overflow:scroll\"><img src=\""+getFileName(nameScreenshot)+"\"></div>",true);
    		 logger.info(("<a href= " + path + " target='_blank' >" + getFileName(nameScreenshot) + "</a>"));
    		 //String htmlPath = path.replace(" ", "%20");
    		 Reporter.log("<a href=\""+ path +"\">" + getFileName(nameScreenshot) + "</a>");
    	 	 //System.out.println("html path: " + htmlPath);
    	 	 Reporter.log("<br>");   
    	 	 
    	 	//currently use this for the report servlet
    	 	 String serverPath = path.replaceAll("\\\\", "/");
    	 	 //System.out.println("server: " + serverPath);
    	 	 serverPath = serverPath.substring(13); //replace machine name with "" is not working
    	 	 										//using this as a patch
    	 	//System.out.println("server: " + serverPath);
    	 	 //return path;
    	 	 return serverPath; 
        }


    	 private String getFileName(String nametest) throws IOException {
     
    		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_hh.mm.ss");

    		 Date date = new Date();
     
    		return dateFormat.format(date) + "_" + nametest + ".jpg";

    	 }
 
    	private String getPath(String nameTest) throws IOException {
    		String app =SCREENSHOT_FOLDER + "\\" + propFile.getConfigPropertyVal("Application"); 
    		String cycle = app + "\\" +propFile.getConfigPropertyVal("Cycle");
    		
    		new File(app).mkdirs();
    		new File(cycle).mkdirs();

    		File directory = new File(cycle);
     
    		String newFileNamePath = directory.getCanonicalPath() + "\\screenshots\\" + getFileName(nameTest);
    		//System.out.println ("Current directory's canonical path: " + directory.getCanonicalPath());
    		return newFileNamePath;
     
    	}

    	
   	 public static String encodeImage(byte[] imageByArray){
		 return Base64.encodeBase64URLSafeString(imageByArray);
	 }
	 
	 public static byte[]decodeImage(String imageDataString){
		 return Base64.decodeBase64(imageDataString);
	 }
	
	

}

