package com.verity.webreport;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;

   

@WebServlet("/exportExcel")
public class ExportExcel extends HttpServlet
{
	private static final int DEFAULT_BUFFER_SIZE = 8192; // 8KB.
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try{
			DatabaseConnection dataConn = new DatabaseConnection();
			String projectName = request.getParameter("project");
		    String cycleName = request.getParameter("cycle");
		    String option = request.getParameter("option");
		    List<TestCaseReport> report = dataConn.getDetailedReport(projectName, cycleName, option);
		    CycleSummary cycleSummary = dataConn.getCycleSummary(projectName, cycleName);
		    
		    
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    HSSFSheet sheet = workbook.createSheet("Results sheet");

		    
		    
		    
		    //style for header
		    HSSFFont fontHeader = workbook.createFont();
		    fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    fontHeader.setFontHeight((short) 300);
		    HSSFCellStyle styleHeader = workbook.createCellStyle();
		    styleHeader.setFont(fontHeader);
		    
		    //style for table header
		    HSSFColor lightGray =  setColor(workbook,(byte) 0xE0, (byte)0xE0,(byte) 0xE0);
		    HSSFFont fontTableHeader = workbook.createFont();
		    fontTableHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    HSSFCellStyle styleTableHeader = workbook.createCellStyle();
		    styleTableHeader.setFont(fontTableHeader);
		    styleTableHeader.setFillForegroundColor(lightGray.getIndex());
		    styleTableHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
		    
		    //title    
		    Row row = sheet.createRow(0);
		    Cell cell = row.createCell(0);
		    cell.setCellValue("Verity Automation Result");
		    cell.setCellStyle(styleHeader);
		    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		    //Application
		    row = sheet.createRow(1);
		    row.createCell(0).setCellValue("Application");
		    row.createCell(1).setCellValue(projectName);
		    //Cycle
		    row = sheet.createRow(2);
		    row.createCell(0).setCellValue("Cycle");
		    row.createCell(1).setCellValue(cycleName);
		    //Pass rate
		    row = sheet.createRow(3);
		    row.createCell(0).setCellValue("Pass rate");
		    row.createCell(1).setCellValue(String.valueOf(cycleSummary.getPassRate()) + "%");
		    //Pass 
		    row = sheet.createRow(4);
		    row.createCell(0).setCellValue("Passed");
		    row.createCell(1).setCellValue(String.valueOf(cycleSummary.getPassed()));
		    //Fail 
		    row = sheet.createRow(5);
		    row.createCell(0).setCellValue("Failed");
		    row.createCell(1).setCellValue(String.valueOf(cycleSummary.getFailed()));
		    //Total
		    row = sheet.createRow(6);
		    row.createCell(0).setCellValue("Total");
		    row.createCell(1).setCellValue(String.valueOf(cycleSummary.getTotal()));
		    //Start date
		    row = sheet.createRow(7);
		    row.createCell(0).setCellValue("Start date");
		    row.createCell(1).setCellValue(cycleSummary.getStartDate());
		    //Last run
		    row = sheet.createRow(8);
		    row.createCell(0).setCellValue("Last run date");
		    row.createCell(1).setCellValue(cycleSummary.getLastRunDate());
		    //Generation date
		    row = sheet.createRow(9);
		    row.createCell(0).setCellValue("Report generated on");
		    row.createCell(1).setCellValue(getCurrentTimeStamp());
		    //empty row
		    row = sheet.createRow(10);
		    //Result title and # of entries
		    row = sheet.createRow(11);
		    cell = row.createCell(0);
		    int entries=0;
		    if(option.equalsIgnoreCase("all")){
		    	cell.setCellValue("Result for all test cases");
			    entries = cycleSummary.getTotal();
		    }
		    else if(option.equalsIgnoreCase("fail")){
		    	cell.setCellValue("Result for failed test cases");
		    	entries = cycleSummary.getFailed();
		    }
		    else if(option.equalsIgnoreCase("pass")){
		    	cell.setCellValue("Result for passed test cases");
		    	entries = cycleSummary.getPassed();
		    }
		    cell.setCellStyle(styleHeader);
		    sheet.addMergedRegion(new CellRangeAddress(11, 11, 0, 4));
		    //Number of entries
		    row = sheet.createRow(12);
		    row.createCell(0).setCellValue("Number of entries");
		    row.createCell(1).setCellValue(String.valueOf(entries));
		    //empty row
		    row = sheet.createRow(13);
		    //table header
		    row = sheet.createRow(14);
		    cell = row.createCell(0);
		    cell.setCellValue("Test case ID");
		    cell.setCellStyle(styleTableHeader);
		    cell = row.createCell(1);
		    cell.setCellValue("Description");
		    cell.setCellStyle(styleTableHeader);
		    cell = row.createCell(2);
		    cell.setCellValue("Status");
		    cell.setCellStyle(styleTableHeader);
		    cell = row.createCell(3);
		    cell.setCellValue("Screenshot");
		    cell.setCellStyle(styleTableHeader);
		    cell = row.createCell(4);
		    cell.setCellValue("Error message");
		    cell.setCellStyle(styleTableHeader);
		    
		    //style for table data
		    HSSFCellStyle styleTableData = workbook.createCellStyle();
		    styleTableData.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		    styleTableData.setWrapText(true);
		    
		    for(int i =0; i<report.size(); i++){
		    	row = sheet.createRow(15+i);
		    	cell = row.createCell(0);
		    	cell.setCellValue(report.get(i).getID());
		    	cell.setCellStyle(styleTableData);
		    	cell = row.createCell(1);
		    	cell.setCellValue(report.get(i).getDescription());
		    	cell.setCellStyle(styleTableData);
		    	cell = row.createCell(2);
		    	cell.setCellValue(report.get(i).getStatus());
		    	cell.setCellStyle(styleTableData);
		    	cell = row.createCell(3);
		    	if(report.get(i).getScreenshot()==null ||  report.get(i).getScreenshot().equalsIgnoreCase("null"))
		    		cell.setCellValue(report.get(i).getScreenshot());
		    	else
		    		cell.setCellValue("http://10.107.133.49:8080/ReportServlet" + report.get(i).getScreenshot());
		    	cell.setCellStyle(styleTableData);
		    	cell = row.createCell(4);
		    	cell.setCellValue(report.get(i).getError());
		    	cell.setCellStyle(styleTableData);
		    }
		    
		    for(int i=0; i<5 ; i++){
			    //resize column, need to put this inside the loop 
			    sheet.autoSizeColumn(i);
		    }
			 
		    //Test new line char
//		    row = sheet.createRow(15);
//		    row.createCell(0).setCellValue("testing long long long longer \013 new line character");

		    //set fixed width for column description, screenshot, and error message
		    sheet.setColumnWidth(1, 10000);
		    sheet.setColumnWidth(3, 10000);
		    sheet.setColumnWidth(4, 10000);

		    
		    File file = new File("C:\\Selenium\\ReportServlet\\WebContent\\excelSheets\\"+ projectName + "_"
		    						+cycleName + "_" + option + "_ResultReport" + ".xls");
		    FileOutputStream outFile =new FileOutputStream(file);
		    workbook.write(outFile);
		    outFile.close();
		
			response.reset();
			response.setBufferSize(DEFAULT_BUFFER_SIZE);
			response.setHeader("Content-Type", "application/vnd.ms-excel");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
		
			BufferedInputStream input = null;
			BufferedOutputStream output = null;
		
			try {
			    input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
			    output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
		
			    byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			    for (int length; (length = input.read(buffer)) > -1;) {
			        output.write(buffer, 0, length);
			    }
			} finally {
			    if (output != null) try { output.close(); } catch (IOException ignore) {}
			    if (input != null) try { input.close(); } catch (IOException ignore) {}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		   
	}
	
	private String getCurrentTimeStamp(){
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	
	private HSSFColor setColor(HSSFWorkbook workbook, byte r,byte g, byte b){
		HSSFPalette palette = workbook.getCustomPalette();
		HSSFColor hssfColor = null;
		try {
			hssfColor= palette.findColor(r, g, b); 
			if (hssfColor == null ){
			    palette.setColorAtIndex(HSSFColor.LAVENDER.index, r, g,b);
			    hssfColor = palette.getColor(HSSFColor.LAVENDER.index);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return hssfColor;
	}
	

}