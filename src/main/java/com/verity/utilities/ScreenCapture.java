/**
 * Framework -QA CoE Test Framework
 * Version - 0.1
 * Creation Date - Feb, 2013
 * Author - Ramesh Tejavath
 * Description: Capture Screenshot
 *  **/
package com.verity.utilities;


import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
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
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;





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
	
	public void CaptureScreen (String sFileName) throws Exception
	{
		BufferedImage PrtScn = null;
		Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Robot robot = new Robot();
		
		// captures whole screen
		Rectangle rect = new Rectangle(0,0, ScreenSize.width, ScreenSize.height);
		PrtScn = robot.createScreenCapture(rect);
		
		
		FileOutputStream out = new FileOutputStream(sFileName);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(PrtScn);
		out.flush();
		out.close();
		
		// or
		//File myScreenshot = new File(sFileName);
	    //ImageIO.write(PrtScn, "jpeg", myScreenshot);
		
	}
	
	/* uses webdriver methods..
	// Captures only Browser Child..no addressbar or window title..	
	public void CaptureScreen_WD (String sFileName)
	{
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		try {
	//	String browser =ReadPropertyFile.getConfigPropertyVal("BrowserType");
	//	driver=new Augmenter().augment(driver);
	//	WebDriver augmentedDriver = new Augmenter().augment(AutomationCommon.driver);
       // File screenshot = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
		File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
        
			org.apache.commons.io.FileUtils.copyFile(screenshot, new File(sFileName));
			//Reporter.log("<a href=" + path + " target='_blank' >" + this.getFileName(nameScreenshot) + "</a>");
		} catch (IOException e) {
			
			e.printStackTrace();
		}	
	}*/
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

