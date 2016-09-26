package de.axelspringer.ideas.mate.five.challenges;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Service
public class MailSender {

    @Value("${de.axelspringer.ideas.mate.mail.user:mate.challenge}")
    private String username;

    @Value("${de.axelspringer.ideas.mate.mail.password:changeit}")
    private String password;

    @SneakyThrows
    public void send(String name, String email, String text) {
        log.info("Sending mail to: " + email);

        Properties props = new Properties();

        props.put("mail.smtp.starttls.enable", true); // added this line
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress("Mate Challenge <hello@asideas.de>"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("AS Ideas Mate-Challenge 5");
        message.setText(text);

        log.info("Sending mail...");
        Transport.send(message);
        log.info("Done.");
    }
}