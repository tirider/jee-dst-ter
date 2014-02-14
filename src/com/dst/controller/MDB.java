package com.dst.controller;

public class MDB {

	/**
	 * 

package convert;

import java.text.DecimalFormat;
import java.util.*;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;
import javax.mail.*;
import javax.mail.internet.*;


@MessageDriven(mappedName = "jms/MailContentQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MailerMDB implements MessageListener {
    
    @EJB
    Converter converter;
    
    public MailerMDB() {
    }
    
    @Override
    public void onMessage(javax.jms.Message message) {        
        try {                       
            if (message instanceof TextMessage) {                             
                TextMessage mesg = (TextMessage) message; 
                // Envoi de mail :
                Properties p = new Properties();
                p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.ssl.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.starttls.enable","true");
                javax.mail.Session session = javax.mail.Session.getInstance(p);
                javax.mail.Message msg = new MimeMessage(session);
                try {
                    // Préparation du mail                                       
                    msg.setFrom(new InternetAddress("chouki.tiber@gmail.com"));
                    String content = mesg.getText();
                    String amount = content.substring(0,content.indexOf("#"));                    
                    String dest = content.substring(content.indexOf("#")+1);                    
                    msg.setRecipient(javax.mail.Message.RecipientType.TO,new InternetAddress(dest));
                    String sujet = "Conversions de monnaie";
                    msg.setSubject(sujet);         
                    
                    content = "<h3>"+amount+" euros is equivalent to:</h3>";
                    double amountd = Double.parseDouble(amount);
                    
                    Map<Monnaie,Double> map = converter.toOtherCurrencies(amountd);
                    
                    content += "<table border=\"1\">";
                    content += "<tr><th>"+"Currency"+"</th>";
                    content += "<th>"+"Actual Rate"+"</th>";
                    content += "<th>"+"Converted Amount"+"</th></tr>";
                    for(Monnaie k : map.keySet()) {                        
                        content += "<tr>";
                        content += "<td>"+k.getCountry()+" ("+k.getCurrency()+" : "+k.getCurrencyCode()+")</td>";
                        DecimalFormat f = new DecimalFormat();
                        f.setMaximumFractionDigits(2);                        
                        
                        content += "<td>"+f.format(k.getRate())+"</td>";
                        
                        double result = map.get(k);                                                
                        content += "<td>"+f.format(result)+"</td>";
                        content += "</tr>";
                    }
                    content += "</table>";
                    // Envoi de mail :                                                            
                    msg.setContent(content,"text/html;charset=utf8");                                                           
                    msg.setSentDate(Calendar.getInstance().getTime());
                    
                    // Préparation de l'envoi du mail
                    Transport transport = session.getTransport("smtp");
                    transport.connect("smtp.gmail.com",587,"chouki.tiber","...");
                    
                    // Envoi du mail
                    transport.sendMessage(msg,msg.getAllRecipients());
                    transport.close();
                    System.out.println("Email sent to destination");
                }
                catch(MessagingException e){
                    e.printStackTrace();
                }
            }
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
}






    <body>
        <h1>Currency Converter</h1>
        <form action="index.jsp" method="post">
            Amount to convert (in euros): <input type="text" name="amount" size="10"/><br/>
            Target Currency:
            <select name="currency">
                <option value="USD">American Dollars</option>
                <option value="JPY">Japanese Yen</option>
                <option value="GBP">British Pound Sterling</option>
                <option value="CAD">Canadian Dollars</option>
                <option value="CNY">Chinese Yuan</option>
            </select><br/><br/>
            If you want to get the conversion in all currencies,
            enter you email address: <input type="text" name="email" size="20"/><br/>
            <input align="right" type="submit" value="Convertir"/>
        </form>
        <%@page import="java.util.*,javax.jms.*,javax.naming.*,java.math.*" %>
        <jsp:useBean class="convert.ConverterBean" id="converter"/>
        <%                      
                String str =  request.getParameter("amount");
                String currency = request.getParameter("currency");
                if((str != null)&&(str.length()!=0)&&(currency!=null)&&(currency.length()!=0)) {
                    
                    double amount = Double.parseDouble(str);                    
                    double result = converter.toCurrency(amount, currency);
                    DecimalFormat f = new DecimalFormat();
                    f.setMaximumFractionDigits(2);
                    out.println("<h3>"+f.format(amount)+" euros is equivalent to "+f.format(result)+" in currency: "+currency+"</h3>");
                    
                    String email = request.getParameter("email");                        
                    if(email != null && email.length() != 0) {                        
                        try {
                            Context jndiContext = new InitialContext();
                             javax.jms.ConnectionFactory connectionFactory =
                                     (QueueConnectionFactory)jndiContext.lookup("jms/MailContentQueueFactory");
                            
                            Connection connection = connectionFactory.createConnection();
                            Session sessionQ = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
                            TextMessage message = sessionQ.createTextMessage();                            

                            String text = str+"#"+email;                            
                            message.setText(text);
                            
                            javax.jms.Queue queue = (javax.jms.Queue)
                            jndiContext.lookup("jms/MailContentQueue");
                            MessageProducer messageProducer=sessionQ.createProducer(queue);

                            messageProducer.send(message);

                        }
                        catch(NamingException e) {
                            e.printStackTrace();
                        }
                        catch(JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }            
            %>
    </body>
	 */
}
