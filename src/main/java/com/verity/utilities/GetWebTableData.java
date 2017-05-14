/**
 * Framework -QA CoE Test Framework
 * Version - 0.1
 * Creation Date - May, 2013
 * Author - Anh Ho
 * Description: This class will provide methods to get data from tables using webdriver. 
 * 				It will return a String array containing all of the values 
 *  **/

package com.verity.utilities;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;




public class GetWebTableData {
	private List<String> data;
	private int row;
	private int col;
	private final int minTime = 3; //seconds
	private final int normalTime = 30; //seconds
	private  Logger logger= Logger.getLogger(GetWebTableData.class);
	private WebDriver driver;
	/***************************************
	 * Constructor - Initialize list
	 ***************************************/
	public GetWebTableData(WebDriver driver){
		data = new ArrayList<String>();
		row = 1;
		col = 1;
		this.driver=driver;
	}
	
	/**************************************************
	 * Method to get the data by the exact column name
	 * Parameter: String locator, String colName
	 * Return value: List<String> data
	 **************************************************/
	public List<String> getDataByExactColumn(String locator, String colName){
		logger.info("Getting data from column [" + colName  +"]...");
		manageTimeout(minTime);
		int count = 0;
		String cell;
		resetData();
		locator = formatLocator(locator);
		findExactColumnIndex(locator, colName);
		//loop through all of the rows of the found column
		try{
			while(true){
				row++;
				try{
					//only getting value from text field right now
					if(driver.findElement(By.xpath(locator+"tr[" + row + "]/td[" + col + "]/input")).getAttribute("type").equalsIgnoreCase("text"))
						cell = driver.findElement(By.xpath(locator+"tr[" + row + "]/td[" + col + "]/input")).getAttribute("value");
					else
						cell = driver.findElement(By.xpath(locator+"tr[" + row + "]/td[" + col + "]")).getText();
				}
				catch(Exception e){
					//getting normal text from cell
					cell = driver.findElement(By.xpath(locator+"tr[" + row + "]/td[" + col + "]")).getText();
				}
				
				data.add(cell);
				count++;
			}
		}
		catch(Exception e){
			logger.info("Values found: " + count);
		}
		manageTimeout(normalTime);
		return getCopy(data);
	}
	
	/**************************************************
	 * Method to get the data by the partial column name
	 * Parameter: String locator, String colName
	 * Return value: List<String> data
	 **************************************************/
	public List<String> getDataByPartialColumn(String locator, String colName){
		manageTimeout(minTime);
		logger.info("Getting data from column [" + colName  +"]...");
		int count = 0;
		String cell;
		resetData();
		locator = formatLocator(locator);
		findPartialColumnIndex(locator, colName);
		//loop through all of the rows of the found column
		try{
			while(true){
				row++;
				try{
					//only getting value from text field right now
					if(driver.findElement(By.xpath(locator+"tr[" + row + "]/td[" + col + "]/input")).getAttribute("type").equalsIgnoreCase("text"))
						cell = driver.findElement(By.xpath(locator+"tr[" + row + "]/td[" + col + "]/input")).getAttribute("value");
					else
						cell = driver.findElement(By.xpath(locator+"tr[" + row + "]/td[" + col + "]")).getText();
				}
				catch(Exception e){
					//getting normal text from cell
					cell = driver.findElement(By.xpath(locator+"tr[" + row + "]/td[" + col + "]")).getText();
				}
				data.add(cell);
				count++;
			}
		}
		catch(Exception e){
			logger.info("Values found: " + count);
		}
		manageTimeout(normalTime);
		return getCopy(data);
	}
	
