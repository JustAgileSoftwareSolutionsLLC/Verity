/**
 * Framework -QA CoE Test Framework
 * Version - 0.1
 * Creation Date - July, 2013
 * Author - Anh Ho
 * Description: This class will provide methods to send out emails automatically
 *  **/
package com.verity.utilities;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class SendEmails {
	private final String from = "AutomationResults@bankofthewest.com";
//	private final String[] to = { 	"Anh.Ho@bankofthewest.com",
//									"Ramesh.Tejavath@bankofthewest.com",
//									"Jane.Oster@bankofthewest.com"
//								};
	private String[] to;
	private final String host = "smtpint1.bankofthewest.com";
	private Properties properties;
	private Session session;
	private Logger logger= Logger.getLogger(SendEmails.class);	
	private boolean send = true;
	
	public SendEmails(){
		logger.info("Setting up SMTP server and properties");
		String emailList = ReadPropertyFile.getConfigPropertyVal("EmailList");
		if(emailList.length() < 1)
			send = false;
		else{
			if(emailList.contains(","))
				to = emailList.split(",");
			else{
				to = new String[1];
				to[0] = emailList;
			}
		}
		properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		session = Session.getDefaultInstance(properties);
		logger.info("Done! Session initialized.");
	}
	
	public boolean send(String emailMessage) throws AddressException, MessagingException{
		if(send){
			logger.info("Loading message and recipients...");
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			
			InternetAddress[] address = new InternetAddress[to.length];
			for(int i = 0; i < to.length; i++)
				address[i] = new InternetAddress(to[i].trim());
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject("Automation results for " + ReadPropertyFile.getConfigPropertyVal("Application") + " "
								+ ReadPropertyFile.getConfigPropertyVal("Cycle"));
			message.setContent(emailMessage, "text/html; charset=utf-8");
			logger.info("Done! Message and recipients loaded");
			logger.info("Sending email...");
			Transport.send(message);
			logger.info("Email sent successfully");		
		}
		else
			logger.info("Recipient list is empty, email is not sent");
		return send;

	}
	
	public boolean send(String emailMessage, String subjectLine) throws AddressException, MessagingException{
		if(send){
			logger.info("Loading message and recipients...");
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			
			InternetAddress[] address = new InternetAddress[to.length];
			for(int i = 0; i < to.length; i++)
				address[i] = new InternetAddress(to[i].trim());
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject(subjectLine);
			message.setContent(emailMessage, "text/html; charset=utf-8");
			logger.info("Done! Message and recipients loaded");
			logger.info("Sending email...");
			Transport.send(message);
			logger.info("Email sent successfully");		
		}
		else
			logger.info("Recipient list is empty, email is not sent");
		return send;	
	}
}
