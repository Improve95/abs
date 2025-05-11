package ru.improve.abs.service.configuration.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

//@Configuration
public class MailSender {

//    @Bean
    public JavaMailSender javaMailSender(MailSenderConfig mailSenderConfig) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailSenderConfig.getHost());
        mailSender.setPort(mailSenderConfig.getPort());

        mailSender.setUsername(mailSenderConfig.getUsername());
        mailSender.setPassword(mailSender.getPassword());

//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}