	/**************************************************
	 * Method to get the data by the exact row name
	 * Parameter: String locator, String row
	 * Return value: List<String> data
	 **************************************************/
	public List<String> getDataByExactRow(String locator, String rowName){
		logger.info("Getting data from row [" + rowName  +"]...");
		manageTimeout(minTime);
		int count = 0;
		String cell;
		resetData();
		locator = formatLocator(locator);
		findExactRowIndex(locator, rowName);
		//loop through all of the columns of the found row
		try{
			while(true){
				col++;
				try{
					//only getting value from text field right now
					if(driver.findElement(By.xpath(locator+"tr[" + row + "]/td[" + col + "]/input")).getAttribute("type").equalsIgnoreCase("text"))
						cell = driver.findElement(By.xpath(locator+"tr[" + row + "]/td[" + col + "]/input")).getAttribute("value");
					else
						cell = driver.findElement(By.xpath(locator+"tr[" + row + "]/td[" + col + "]")).getText();
				}
				catch(Exception e){
					//getting normal text from cell
					cell = driver.findElement(By.xpath(locator+"tr[" + row + "]/td[" + col + "]")).getText();
				}
				data.add(cell);
				count++;
			}
		}
		catch(Exception e){
			logger.info("Values found: " + count);
		}
		manageTimeout(normalTime);
		return getCopy(data);
	}
	
	/**************************************************
	 * Method to get the data by the partial row name
	 * Parameter: String locator, String row
	 * Return value: List<String> data
	 **************************************************/
	public List<String> getDataByPartialRow(String locator, String rowName){
		logger.info("Getting data from row [" + rowName  +"]...");
		manageTimeout(minTime);
		int count = 0;
		String cell;
		resetData();
		locator = formatLocator(locator);
		findPartialRowIndex(locator, rowName);
		//loop through all of the columns of the found row
		try{
			while(true){
				col++;
				try{
					//only getting value from text field right now
					if(driver.findElement(By.xpath(locator+"tr[" + row + "]/td[" + col + "]/input")).getAttribute("type").equalsIgnoreCase("text"))
						cell = driver.findElement(By.xpath(locator+"tr[" + row + "]/td[" + col + "]/input")).getAttribute("value");
					else
						cell = driver.findElement(By.xpath(locator+"tr[" + row + "]/td[" + col + "]")).getText();
				}
				catch(Exception e){
					//getting normal text from cell
					cell = driver.findElement(By.xpath(locator+"tr[" + row + "]/td[" + col + "]")).getText();
				}
				data.add(cell);
				count++;
			}
		}
		catch(Exception e){
			logger.info("Values found: " + count);
		}
		manageTimeout(normalTime);
		return getCopy(data);
	}
	
	/**************************************************
	 * Method to get size of the data list
	 * Parameter: None
	 * Return value: int size
	 **************************************************/
	public int getDataSize(){
		return data.size();
	}
	
	
	/****************************************************************
	 * Method to find the column index using column name
	 * 		and set the col value to the found column
	 * Parameter: String locator, String colName
	 * Return value: int column
	 ****************************************************************/
	public int findColumnIndex(String locator, String colName){
		String cell;
		col = 1;
		//loop through every column of the first row and find the first matched value
		try{
			while(true){			
				cell = driver.findElement(By.xpath(locator+"tr/td[" + col + "]")).getText();
				
				if(cell.contains(colName)){
					logger.info("Column [" + colName + "] found");
					return col;
				}			
				col++;
			}
		}
		catch(Exception e){
			logger.info("Reached the end, unable to find column with value [" + colName + "]");
			return -1;
		}
	}
	
	
	/****************************************************************
	 * Method to find the row index using row name
	 * 		and set the row value to the found row
	 * Parameter: String locator, String rowName
	 * Return value: None
	 ****************************************************************/
	public int findRowIndex(String locator, String rowName){
		String cell;
		row = 1;
		//loop through every row of the first column and find the first matched value
		try{
			while(true){			
				cell = driver.findElement(By.xpath(locator+"tr[" + row + "]/td")).getText();
				
				if(cell.equalsIgnoreCase(rowName)){
					logger.info("Row [" + rowName + "] found");
					return row;
				}			
				row++;
			}
		}
		catch(Exception e){
			logger.info("Reached the end, unable to find row with value [" + row + "]");
			return -1;
		}	
	}
	
