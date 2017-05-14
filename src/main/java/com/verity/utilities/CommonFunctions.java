package com.verity.utilities;

/**
 * Framework -QA CoE Test Framework
 * Version - 0.1
 * Creation Date - Feb, 2013
 * Author - Ramesh Tejavath
 * Description: This is a common methods used for Web and desktop apps
 *  **/


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;


import javax.imageio.ImageIO;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;




public class CommonFunctions {	

	//static Logger log = Logger.getLogger(CommonFunctions.class.getName());
	public static Logger logger= Logger.getLogger(CommonFunctions.class);
	private WebDriver driver;
	//public  Selenium selenium;
	public  Long timeout;
  	private  Actions builder;
  	
  	
	public CommonFunctions(WebDriver driver){
		this.driver = driver;
		 builder=new Actions(driver);
	}
	
	public void setDriver(WebDriver driver){
		this.driver = driver;
		builder=new Actions(this.driver);
	}
	
	public void waitForDriver(long enterSeconds){
		driver.manage().timeouts().implicitlyWait(enterSeconds, TimeUnit.SECONDS);
		logger.info("Driver waiting for: "+ enterSeconds);
	}
	
	static char specialCharacters[] = { '!', '@', '#', '$', '%', '^', '&', '*',
		'(', ')', '?', '/', '"', '|', '{', '[', '<', '>', ';', '`', ',',
		'_', '-' };

	/**
	 * Retrieve popup text message.
	 * 
	 * @param WebDriver
	 *            driver
	 * @return
	 */
	public String getPopupMessage() {
		String message = null;
		try {
			Alert alert = driver.switchTo().alert();
			message = alert.getText();
			alert.accept();
		} catch (Exception e) {
			// Sometimes the text exist, but not the accept button.
			// this means the popup wasn't displayed and therefore
			// really never existed.
			//
			message = null;
		}
		logger.info("message"+message);
		return message;
	}

	public String cancelPopupMessageBox() {
		String message = null;
		try {
			Alert alert = driver.switchTo().alert();
			message = alert.getText();
			logger.info("Alert Window: "+message);
			alert.dismiss();
		} catch (Exception e) {
			// Sometimes the text exist, but not the accept button.
			// this means the popup wasn't displayed and therefore
			// really never existed.
			//
			message = null;
		}

		return message;
	}

	
//	public boolean isTextPresent(String text){
//        try{
//        	boolean b = driver.getPageSource().contains(text);            
//            return b;
//        }
//        catch(Exception e){
//            return false;
//        }
//    }
	
	public void acceptPopup() {
		try {
			Alert alert = driver.switchTo().alert();
			//Thread.sleep(10000);
			alert.accept();
			driver.switchTo().defaultContent();
			logger.info("Alert Accepted");
		} catch (Exception e) {
			// Sometimes the text exist, but not the accept button.
			logger.info("Alert not found");
			//		
		}
	
		}
	
//	public boolean verifyPageTitle(String title) {
//		boolean match=false;
//		try {
//			Thread.sleep(2000);
//		   //String pageTitle= driver.findElement(By.xpath("//*[@id='HTMLTITLE']")).getText();
//			//getTitle();
//		   if (title.equalsIgnoreCase(getTitle()))
//		   {
//			   //Assert.assertEquals(getTitle(),title);
//			   logger.info(title+ " title verified");
//			   match=true;			   
//		   } else 			   
//				   match=false;
//				   logger.info("Title not verified");
//				   logger.info("Expected title:" + title);
//				   logger.info("Actual title:" + getTitle());				   
//		   
//		} catch (Exception e) {
//			logger.info(e.getMessage());			
//		}
//		return match;
//	}
	
	public String getTitle(){
		 String pageTitle= driver.getTitle();
		 logger.info(pageTitle+ " title found");
		 return pageTitle;
	}
	
