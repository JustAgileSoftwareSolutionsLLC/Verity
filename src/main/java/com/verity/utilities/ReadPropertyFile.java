/**
 * Framework -QA CoE Test Framework
 * Version - 0.1
 * Creation Date - Feb, 2013
 * Author - Ramesh Tejavath
 * Description: Property file reader
 *  **/
package com.verity.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import org.apache.log4j.Logger;

public class ReadPropertyFile {
	public static String pValue;
	public static String ConfigPathLocation="/Users/rameshtejavath/Documents/Selenium/JustAgile/VerityFramework/src/test/resources/Config.properties";
	public static Logger logger= Logger.getLogger(ReadPropertyFile.class);
	public static String readFile(String File,String pName) {
		try {
			Properties pro = new Properties();
			FileInputStream in = new FileInputStream(File);
			pro.load(in);
			// getting values from property file
			 pValue = pro.getProperty(pName);
			 logger.info("Value is:" + pValue );
			 return pValue;
		
		} catch (IOException e) {
			System.out.println("Error is:" + e.getMessage());
			e.printStackTrace();
		}
		return pValue;

	}
	
	 public static String getConfigPropertyVal(final String key) {
		    String ConfigPropertyVal = readFile(ConfigPathLocation,key);
			return ConfigPropertyVal != null ? ConfigPropertyVal.trim() : ConfigPropertyVal;
		}
	
}

