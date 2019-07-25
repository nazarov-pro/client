package com.shahinnazarov.client.email.service;


import com.shahinnazarov.client.common.container.MessageResponse;
import com.shahinnazarov.client.common.container.MessageStatus;
import com.shahinnazarov.client.common.service.TransportableService;
import com.shahinnazarov.client.email.container.MailMessage;
import com.shahinnazarov.client.email.utils.MailConstants;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;


public class MailService implements TransportableService<MailMessage> {

    private String auth;
    private String socketPort;
    private String socketClass;
    private String host;
    private String port;
    private String user;
    private String password;
    private String tls;
    private String protocol;
    private String from;

    private Properties mailProperties;

    /**
     * Initialize Mail Client and Properties
     */
    @PostConstruct
    public void init() {
        try {
            InputStream resource = getClass().getClassLoader().getResourceAsStream(MailConstants.PROPERTIES_PATH);
            assert resource != null;

            Properties properties = new Properties();
            properties.load(resource);

            this.auth = properties.getProperty(MailConstants.K_AUTH);
            this.socketPort = properties.getProperty(MailConstants.K_SOCKET_PORT);
            this.socketClass = properties.getProperty(MailConstants.K_SOCKET_CLASS);
            this.host = properties.getProperty(MailConstants.K_HOST);
            this.port = properties.getProperty(MailConstants.K_PORT);
            this.from = properties.getProperty(MailConstants.K_USER);
            this.user = properties.getProperty(MailConstants.K_USER);
            this.password = properties.getProperty(MailConstants.K_PASSWORD);
            this.tls = properties.getProperty(MailConstants.K_TLS_ENABLED);
            this.protocol = properties.getProperty(MailConstants.K_PROTOCOL);
            this.mailProperties = getProperties();

        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong when initializing sms client. Details: " + e.getMessage());
        }
    }

    /**
     * Send mail to user
     * @param mailMessage
     * @return
     */
    @Override
    public MessageResponse send(MailMessage mailMessage) {
        MessageResponse response = new MessageResponse();
        response.setExecuted(LocalDateTime.now());

        try {
            Session session = getSession(mailProperties);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));

            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(mailMessage.getReceiver()));
            message.setSubject(mailMessage.getSubject());

            String msg = mailMessage.getContent();

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            mailMessage.getAttachments().forEach(a -> {
                try {
                    MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                    attachmentBodyPart.attachFile(a);
                    multipart.addBodyPart(attachmentBodyPart);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            message.setContent(multipart);
            Transport.send(message);
            response.setStatus(MessageStatus.SUCCESS);
        } catch (MessagingException e) {
            response.setStatus(MessageStatus.FAIL);
            response.setErrorCode("2");
            response.setErrorMessage("Unknown mail error. Details: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(MessageStatus.NOT_VALID);
            response.setErrorCode("0");
            response.setErrorMessage("Error occured. Details: " + e.getMessage());
        }
        return response;
    }

    private Properties getProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", auth);
        prop.put("mail.smtp.starttls.enable", tls);
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        return prop;
    }

    private Session getSession(Properties properties) {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
    }
}