	public void cancelPopup() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (Exception e) {
			// Sometimes the text exist, but not the accept button.
			// this means the popup wasn't displayed and therefore
			// really never existed.
			//
		}	
	}
	
	public void mouseOvertoElement(WebElement slocator) {
	logger.info(driver.getTitle());
	 waitUntilElementPresent(slocator);
	 String sElement=slocator.getText();	
	 builder.moveToElement(slocator).build().perform();
	 logger.info("Move mouse to \"" + sElement + "\"");
	}
	
	public boolean waitUntilElementPresent(WebElement slocator) {
		boolean bResult = false;
		Long maxWait = timeout;
		int secsWaited = 0;
		try {
			do {
				Thread.sleep(100);
				secsWaited++;
				if (isElementPresent(slocator)) {
					logger.info("Element found, wait time is = "+ secsWaited );
					bResult = true;
					break;
				}
			} while (secsWaited < (maxWait * 10));
			Thread.sleep(100);
		} catch (Exception e) {
			logger.info("Exception caught while waiting for the page to load ");
			bResult = false;
		}
		return bResult;
	}
	
	

	
	  public void waitForElementPresent(By locator, long timeout){
	       driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	       try{
	       driver.findElement(locator);
	       } catch (NoSuchElementException e){
	      System.err.print(e.getMessage());
	     }
	     }
	


	/**
	 * set the values x
	 * 
	 * @return
	 */
	public void populateField(WebDriver driver, By locator, String value) {
		WebElement field = driver.findElement(locator);
		field.clear();
		field.sendKeys(value);

	}

	/**
	 * Check hover message text
	 * 
	 * @param driver
	 * @param by
	 * 
	 * @return string
	 */
	public String checkHoverMessage(WebElement slocator){
		String tooltip = slocator.getAttribute("title");
		return tooltip;
	}

	/**
	 * Select radio button
	 * 
	 * @param driver
	 * @param by
	 * @param value
	 * 
	 */
	public void selectRadioButton(By locator, String value){
		List<WebElement> select = driver.findElements(locator);

		for (WebElement radio : select){
			if (radio.getAttribute("value").equalsIgnoreCase(value)){
				radio.click(); 	            	

			}}}

	/**
	 * Select multiple check boxes
	 * 
	 * @param driver
	 * @param by
	 * @param value
	 * 
	 */
	public void selectCheckboxes(By locator, String value){

		List<WebElement> abc = driver.findElements(locator);
		List<String> list = new ArrayList<String>(Arrays.asList(value.split(",")));

		for (String check : list){
			for (WebElement chk : abc){        	
				if(chk.getAttribute("value").equalsIgnoreCase(check)){	        	
					chk.click();	    	            	
				}      		        		            	
			}
		}
	}


	/**
	 * Select drop down
	 * 
	 * @param driver
	 * @param by
	 * @param value
	 * 
	 */
	
	public void selectDropdown(WebElement slocator, String value){
		List<WebElement> getDropDownValues=slocator.findElements(By.tagName("option"));
		boolean match = false;
		logger.info("Total no. of dropdown values:"+ getDropDownValues.size());
		for(int i = 0; i< getDropDownValues.size();i++){
			logger.info(getDropDownValues.get(i).getText());
			 if (getDropDownValues.get(i).getText().equalsIgnoreCase(value)){
				 getDropDownValues.get(i).click();
				 match = true;
			     break;}

		}
		if(!match){
			logger.info("No Selection Found");
			Assert.fail(value + "Not found in the dropdown " + slocator);
			 
		}
	  }
	
	public void selectDropDownByValue(WebElement locator, String value){
		try{
			Select select = new Select(locator);
			select.selectByValue(value);
			logger.info("[" + value +"] selected");
		}
		catch(Exception e){
			logger.info("[" + value +"] not found");
			Assert.fail("[" + value +"] not found");
		}
	}
	
	public void selectDropDownByText(WebElement locator, String text){
		try{
			Select select = new Select(locator);
			select.selectByVisibleText(text);
			logger.info("[" + text +"] selected");
		}
		catch(Exception e){
			logger.info("[" + text +"] not found");
			Assert.fail("[" + text +"] not found");
		}
	}
	
	
	/**
	 * Select drop down by default index1
	 * 
	 * @param driver
	 * @param by
	 * @param value
	 * 
	 */
	
	public void selectDropdownByIndex(WebElement slocator,int Index){
		try{
		Select sel = new Select(slocator);
		sel.selectByIndex(Index);
		logger.info(sel.getFirstSelectedOption().getText()+ " value selected");
		}catch(Exception e)
		{
			logger.info("index " + Index + " not found");
			Assert.fail("index " + Index + " not found");
		}
	  }

	/**
	 * Select auto-suggest search drop down
	 * 
	 * @param driver
	 * @param by
	 * @param value
	 * 
	 */
	public void selectSearchDropdown(WebElement slocator, String value){
		slocator.click();
		slocator.sendKeys(value);
		slocator.sendKeys(Keys.TAB);		 
	}


	/**
	 * get image count
	 * 
	 **/
	public int getImageCount(WebElement slocator){
		List<WebElement> getimgCount=slocator.findElements(By.tagName("img"));
		logger.info(getimgCount.size());
	    return getimgCount.size();
	 }
	
	/* * @param driver
	 * @param by
	 * @param value
	 * 
	 */
	public void uploadFile(By locator, String value){
		driver.findElement(locator).sendKeys(value);		  
	}


	/**
	 * Takes controls on new tab
	 * 
	 * @param driver
	 * 
	 */
	public void handleNewWindow(String windowTitle)
	{
        boolean found = false;
        String currentWindow = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        Iterator<String> i = handles.iterator();
        while(i.hasNext()){
               String handle = i.next();
               driver.switchTo().window(handle);
               if(driver.getTitle().equals(windowTitle)){
                     found = true;
                     logger.info("Window with title " + windowTitle + " found and Swiched");
                     break;
               }
        }
        if(!found){
               //go back to the parent window if not found
               driver.switchTo().window(currentWindow);
               logger.info("Window with title " + windowTitle + " not found");
        }

	}

	
	public void closeAndSwitchDefault(){
		for(String handle : driver.getWindowHandles()){
			if(!handle.equalsIgnoreCase(WebAccelerator.rootWindow)){
				driver.switchTo().window(handle);
				driver.close();
			}
		}
		driver.switchTo().window(WebAccelerator.rootWindow);
		WebAccelerator.windowHandles = driver.getWindowHandles();
		logger.info("root window is:" + WebAccelerator.rootWindow);
	}
	
	public void handleNativeWindow(){
		try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            logger.info("Native Window accepted");
     } catch (AWTException e) {
            e.printStackTrace();
     }

	
	}

	
	public void handleNewTab()
	{
		
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);
		}
	//	Set<String> allWindowHandles = driver.getWindowHandles();		
		//String window0 = (String) allWindowHandles.toArray()[1];
		//driver.switchTo().window(window0);
		
