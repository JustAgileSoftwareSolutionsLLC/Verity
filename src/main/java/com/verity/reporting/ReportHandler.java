package com.verity.reporting;

/**
 * Framework -QA CoE Test Framework
 * Version - 0.1
 * Creation Date - Feb, 2013
 * Author - Ramesh Tejavath
 * Description: Reporting 
 *  **/
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;


public class ReportHandler {

	public double successWidth = 0;
	public double warningWidth = 0;
	public double dangerWidth = 0;

	public FileWriter fstream;
	public BufferedWriter out;
	public String reportName;
	public int stepNo = 0;
	public int index = 0;
	
	public ReportHandler(String reportName) throws IOException {
		fstream = new FileWriter("reports/" + reportName + ".html", true);
		out = new BufferedWriter(fstream);
		this.reportName = reportName;
		getHtmlHeaders();
		getTestMeta();
	}

	public void info(String sinfo) throws IOException {
		index++;
		java.util.Date date = new java.util.Date();
		out.write("<tr><td>" + index + "</td><td>" + sinfo + "</td><td><span class='label label-info'>Info</span></td><td>" + new Timestamp(date.getTime()) + "</td></tr>");

	}
	
	public void pass(String sinfo) throws IOException {
		index++;
		stepNo++;
		successWidth++;
		java.util.Date date = new java.util.Date();
		out.write("<tr><td>" + index + "</td><td>" + sinfo + "</td><td><span class='label label-success'>Pass</span></td><td>" + new Timestamp(date.getTime()) + "</td></tr>");

	}

	public void fail(String sinfo) throws IOException {
		index++;
		stepNo++;
		dangerWidth++;
		java.util.Date date = new java.util.Date();
		out.write("<tr><td>" + index + "</td><td>" + sinfo + "</td><td><span class='label label-important'>Fail</span></td><td>" + new Timestamp(date.getTime()) + "</td></tr>");

	}

	public void warning(String sinfo) throws IOException {
		index++;
		stepNo++;
		warningWidth++;
		java.util.Date date = new java.util.Date();
		out.write("<tr><td>" + index + "</td><td>" + sinfo + "</td><td><span class='label label-warning'>Warn</span></td><td>" + new Timestamp(date.getTime()) + "</td></tr>");

	}

	public void crash(String sinfo) throws IOException {
		index++;
		stepNo++;
		java.util.Date date = new java.util.Date();
		out.write("<tr><td>" + index + "</td><td>" + sinfo + "</td><td><span class='label label-crash'>Crash</span></td><td>" + new Timestamp(date.getTime()) + "</td></tr>");
	}

	public void getHtmlHeaders() throws IOException {
		String htmlHeaders = "<!DOCTYPE html>\n" + "<html lang='en'>\n" + "<head>\n" + "<title>" + reportName + "</title>\n" + "<link href='../assets/css/bootstrap.min.css' rel='stylesheet'>\n" + "<link href='../assets/css/bootstrap-responsive.min.css' rel='stylesheet'>" + "<link href='../assets/css/custom.css' rel='stylesheet'>" + "</head>\n<body>";
		out.write(htmlHeaders);
	}

	public void getTestMeta() throws IOException {
		String tableMeta = "<div class='container'><div class='row'><h1>" + reportName + "</h1><hr><table class='table table-bordered table-hover table-striped table-condensed'><tr><thead><th class='span1'>Step</th><th>Description</th><th class='span1'>Status</th><th class='span3'>Timestamp</th></thead></tr><tbody>";
		out.write(tableMeta);
	}

	public void closeLogs() throws IOException {
		out.write("</tbody><tfoot><th colspan='4'><div class='progress' style='margin-bottom:0px;'><div class='bar bar-success' style='width: " + (successWidth / stepNo) * 100 + "%;'>" + (successWidth / stepNo) * 100 + "%</div><div class='bar bar-warning' style='width: " + (warningWidth / stepNo) * 100 + "%;'>" + (warningWidth / stepNo) * 100 + "%</div><div class='bar bar-danger' style='width: " + (dangerWidth / stepNo) * 100 + "%;'>" + (dangerWidth / stepNo) * 100 + "%</div></div></th></tfoot></table></div></div></body></html>");
		out.close();
	}

	public void generateReport() throws MalformedURLException, IOException {
		String url = ((new java.io.File("reports/" + reportName + ".html")).toURI().toURL()).toString();
		java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
	}
	
}
