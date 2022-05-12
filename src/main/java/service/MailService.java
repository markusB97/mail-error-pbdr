package service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class MailService {

    private Session session;
    private String fromMail;
    private String fromName;

    public MailService() throws Exception{
        String smtpHost = ConfigService.readFromConfig("smtp.host");
        fromMail = ConfigService.readFromConfig("from.normal.mail");
        fromName = ConfigService.readFromConfig("from.normal.name");
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        session = Session.getDefaultInstance(properties);
    }

    public void sendMail(String address, File file) throws IOException, MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromMail, fromName));
        BodyPart bodyPart = new MimeBodyPart();
        String text = "Beim Druck von mindestens einer Bestellung ist ein Fehler aufgetreten und wurde somit nicht gedruckt.<br/>" +
                "Im Anhang ist eine Liste dieser Bestellungen.";
        bodyPart.setContent(text, "text/html; charset=UTF-8");
        MimeBodyPart attachment = new MimeBodyPart();
        attachment.attachFile(file);
        Multipart multi = new MimeMultipart();
        multi.addBodyPart(bodyPart);
        multi.addBodyPart(attachment);
        message.setContent(multi);
        message.setSubject("Fehler beim Druck von Bestellungen");
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
        message.addRecipient(Message.RecipientType.CC, new InternetAddress(ConfigService.readFromConfig("to.mails")));
        Transport.send(message);
    }

}