//		for(String handle : driver.getWindowHandles()){
//			if(!handle.equalsIgnoreCase(rootWindow)){
//				driver.switchTo().window(handle);
//				//driver.close();
//			}
//		}
	//	driver.switchTo().window(rootWindow);
		//windowHandles = driver.getWindowHandles();
		logger.info("current window handle is: " + driver.getWindowHandle());
		 
	}
	

	 /*******************************************************************************
	  * 				Verify Methods
	  *******************************************************************************/
	 public void verifyText(WebElement slocator, String ExpectedText) {
			String ActualText=slocator.getText().trim();
			ExpectedText=ExpectedText.trim();
			//logger.info(ActualText + "\n");
			//logger.info(ExpectedText + "\n");
		if (ExpectedText.equalsIgnoreCase(ActualText)){
			 logger.info("On page " + driver.getTitle() + ". Expected Text"+ ExpectedText +"Verified");
			}
		else{
			logger.info("On page " + driver.getTitle() + ". Expected Text="+ ExpectedText +"Not Found, instead found="+ActualText);
			Assert.fail("On page " + driver.getTitle() + ". Expected Text="+ ExpectedText +"Not Found, instead found="+ActualText);}
}	
	 
	 public void verifyText(String expected){
		 try{
			 driver.findElement(By.xpath("//*[contains(text(),'"+ expected.trim() +"')]"));
			 logger.info("On page " + driver.getTitle() + ". Expected Text \""+ expected +"\" verified");
			// return true;
		 }
		 catch(NoSuchElementException e){
			 logger.info("On page " + driver.getTitle() + ". Expected Text \""+ expected +"\" not found");
			 Assert.fail("On page " + driver.getTitle() + ". Expected Text \""+ expected +"\" not found");
		 }
		 
	 }
	 	 
	 
	 public boolean verifyTextByBoolean(String expected){
		 try{
			 driver.findElement(By.xpath("//*[contains(text(),'"+ expected.trim() +"')]"));
			 logger.info("On page " + driver.getTitle() + ". Expected Text \""+ expected +"\" verified");
			 return true;
		 }
		 catch(NoSuchElementException e){
			 logger.info("On page " + driver.getTitle() + ". Expected Text \""+ expected +"\" not found");
			 return false;
		 }
		 
	 }
	 
	 public void verifyTitle(String expected) throws InterruptedException{
		boolean found = false;
		int count = 0;
		while (count < 30){
			if(driver.getTitle().equalsIgnoreCase(expected)){
				logger.info("On page " + driver.getTitle() + ". Expected Text \""+ expected +"\" verified");
				found = true;
				break;
			}
			count++;
			Thread.sleep(1000);			
		}
		
		if(!found){		 
			 logger.info("On page " + driver.getTitle() + ". Expected Text \""+ expected +"\" not found");
			 Assert.fail("On page " + driver.getTitle() + ". Expected Text \""+ expected +"\" not found");
		 }
		 
	 }
	 
	 public void verifyDropDownDefaultValue(WebElement locator, String value){
		Select select = new Select(locator);
		String defaultVal = select.getFirstSelectedOption().getText();
		if(defaultVal.equalsIgnoreCase(value)){
			logger.info("Default value [" + value +"] verified");
			//return true;
		}
		else{
			logger.info("On page " + driver.getTitle() + ". Expected: [" + value +"]. Actual: [" + defaultVal + "]");
			Assert.fail("On page " + driver.getTitle() + ". Expected: [" + value +"]. Actual: [" + defaultVal + "]");
		}
	}
///**
//	 * Helper method: looks through a list of WebElements, to find the first WebElement with matching text
//	 * 
//	 * @param elements
//	 * @param text
//	 * 
//	 * @return WebElement or null
//	 */
//	public WebElement findElementByText(List<WebElement> elements, String text) {
//		WebElement result = null;
//		for (WebElement element : elements) {
//			element.getText().trim();
//			if (text.equalsIgnoreCase(element.getText().trim())) {
//				result = element;
//				break;
//			}
//		}
//		return result;
//	}



