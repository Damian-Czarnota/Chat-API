package com.chat.api.infrastructure.service.classes;

import com.chat.api.infrastructure.service.interfaces.IEmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailServiceImpl implements IEmailService {

    private JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    EmailServiceImpl(JavaMailSender emailSender, TemplateEngine templateEngine){
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(String to, String title, Context content) {
        MimeMessage mail = emailSender.createMimeMessage();

        String body = templateEngine.process("template", content);

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setReplyTo("chat-pk-ti@chat.pk");
            helper.setFrom("chat-pk-ti@chat.pk");
            helper.setSubject(title);
            helper.setText(body, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        emailSender.send(mail);
    }

}