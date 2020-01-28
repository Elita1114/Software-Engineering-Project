package common;


import java.util.*; 
/*
import javax.mail.*; 
import javax.mail.internet.*; 
import javax.activation.*; 
import javax.mail.Session; 
import javax.mail.Transport; 
*/

public class SendEmail 
{
	/*
	// email ID of Recipient. 
	String recipient; 

	// email ID of Sender. 
	String sender;
	
	// using host as localhost 
	String host;
	
	String subject;
	
	String content;
	
	// Getting system properties 
	Properties properties; 
		
	// creating session object to get properties 
	Session session; 
	
	// MimeMessage object. 
	MimeMessage message; 
				
	public SendEmail(String recipient, String sender, String host, String subject, String content) {
		this.recipient = recipient;
		this.sender = sender;
		this.host = host;
		this.subject = subject;
		this.content = content;
		this.properties = System.getProperties();
		// Setting up mail server 
		this.properties.setProperty("mail.smtp.host", host);
		session = Session.getDefaultInstance(properties); 
		
		try
		{
			message = new MimeMessage(session); 

			// Set From Field: adding senders email to from field. 
			message.setFrom(new InternetAddress(sender)); 

			// Set To Field: adding recipient's email to from field. 
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); 

			// Set Subject: subject of the email 
			message.setSubject(subject); 

			// set body of the email. 
			message.setText(content); 

			// Send email. 
			Transport.send(message); 
			System.out.println("Mail successfully sent"); 
		} 
		catch (MessagingException mex) 
		{ 
			mex.printStackTrace(); 
		}
	}
	*/
} 