//	/**
//	 * Downloads a file from the defined url, and saves it into the
//	 * OutputDatafolder, using the filename defined
//	 * 
//	 * @param href
//	 * @param fileName
//	 */
//	public void downloadFile(String href, String fileName) throws Exception{
//
//		PropertyConfigurator.configure("config/log4j.properties");
//
//		URL url = null;
//		URLConnection con = null;
//		int i;
//
//		url = new URL(href);
//
//		con = url.openConnection();
//		File file = new File(".//OutputData//" + fileName);
//		BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
//
//		BufferedOutputStream bos = new BufferedOutputStream(
//				new FileOutputStream(file));
//		while ((i = bis.read()) != -1) {
//			bos.write(i);
//		}
//		bos.flush();
//		bis.close();
//		bos.close();
//
//	}
	
	/**
	 * Method to refresh the page
	 */
	public void refreshPage(){
		driver.navigate().refresh();	
	}


	/**
	 * Writes content to the excel sheet
	 * 
	 * @param text
	 * @param fileName
	 */
	public void writeExcel(String text,String fileName) throws Exception 
	{ 
		FileOutputStream file = new FileOutputStream(".//OutputData//" + fileName+".csv",true);
		WritableWorkbook book = Workbook.createWorkbook(file); 
		WritableSheet sheet = book.createSheet("output", 0);
		Label l = new Label(0, 0, text);
		sheet.addCell(l);
		book.write(); 
		book.close(); 
	}
	

	public void imageCompare(String ExpectedImgFile,String ActualImgFile){
		try{
			
			// driver.findElement(By.linkText(""+i+"")).click();
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

			//You can give the location where you want to keep the screen shot
			String fileName = "Screenshot "+" "+ getRandomString(10);
			FileUtils.copyFile(screenshot, new File(".//OutputData//"+fileName+".jpg")); 

			//Compare output screen shot images with sample images in InputTestData folder
			File fileA = new File(ExpectedImgFile);
			File fileB = new File(".//OutputData//"+fileName+".jpg");

			BufferedImage biA = ImageIO.read(fileA);
			DataBuffer dbA = biA.getData().getDataBuffer();
			int sizeA = dbA.getSize();                      
			BufferedImage biB = ImageIO.read(fileB);
			DataBuffer dbB = biB.getData().getDataBuffer();
			int sizeB = dbB.getSize();
			Boolean matchFlag = true;
			if(sizeA == sizeB) {
				for(int j=0; j<sizeA; j++) { 
					if(dbA.getElem(j) != dbB.getElem(j)) {
						matchFlag = false;
						break;
					}
				}
			}
			else 					
				matchFlag = false;

			if (matchFlag) 
				logger.info("image matched for "+fileName+" image");
			else 
				logger.error("image not matched for "+fileName+" image");	
			
		} catch (Exception e) { 
			logger.error("Failed to compare image files ...", e);
		
		}
		

	}
	
//	public List<String> getErrors() {
//		List<WebElement> elements = driver.findElements(By.className("errError"));
//		List<String> errors = new ArrayList<String>();;
//		if ( elements != null && elements.size() != 0 ) {
//			for(WebElement element : elements) {
//				errors.add(element.getText());
//			}
//		}
//		return errors;
//    }
	
	/**
	 * valides Full or Partial URLs
	 * 
	 * @param PageName
	 * @param URL to validate
	 */
	
    public void validatePageURL(String PageName,String ExpectedPageURL){
    	logger.info("Current URL="+driver.getCurrentUrl()+"\n"+"Expected url="+ExpectedPageURL);        
        if (driver.getCurrentUrl().contains(ExpectedPageURL)){                            
               logger.info(PageName+" Page URL verified - "+ ExpectedPageURL);
              // return true;  
        } else {
        
        logger.info("On"+PageName+"\t Page "+ ExpectedPageURL+" URL Not Found.");
      //  return false;
        Assert.fail("On"+PageName+"\t Page "+ ExpectedPageURL+" URL Not Found.");
        }

 }

	
	public void verifytDropdownValue(WebElement slocator, String[] value) {
		Select select = new Select(slocator); 
		//boolean match = false;
		List<WebElement> getDropDownValues=select.getOptions();
		logger.info(getDropDownValues.size());
		
//		for(WebElement we:getDropDownValues){
//		for(int i=0; i<value.length;i++){
//		     if (we.getText().equals(value[i])){
//		    //	 match=true;	
//		    	 logger.info(we.getText() + "DropDownValue Found");
//		    	
//		     }else {
//		    	 logger.info(we.getText() + "DropDownValue not Found");
//				 Assert.fail(we.getText() + "DropDownValue not Found");
//		     }
//			}
//		}
		
		for(int i = 0; i < getDropDownValues.size(); i++){
			if(getDropDownValues.get(i).getText().trim().equals(value[i].trim())){
				logger.info(getDropDownValues.get(i).getText() + "DropDownValue Found");
			}
			else {
		    	 logger.info(getDropDownValues.get(i).getText() + "DropDownValue not Found");
				 Assert.fail(getDropDownValues.get(i).getText() + "DropDownValue not Found");
		     }
		}
		
//
//		 if (match=false){
//			 logger.info("DropDownValues not Found");
//			 Assert.fail("DropDownValue not Found");
//		 }
		
	 }
	
	
	public void verifyDropdownValueNotExist(WebElement slocator, String[] value) {
		Select select = new Select(slocator); 
		List<WebElement> getDropDownValues=select.getOptions();
		logger.info(getDropDownValues.size());
		
		for(int i = 0; i< value.length; i++){
			if(getDropDownValues.contains(value[i].trim())){
				logger.info(value[i] + " is found in the drop down");
				Assert.fail(value[i] + " is found in the drop down");
			}
			else
				logger.info(value[i] + " is not found in the drop down");
				
		}
	 }

	public void verifyLink(WebElement slocator,String sLinkName){
	       boolean match = false;
	       List<WebElement> links = slocator.findElements(By.tagName("a"));  
			
				for(int i=0; i<links.size();i++){
			     if (links.get(i).getText().equals(sLinkName)){
			    	 match=true;
			    	 logger.info(links.get(i).getText()+ " Found");
			    	 break;
				  }
				}
		
				 if (!match){
					 logger.info(sLinkName + " not Found");
					 Assert.fail(sLinkName + " not Found");
				 }
	   }
	
	public void verifyLink(String sLinkName){
	       boolean match = false;
	       List<WebElement> links = driver.findElements(By.tagName("a"));  
			
				for(int i=0; i<links.size();i++){
			     if (links.get(i).getText().equals(sLinkName)){
			    	 match=true;
			    	 logger.info(links.get(i).getText()+ " Found");
			    	 break;
				  }
				}
		
				 if (!match){
					 logger.info(sLinkName + " not Found");
					 Assert.fail(sLinkName + " not Found");
				 }
	   }
	
