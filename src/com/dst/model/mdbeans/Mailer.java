package com.dst.model.mdbeans;

import java.util.Calendar;
import java.util.Properties;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.ibm.icu.util.StringTokenizer;

/**
 * Message-Driven Bean implementation class for: Mailer
 */
@MessageDriven(
activationConfig = { @ActivationConfigProperty(
propertyName = "destinationType", propertyValue = "javax.jms.Queue")}, 
mappedName = "jms/MailerDestinationResource")
public class Mailer implements MessageListener 
{
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) 
    {
        try 
        {                       
            if (message instanceof TextMessage) 
            {             
                TextMessage mesg = (TextMessage) message; 
                
            	// QQS CONSTANTES            	
                final String HOST 		= "smtp.gmail.com";
                final String TRANSPORT 	= "smtp";
            	final String SUBJECT 	= "Document Search Tool - About your password resetting";
                final String FROM 		= "dstmailerter@gmail.com";
                final String PASS 		= "123456789ter";
            	StringTokenizer st 		= new StringTokenizer(mesg.getText(),":");
            	final String TO 	 	= st.nextToken();
            	final String NEWPASS 	= st.nextToken();
            			
                Properties properties = new Properties();
                properties.put("mail.smtp.host", HOST);
				properties.put("mail.smtp.ssl.enable", "true");
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.smtp.starttls.enable", "true");
				
                javax.mail.Session session = javax.mail.Session.getInstance(properties);
                javax.mail.Message msg = new MimeMessage(session);
                
            	// PREPARATION DU CONTENU DU MAIL   
                msg.setFrom(new InternetAddress(FROM));
                msg.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(TO));
                msg.setSubject(SUBJECT);         
                msg.setContent("Hi, <br /><br/>Your new password is: "+NEWPASS+"<br /><br/>----------------<br />DST Team", "text/html;charset=utf8");                                                           
                msg.setSentDate(Calendar.getInstance().getTime());
                
                // PREPARATION DE L'ENVOIE DU MAIL
                Transport transport = session.getTransport(TRANSPORT);
                transport.connect(HOST, FROM, PASS);
                
                // ENVOIE DU MAIL
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();
                
                System.out.println("Email sent to destination !!!");
            }
            else
            {
            	System.err.println("Error sending email...");
            }
        } 
        catch(MessagingException e)
        {
            e.printStackTrace();
        }
        catch (JMSException ex) 
        {
            ex.printStackTrace();
        }
    }

}