	/****************************************************************
	 * Method to change the reference of the driver variable		
	 * Parameter: WebDriver driver
	 * Return value: None
	 ****************************************************************/
	public void setDriver(WebDriver driver){
		this.driver = driver;
	}
	
	/**************************************************
	 * Helper method to reset the variables
	 * Parameter: None
	 * Return value: none
	 **************************************************/
	private void resetData(){
		data.clear();
		row = 1;
		col = 1;
	}
	
	/**************************************************
	 * Helper method to get the locator into desired format
	 * Parameter: String locator
	 * Return value: String locator
	 **************************************************/
	private String formatLocator(String locator){
		if(!locator.substring(locator.length() -1).equals("/"))
			return locator + "/tbody/";
		else
			return locator + "tbody/";
	}
	
	/****************************************************************
	 * Helper method to find the column index using exact column name
	 * 		and set the col value to the found column
	 * Parameter: String locator, String colName
	 * Return value: None
	 ****************************************************************/
	private void findExactColumnIndex(String locator, String colName){
		String cell;
		col = 1;
		//loop through every column of the first row and find the first matched value
		try{
			while(true){			
				cell = driver.findElement(By.xpath(locator+"tr/*[" + col + "]")).getText();
				
				if(cell.equalsIgnoreCase(colName)){
					logger.info("Column [" + colName + "] found");
					break;
				}			
				col++;
			}
		}
		catch(Exception e){
			logger.info("Reached the end, unable to find column with value [" + colName + "]");
		}	
	}
	
	/****************************************************************
	 * Helper method to find the column index using partial column name
	 * 		and set the col value to the found column
	 * Parameter: String locator, String colName
	 * Return value: None
	 ****************************************************************/
	private void findPartialColumnIndex(String locator, String colName){
		String cell;
		col = 1;
		//loop through every column of the first row and find the first matched value
		try{
			while(true){			
				cell = driver.findElement(By.xpath(locator+"tr/*[" + col + "]")).getText();
				
				if(cell.contains(colName)){
					logger.info("Column [" + colName + "] found");
					break;
				}			
				col++;
			}
		}
		catch(Exception e){
			logger.info("Reached the end, unable to find column with value [" + colName + "]");
		}	
	}	
	
	/****************************************************************
	 * Helper method to find the row index using exact row name
	 * 		and set the row value to the found row
	 * Parameter: String locator, String rowName
	 * Return value: None
	 ****************************************************************/
	private void findExactRowIndex(String locator, String rowName){
		String cell;
		row = 1;
		//loop through every row of the first column and find the first matched value
		try{
			while(true){			
				cell = driver.findElement(By.xpath(locator+"tr[" + row + "]/*[1]")).getText();
				
				if(cell.equalsIgnoreCase(rowName)){
					logger.info("Row [" + rowName + "] found");
					break;
				}			
				row++;
			}
		}
		catch(Exception e){
			logger.info("Reached the end, unable to find row with value [" + row + "]");
		}	
	}
	
	/****************************************************************
	 * Helper method to find the row index using partial row name
	 * 		and set the row value to the found row
	 * Parameter: String locator, String rowName
	 * Return value: None
	 ****************************************************************/
	private void findPartialRowIndex(String locator, String rowName){
		String cell;
		row = 1;
		//loop through every row of the first column and find the first matched value
		try{
			while(true){			
				cell = driver.findElement(By.xpath(locator+"tr[" + row + "]/*[1]")).getText();
				
				if(cell.contains(rowName)){
					logger.info("Row [" + rowName + "] found");
					break;
				}			
				row++;
			}
		}
		catch(Exception e){
			logger.info("Reached the end, unable to find row with value [" + row + "]");
		}	
	}
	
	/**************************************************
	 * Helper method to change the default timeout
	 * Parameter: None
	 * Return value: none
	 **************************************************/
	private void manageTimeout(int seconds){
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}
	
	private List<String> getCopy(List<String> original){
		List<String> copy = new ArrayList<String>(original.size());
	    for(int i =0; i < original.size(); i++){
	    	copy.add(original.get(i));
	    }
		return copy;
	}
}
