package com.engineering.shop.chat;

import com.engineering.shop.chat.mail.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("notification")
public class Notification {

    @Autowired
    private EmailServiceImpl emailService;

    public boolean sendNotification(String email){
        emailService.sendSimpleMessage(email, "Dostałeś odpowiedź od pracownika", "Dostałeś odpowiedź od pracownika");
        return true;
    }
}
