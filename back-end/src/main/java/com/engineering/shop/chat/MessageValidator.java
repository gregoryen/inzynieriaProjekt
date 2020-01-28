package com.engineering.shop.chat;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component("messageValidator")
public class MessageValidator {

    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    public boolean validate(Message message){

        Pattern pat = Pattern.compile(emailRegex);
        if((message.getReceiver() == null && message.getSender() == null) || (message.getReceiver() != null && message.getSender() != null))
            return false;

        if(message.getReceiver() != null && !pat.matcher(message.getReceiver()).matches())
            return false;

        if(message.getText() == null || message.getText() == "")
            return false;

        return true;
    }
}
