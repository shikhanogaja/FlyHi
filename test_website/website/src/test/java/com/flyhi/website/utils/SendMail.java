package com.flyhi.website.utils;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail 
{
	public void emailConfiguration()
    {
    	String SMTP_HOST_NAME 	= "localhost";
	    String SMTP_PORT 	 	= "25"; // default port is  465 !
	    String SMTP_FROM_ADDRESS= "email1@email.com";
		String to 				= "email2@email.com";
		/*
		String CC				= "email3@email.com";
		String BCC				= "email4@email.com";
		*/
		String subject			= "BugList From QA Automation";
		String messageTextFormat= "Hi," ;
		String messageText 		= "Please find attached, the defect found in test automation run";
		String msgconcat 		= messageTextFormat + "\n\n\n" + messageText;
		String messageEndText	= "Thanks,";
		String thanks			= "QA Dept";
		String endConcat 		= msgconcat+"\n\n\n"+messageEndText + "\n" + thanks;
		String fileName     	= ZipFile.OUTPUT_ZIP_FILE;
		System.out.println("test"+fileName);
		boolean sessionDebug	= false;
		
		// Create some properties and get the default Session.
		Properties props = System.getProperties();
		props.put("mail.host", SMTP_HOST_NAME);
		props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.port", SMTP_PORT );

		Session session = Session.getDefaultInstance(props, null);
		
		/*
		 * Set debug on the Session so we can see what is going on.
		 * 
		 * Passing false will not echo debug info, and passing true will
		 */
		session.setDebug(sessionDebug);
		
		try 
		{
			/*
			 * Instantiate a new MimeMessage and fill it with the required information.
			 */
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(SMTP_FROM_ADDRESS));
			InternetAddress[] address = {new InternetAddress(to)};
			/*
			InternetAddress[] address1 = {new InternetAddress(CC)};
			InternetAddress[] address2 = {new InternetAddress(BCC)};
			*/
			msg.setRecipients(Message.RecipientType.TO, address);
			/*			
 			msg.setRecipients(Message.RecipientType.BCC, address2);
			msg.setRecipients(Message.RecipientType.CC, address1);
			*/
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			//msg.setText(messageText);   
			MimeBodyPart mbp1 = new MimeBodyPart();
		    mbp1.setText(endConcat);
	       
	       // create the second message part
	       MimeBodyPart mbp3 = new MimeBodyPart();

	       // Attach the file to the message
	       FileDataSource fds = new FileDataSource(fileName);
	       mbp3.setDataHandler(new DataHandler(fds));
	       mbp3.setFileName(fds.getName());
	       Multipart mp = new MimeMultipart();
	       mp.addBodyPart(mbp1);
	       mp.addBodyPart(mbp3);
	       msg.setContent(mp);
		   // Hand the message to the default transport service for delivery.
		   Transport.send(msg);
		}
		
		catch (MessagingException mex) 
		{
			mex.printStackTrace();
		}
    }
}