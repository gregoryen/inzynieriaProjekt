package com.engineering.shop.chat;

import com.engineering.shop.users.UserRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/messages")
public class MessageController {

    private MessageRepository messageRepository;
    private MessageValidator messageValidator;
    private Notification notification;

    @Autowired
    public MessageController(MessageRepository messageRepository, MessageValidator messageValidator, Notification notification) {
        this.messageRepository = messageRepository;
        this.messageValidator = messageValidator;
        this.notification = notification;
    }

    @PostMapping
    public Message addMessage(@RequestBody Message message) {
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        String email = token.getName();
        Set<String> roles = token.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toSet());

        if (roles.contains(UserRoleType.ADMIN.name())) {
            message.setSender(null);
            notification.sendNotification(message.getReceiver());
        }
        else
            message.setSender(email);

        if( messageValidator.validate(message))
            return messageRepository.save(message);
        return null;
    }

    @GetMapping
    @RequestMapping("/user/{id}")
    public Iterable<Message> getMessages(@PathVariable("id") String id) {
        Iterable<Message> messagesToUser = messageRepository.getAllByReceiver(id);
        Iterable<Message> messagesFromUser = messageRepository.getAllBySender(id);
        List<Message> messages = new ArrayList<>();
        messagesToUser.forEach(messages::add);
        messagesToUser.forEach(message -> message.setDisplayed(true));
        messagesToUser.forEach(message -> messageRepository.save(message));

        messagesFromUser.forEach(messages::add);

        messages.sort(Comparator.comparing(Message::getDate));

        return messages;
    }

    @GetMapping
    @RequestMapping("/user")
    public Iterable<Message> getUserMessages() {
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        String email = token.getName();
        return getMessages(email);
    }


    @GetMapping
    @RequestMapping("/userRole")
    public String getUserRole() {
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = token.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toSet());

        String role = "";

        if (roles.contains(UserRoleType.ADMIN.name()))
            role = "admin";
        else
            role = "user";

        return role;
    }


    @GetMapping
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping("/users")
    public Iterable<String> getUsers() {
        List<Message> messages = new ArrayList<>();
        messageRepository.findAll().forEach(messages::add);
        return messages.stream().map(Message::getSender).distinct().collect(Collectors.toList());
    }
}

