package com.anteag04.springbootbackend.helper;

import java.util.Properties;
import java.util.*;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class EmailHelper {
    public void sendEmail( String to, String subject, String content){
        String from = "professor@gmail.com";
        String host = "localhost";

        Properties properties = System.getProperties();
        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            message.setSubject(subject);
            // Now set the actual message
            message.setText(content);
            // Send message
            Transport.send(message);
            System.out.println("Message sent successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