//	public void verifytErrorMsg(String value){
//		boolean match = false;
//		List<WebElement> ErrMsg = driver.findElements(By.className("errError"));  
//		logger.info("Total errors are : "+ErrMsg.size());
//		for(int i=0; i<ErrMsg.size();i++){
//		     if (ErrMsg.get(i).getText().equals(value)){
//		   	 match=true;
//		   	 logger.info(ErrMsg.get(i).getText()+ " Found");
//		 	}
//		  }		
//		 if (!match){
//			 logger.info(value + " not Found");
//			 Assert.fail(value + " not Found");
//		 }
//	 }
	
//	public boolean verifyHeader(String sHeader){
//		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		waitForDriver(3);
//	       boolean match = false;
//	       List<WebElement> headers = driver.findElements(By.tagName("h1"));  
//	      logger.info("Total headers are : "+headers.size());
//	      	for(int i=0; i<headers.size();i++){
//	      		logger.info("Header names are="+headers.get(i).getText());
//			     if (headers.get(i).getText().equals(sHeader)){
//			    	 match=true;
//			    logger.info(headers.get(i).getText()+ " Found");
//				  }
//	          }		
//	      	waitForDriver(30);
//			return match;
//			
//		}
	

	public void readPDF() throws IOException {
        URL url = new URL(driver.getCurrentUrl());
        BufferedInputStream fileToParse=new BufferedInputStream(url.openStream());
        PDFParser parser = new PDFParser(fileToParse);
        parser.parse();
        String output=new PDFTextStripper().getText(parser.getPDDocument());
        logger.info(output);
        parser.getPDDocument().close();
        driver.close();
    }
	
	/*public String getTextFromPDF() throws IOException{
		URL url = new URL(driver.getCurrentUrl());
		Cookie authorizationCookie = getBrowser().manage().getCookieNamed("JSESSIONID") ;
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("Cookie", authorizationCookie.toString());
        InputStream fileToParse = conn.getInputStream();
        PDFParser parser = new PDFParser(fileToParse);
        parser.parse();
        String text = new PDFTextStripper().getText(parser.getPDDocument());
        logger.info(text);
        parser.getPDDocument().close();
        return text;
	}	*/
	
	
public String getTextFromPDF() throws IOException{
		URL url = new URL(driver.getCurrentUrl());
		BufferedInputStream fileToParse=new BufferedInputStream(url.openStream());
		PDFParser parser = new PDFParser(fileToParse);
		parser.parse();
		 String output=new PDFTextStripper().getText(parser.getPDDocument());
		  logger.info(output);
		  parser.getPDDocument().close(); 
		  driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		 return output;
	}	
	
//public boolean verifyTextFromPDF(String value) throws IOException{
//	String pdfText;
//	pdfText = getTextFromPDF();	
//	return pdfText.contentEquals(value);	
//
//}




	  public boolean isElementPresent(WebElement slocator) {
		    try {
		    	slocator.isDisplayed();
		    	logger.info(slocator+ " is displayed");
		        return true;
		    } catch (NoSuchElementException e) {
		    	logger.info(slocator+ " is not displayed");
		      return false;
		    }
		  }
		
		public boolean isElementNotPresent(WebElement slocator) {
		    try {
		    	slocator.isDisplayed();
		    	logger.info(slocator+ " is displayed");
		        return false;
		    } catch (NoSuchElementException e) {
		    	logger.info(slocator+ " is not displayed");
		      return true;
		    }
		  }
		
			
