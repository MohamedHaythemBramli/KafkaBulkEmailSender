package com.github.bulkemailsender.service;

import com.github.bulkemailsender.config.EmailProviderConfig;
import com.github.bulkemailsender.dto.EmailMessageDto;
import com.github.bulkemailsender.exception.EmailMessageSendingException;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailMessageSenderService {

    private final EmailProviderConfig emailProviderConfig;

    private final SSLContext sslContext;

    public void sendEmail(EmailMessageDto emailMessageDto) throws EmailMessageSendingException, MessagingException {
        log.info("Preparing to send email to: " + emailMessageDto.getTo());

        try {
            // Load properties from the configuration
            Properties prop = new Properties();
            prop.putAll(emailProviderConfig.getProperties());

            if (sslContext != null) {
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                prop.put("mail.smtp.ssl.socketFactory", sslSocketFactory);
            }


            // Create a mail session with authentication
            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            emailProviderConfig.getAuth().getUsername(),
                            emailProviderConfig.getAuth().getPassword()
                    );
                }
            });


            // Create the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailMessageDto.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailMessageDto.getTo()));
            message.setSubject(emailMessageDto.getSubject());

            // Create the body part
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(emailMessageDto.getBody(), "text/html; charset=utf-8");

            // Create multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            // Set the content of the email
            message.setContent(multipart);

            // Send the email
            Transport.send(message);
            log.info("Email sent successfully to: " + emailMessageDto.getTo());

        } catch (MessagingException e) {
            log.error("Failed to send email to: " + emailMessageDto.getTo(), e);
            throw new EmailMessageSendingException("Error while sending email", e);
        }
    }
}
