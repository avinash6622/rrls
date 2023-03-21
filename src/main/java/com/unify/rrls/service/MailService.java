package com.unify.rrls.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.unify.rrls.domain.HistoryLogs;
import com.unify.rrls.domain.User;

import io.github.jhipster.config.JHipsterProperties;

/**
 * Service for sending emails.
 * <p>
 * We use the @Async annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private static final String LISTS="lists";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
            MessageSource messageSource, SpringTemplateEngine templateEngine) {

        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);
        DateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
    	Date date = new Date();
    	String subDate=sdf.format(date);
        System.out.println("from mail id "+jHipsterProperties.getMail().getFrom());

//        String recipient = "harisoft89@gmail.com";
//
//        // email ID of  Sender.
        String sender = "unifirrnls@gmail.com";
//
//        // using host as localhost
        String host = "smtp.gmail.com";
        String pass ="nswwvqlvivtshkir";

        Properties properties = System.getProperties();
//
//        // Setting up mail server
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.user", sender);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "false");
        properties.put("mail.smtp.password", pass);

        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(
                    "unifirrnls@gmail.com", "nswwvqlvivtshkir");// Specify the Username and the PassWord
           }
        });

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(new InternetAddress(sender));
            if(subject.equals("Research Repository & Learning System EOD Notifications - Dated "))
            message.setSubject(subject+subDate);
            else{
            	 message.setSubject(subject+subDate);}
            message.setText(content, isHtml);
           // javaMailSender.send(mimeMessage);
            System.out.println("Mail successfully sent");
            log.debug("Sent email to User '{}'", to);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Email could not be sent to user '{}'", to, e);
            } else {
                log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
            }
        }
    }

    @Async
    public void sendEmailResetPassword(String to, String subject, String content, boolean isMultipart, boolean isHtml )throws MessagingException {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);
        DateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        Date date = new Date();
        String subDate=sdf.format(date);
        System.out.println("from mail id "+jHipsterProperties.getMail().getFrom());
        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        // email ID of Recipient.
//        String recipient = "harikrishnan.s@indiumsoft.com";
//
//        // email ID of  Sender.
        String sender = "unifirrnls@gmail.com";
        String host = "smtp.gmail.com";
        String pass ="nswwvqlvivtshkir";

        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.port", 25);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.user", sender);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "false");
        properties.put("mail.smtp.password", pass);




        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(new InternetAddress(sender));
            if(subject.equals("Research Repository & Learning System EOD Notifications - Dated "))
                message.setSubject(subject+subDate);
            else{
                message.setSubject(subject+subDate);}
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Email could not be sent to user '{}'", to, e);
            } else {
                log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
            }
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);

    }
    @Async
    public void sendEmailFromTemplateReset(User user, String templateName, String titleKey) throws MessagingException {
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmailResetPassword(user.getEmail(), subject, content, false, true);

    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "passwordResetEmail", "email.reset.title");
    }

    @Async
    public void sendPasswordReset(User user) throws MessagingException {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplateReset(user, "passwordResetEmail", "email.reset.title");
    }

    @Async
    public void sendNotification(User user,List<HistoryLogs>lists) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailForNotification(user, "notificationEmail", "email.notification.title",lists);
    }

    @Async
    public void sendEmailForNotification(User user, String templateName, String titleKey,List<HistoryLogs> lists) {
        Locale locale = Locale.forLanguageTag(user.getLangKey());

        Context context = new Context(locale);
        context.setVariable(LISTS, lists);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);

    }
}