//		public  boolean click(By locator) {
//			try {
//				waitForElementPresent(locator,30);
//			   driver.findElement(locator).click();
//				return true;
//			} catch (Exception e) {
//				logger.info(e.getMessage());
//			}
//			return false;
//		}
		
		
		
		public  void verifyWebElement(WebElement slocator) {
			try {
				slocator.isDisplayed();
				logger.info( slocator + "Found ");			
			} catch (Exception e) {
				logger.info(e.getMessage());
				logger.info( slocator + "Not Found ");
				Assert.fail(slocator + "Not Found ");
			}		
		}
		
		public void click(WebElement slocator){
			try {
				
				String Element=slocator.getText();
				if ((Element.isEmpty()) || (Element==null)){
					Element=slocator.getAttribute("value");
				}
				logger.info(Element + " trying to click");
				waitUntilElementPresent(slocator);
				slocator.click();
				logger.info(Element + " clicked ");
				acceptPopup();
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.info(slocator + " not clicked ");
			}
		}
			
		public void check(WebElement locator){
			if(!locator.isSelected()){
				click(locator);
				logger.info("Checkbox checked");
			}	
		}
		
		public void uncheck(WebElement locator){
			if(locator.isSelected()){
				click(locator);
				logger.info("Checkbox unchecked");
			}	
		}
		
		public void check(WebElement locator, String option){
			if(!locator.isSelected() && option.equalsIgnoreCase("yes")){
				click(locator);
				logger.info("Checkbox was unchecked, now checked");
			}
			else if(locator.isSelected() && option.equalsIgnoreCase("no")){
				click(locator);
				logger.info("Checkbox was checked");
			}
			else
				logger.info("Invalid option for check/uncheck. Valid options are yes or no");
		}
		
		public void uncheck(WebElement locator, String option){
			if(locator.isSelected() && option.equalsIgnoreCase("yes")){
				click(locator);
				logger.info("Checkbox was checked, now unchecked");
			}
			else if(!locator.isSelected() && option.equalsIgnoreCase("no")){
				click(locator);
				logger.info("Checkbox was unchecked");
			}
			else
				logger.info("Invalid option for check/uncheck. Valid options are yes or no");
		}
		
