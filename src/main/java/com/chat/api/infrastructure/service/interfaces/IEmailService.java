package com.chat.api.infrastructure.service.interfaces;

import org.thymeleaf.context.Context;

public interface IEmailService {
    void sendEmail(String to, String subject, Context content);
}
