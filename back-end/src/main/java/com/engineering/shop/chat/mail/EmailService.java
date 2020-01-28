package com.engineering.shop.chat.mail;

public interface EmailService {
    void sendSimpleMessage(String to,
                           String subject,
                           String text);
}