//		  private static boolean isSupportedPlatform() {
//			    Platform current = Platform.getCurrent();
//			    return Platform.MAC.is(current) || Platform.WINDOWS.is(current);
//			  }
			
		public void setValue(WebElement slocator,String sValue){
			String Element=slocator.getText();
			try {	
				logger.info(Element + "trying to set the value");
				slocator.clear();
				slocator.sendKeys(sValue);
				logger.info(sValue + " entered");
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.info(Element + "field not found");
			}
		}
		
		public void setValue(WebElement slocator,Object value){
			String Element=slocator.getText();
			try {	
				logger.info(Element + "trying to set the value");
				slocator.clear();
				slocator.sendKeys(String.valueOf(value));
				logger.info(String.valueOf(value) + " entered");
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.info(Element + "field not found");
			}
		}		
	
	
	public void mouseOverAndClicktoElement(WebElement slocator) {
		 waitUntilElementPresent(slocator);
		 String sElement=slocator.getText();
		 builder.moveToElement(slocator).build().perform();
		 builder.click(slocator).build().perform();
		 acceptPopup();
		 logger.info("Move Mouse and Click \"" + sElement + "\"");
		 try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
	
	
	/********************************************************************************
	 * 				Methods to get current time stamp, dealing with dates			*
	 ********************************************************************************/
	public String getCurrentTimeStamp(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyyHHmmss");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	
	public String getCurrentTimeStamp(String format){
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);	
	}
	
	public String convertDateString(String currentFormat, String newFormat, String date) throws ParseException{
		SimpleDateFormat current = new SimpleDateFormat(currentFormat);
		SimpleDateFormat convert = new SimpleDateFormat(newFormat); 
		Date convertedDate = current.parse(date); 
		return convert.format(convertedDate);
	}
	
	/********************************************************************************
	 *                           Randomized Methods 								*
	 ********************************************************************************/
	public String randomSSN(){
		Random r = new Random();
		int number = r.nextInt(900000000) + 100000000;
		return Integer.toString(number);
	}
	
	private SecureRandom random = new SecureRandom();

	/**
	 * Generate random string of special characters of length x
	 * 
	 * @return
	 */
	public String getRandomSpecialString(int length) {
		int len = specialCharacters.length;
		String str = "";
		Random randomGenerator = new Random();
		int index;

		for (int i = 0; i < length; i++) {
			index = randomGenerator.nextInt(len - 1);
			str = str + specialCharacters[index];
		}
		return str;
	}

	/**
	 * Generate random string of length x
	 * 
	 * @return
	 */
	public String getRandomString(int length) {
		String result = new BigInteger(Long.SIZE * length, random).toString(32);
		return result.substring(0, length);
	}
	
	//year from 1940 to 1990
	public String randomDOB_MMDDYYYY() throws ParseException{
		int startYear = 1940;
		int endYear = 1990;
		int year = randBetween(startYear, endYear);
		int month = randBetween(0, 11);
		GregorianCalendar gc = new GregorianCalendar(year, month, 1);
		int day = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_MONTH));
		gc.set(year, month, day);
		
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		format.setCalendar(gc);
		return format.format(gc.getTime());
		//return Integer.toString(gc.get(gc.MONTH)) + "/" + Integer.toString(gc.get(gc.DAY_OF_MONTH)) + "/" + Integer.toString(gc.get(gc.YEAR));
		
	}
	
	public String randomDOB_MMDDYYYY(int startYear, int endYear) throws ParseException{
		int year = randBetween(startYear, endYear);
		int month = randBetween(0, 11);
		GregorianCalendar gc = new GregorianCalendar(year, month, 1);
		int day = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_MONTH));
		gc.set(year, month, day);
		
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		format.setCalendar(gc);
		return format.format(gc.getTime());
		//return Integer.toString(gc.get(gc.MONTH)) + "/" + Integer.toString(gc.get(gc.DAY_OF_MONTH)) + "/" + Integer.toString(gc.get(gc.YEAR));
		
	}
	
    public int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
    
    /****************************************************************************
     * 				Methods to kill current running processes					*
     ****************************************************************************/
    public static boolean isProcessRunning(String process) throws IOException{
    	Process p = Runtime.getRuntime().exec("tasklist");
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String s;
        while((s = reader.readLine()) != null){
               if(s.contains(process))
                     return true;
        }
        return false;
    }
 
    //kill without saving
    public static void killProcess(String process) throws IOException{
    	if(isProcessRunning(process))
    		Runtime.getRuntime().exec("taskkill /F /IM " + process);
    }
    
    /****************************************************************************
	 * 			Method to compare text on the page with a txt file			 	*
	 ****************************************************************************/
	 public boolean comparePageWithTxtFile(String xpathLocator, String fileLocation){
		 int upperBound = 0;
		 int lowerBound = 0;
		 boolean status = true;
		 List<String> txtFileLines = new ArrayList<String>();
		 String pageText = driver.findElement(By.xpath(xpathLocator)).getText();
		 String pageAdjusted = pageText.replaceAll("(?m)^[ \t]*\r?\n", "");
		 String pageLines[] = pageAdjusted.split("\\r?\\n");
		 
		 //read in txt file and add to an array list
		 try{
			FileInputStream fstream = new FileInputStream(fileLocation);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				  if(strLine.trim().length() > 0)
					  txtFileLines.add(strLine.trim());
			}
			in.close();
			 
			//still comparing if there are extra lines in either array, will set status = failed after printing out extra lines below
			if(pageLines.length < txtFileLines.size()){
				upperBound = txtFileLines.size();
				lowerBound = pageLines.length;
			}
			else if(pageLines.length > txtFileLines.size()){
				lowerBound = txtFileLines.size();
				upperBound = pageLines.length;
			}
			//or if they're the same, then it does'nt matter which bound we're using
			else{
				upperBound = txtFileLines.size();
				lowerBound = pageLines.length;
			}
				
			for(int i = 0; i < lowerBound; i++){
				if(!pageLines[i].trim().equals(txtFileLines.get(i).trim())){
					status = false;
					logger.info("Comparison FAILURE:");
					logger.info("Line from page: [" + pageLines[i].trim() + "]");
					logger.info("Line from file: [" + txtFileLines.get(i).trim() + "]");
				}
			}
			
			if(pageLines.length > txtFileLines.size()){
				logger.info("Comparison FAILURE, extra lines on the page:");
				for(int i = lowerBound; i < upperBound; i++)
					logger.info(pageLines[i]);
				status = false;
			}
			else if(pageLines.length < txtFileLines.size()){
				logger.info("Comparison FAILURE, extra lines in the file:");
				for(int i = lowerBound; i < upperBound; i++)
					logger.info(txtFileLines.get(i));
				status = false;
			}	
				
			if(status)
				logger.info("Comparison completed. PASS");
		} catch (Exception e) {
			e.printStackTrace();
		}	
		 
		return status;
	 }
	 
	 /****************************************************************************
	  * 			Method to compare text on the page with a doc file			 *
	  ****************************************************************************/
	 public boolean comparePageWithDocFile(String xpathLocator, String fileLocation){
		int upperBound = 0;
		int lowerBound = 0;
		boolean status = true;
		String pageText = driver.findElement(By.xpath(xpathLocator)).getText();
		String pageAdjusted = pageText.replaceAll("(?m)^[ \t]*\r?\n", "");
		String pageLines[] = pageAdjusted.split("\\r?\\n");
		
			 
		//read in txt file and add to an array list
		try{
			
			FileInputStream fstream = new FileInputStream(fileLocation);
			HWPFDocument document=new HWPFDocument(fstream);
			WordExtractor extractor = new WordExtractor(document);
			String fileText = extractor.getText();
			String fileAdjusted = fileText.replaceAll("(?m)^[ \t]*\r?\n", "");
			String docFileLines[] = fileAdjusted.split("\\r?\\n");; 
			
			//still comparing if there are extra lines in either array, will set status = failed after printing out extra lines below
			if(pageLines.length < docFileLines.length){
				upperBound = docFileLines.length;
				lowerBound = pageLines.length;
			}
			else if(pageLines.length > docFileLines.length){
				lowerBound = docFileLines.length;
				upperBound = pageLines.length;
			}
			//or if they're the same, then it does'nt matter which bound we're using
			else{
				upperBound = docFileLines.length;
				lowerBound = pageLines.length;
			}
						
			for(int i = 0; i < lowerBound; i++){
				if(!pageLines[i].trim().equals(docFileLines[i].trim())){
					status = false;
					logger.info("Comparison FAILURE:");
					logger.info("Line from page: [" + pageLines[i].trim() + "]");
					logger.info("Line from file: [" + docFileLines[i].trim() + "]");
				}
			}
				
			if(pageLines.length > docFileLines.length){
				logger.info("Comparison FAILURE, extra lines on the page:");
				for(int i = lowerBound; i < upperBound; i++)
					logger.info(pageLines[i]);
				status = false;
			}
			else if(pageLines.length < docFileLines.length){
				logger.info("Comparison FAILURE, extra lines in the file:");
				for(int i = lowerBound; i < upperBound; i++)
					logger.info(docFileLines[i]);
				status = false;
			}	
			
			if(status)
				logger.info("Comparison completed. PASS");
		} catch (Exception e) {
				e.printStackTrace();
		}	
		return status;
	 }
	 
	 /*******************************************************************************
	  * 				Methods to compare 2 lists of String	by order			*
	  *******************************************************************************/
	 public boolean compareStringArrayLists(List<String> expected, List<String> actual){
		 //return (expected.containsAll(actual) && actual.containsAll(expected));
		 
		 boolean status = true;
		 int upperBound = 0;
		 int lowerBound = 0;
		 try{
			//still comparing if there are extra elements in either array, will set status = failed after printing out extra elements below
			if(expected.size() < actual.size()){
				upperBound = actual.size();
				lowerBound = expected.size();
			}
			else if(expected.size() > actual.size()){
				lowerBound = actual.size();
				upperBound = expected.size();
			}
			//or if they're the same, then it does'nt matter which bound we're using
			else{
				upperBound = actual.size();
				lowerBound = expected.size();
			}
			
			for(int i = 0; i < lowerBound; i++){
				if(!expected.get(i).trim().equals(actual.get(i).trim())){
					status = false;
					logger.info("Comparison FAILURE:");
					logger.info("Element from expected array: [" + expected.get(i).trim() + "]");
					logger.info("Element from actual array  : [" + actual.get(i).trim() + "]");
				}
			}
			
			if(expected.size() > actual.size()){
				logger.info("Comparison FAILURE, extra elements in the expected array:");
				for(int i = lowerBound; i < upperBound; i++)
					logger.info(expected.get(i));
				status = false;
			}
			else if(expected.size() < actual.size()){
				logger.info("Comparison FAILURE, extra element in the actual array:");
				for(int i = lowerBound; i < upperBound; i++)
					logger.info(actual.get(i));
				status = false;
			}	
			
			if(status)
				logger.info("Comparison completed. PASS");
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 status = false;
		 }
		 return status;
	 }
	 
	 public boolean compareStringArray(String[] expected, String[] actual){
		 //return (expected.containsAll(actual) && actual.containsAll(expected));
		 
		 boolean status = true;
		 int upperBound = 0;
		 int lowerBound = 0;
		 try{
			//still comparing if there are extra elements in either array, will set status = failed after printing out extra elements below
			if(expected.length < actual.length){
				upperBound = actual.length;
				lowerBound = expected.length;
			}
			else if(expected.length > actual.length){
				lowerBound = actual.length;
				upperBound = expected.length;
			}
			//or if they're the same, then it does'nt matter which bound we're using
			else{
				upperBound = actual.length;
				lowerBound = expected.length;
			}
			
			for(int i = 0; i < lowerBound; i++){
				if(!expected[i].trim().equals(actual[i].trim())){
					status = false;
					logger.info("Comparison FAILURE:");
					logger.info("Element from expected array: [" + expected[i].trim() + "]");
					logger.info("Element from actual array  : [" + actual[i].trim() + "]");
				}
			}
			
			if(expected.length > actual.length){
				logger.info("Comparison FAILURE, extra elements in the expected array:");
				for(int i = lowerBound; i < upperBound; i++)
					logger.info(expected[i]);
				status = false;
			}
			else if(expected.length < actual.length){
				logger.info("Comparison FAILURE, extra element in the actual array:");
				for(int i = lowerBound; i < upperBound; i++)
					logger.info(actual[i]);
				status = false;
			}	
			
			if(status)
				logger.info("Comparison completed. PASS");
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 status = false;
		 }
		 return status;
	 }
	 
    
    public void isSurveyWindowDisplayed(){
		 try {
				if (driver.findElement(By.xpath("//*[@id='decline']")).isDisplayed()) {
				    driver.findElement(By.xpath("//*[@id='decline']")).click();
				   }
		 }catch(NotFoundException e) {
					logger.info("Survey Window not found");
				   }		 
	    }
    
	 public String GetCurrentURL(){
		 String url = driver.getCurrentUrl();
		 logger.info("Current URL is =" + url);
		 return url;
	 }	 
	 
	 /*******************************************************************************
	  * 							Scrolling methods								*
	  *******************************************************************************/	
      public void scrollElementByID(String id){   
    	  JavascriptExecutor js = (JavascriptExecutor) driver;  
    	  js.executeScript(	"var obj = document.getElementById(\""+ id +"\");" +
    			  			"obj.scrollTop = obj.scrollHeight;");
      }
      public void scrollElementByName(String name){   
    	  JavascriptExecutor js = (JavascriptExecutor) driver;  
    	  js.executeScript(	"var obj = document.getElementById(\""+ name +"\");" +
    			  			"obj.scrollTop = obj.scrollHeight;");
      }
 
}