package com.engineering.shop.chat;

import com.engineering.shop.chat.mail.EmailServiceImpl;
import com.engineering.shop.common.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component("notification")
public class Notification {

    private EmailServiceImpl emailService;
    private MessageRepository messageRepository;

    @Autowired
    public Notification(EmailServiceImpl emailService, MessageRepository messageRepository) {
        this.emailService = emailService;
        this.messageRepository = messageRepository;
    }

    public void setNotification(long messageId){
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        var val = messageRepository.findById(messageId);
                        String reciver = val.filter(Predicate.not(Message::isDisplayed)).map(Message::getReceiver).orElseThrow(ResourceNotFoundException::new);
                        sendNotification(reciver);
                    }
                },
                30000 // 900000 - 15 minut
        );
    }

    public boolean sendNotification(String email){
        emailService.sendSimpleMessage(email, "Dostałeś odpowiedź od pracownika", "Dostałeś odpowiedź od pracownika");
        return true;
    }
}
