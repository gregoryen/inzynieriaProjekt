package com.engineering.shop.chat;

import com.engineering.shop.users.UserRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    public ResponseEntity addMessage(@RequestBody Message message) {
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        String email = token.getName();
        Set<String> roles = token.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        if (roles.contains("PRIVILEGE_COMMUNICATOR_ADMIN"))
            message.setSender(null);
        else if(roles.contains("PRIVILEGE_COMMUNICATOR_CLIENT"))
            message.setSender(email);
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); //400


        if( messageValidator.validate(message) ) {
            Message savedMessage = messageRepository.save(message);
            if(roles.contains("PRIVILEGE_COMMUNICATOR_ADMIN"))
                notification.setNotification(savedMessage.getId());
            return ResponseEntity.ok().body(savedMessage); //200
        }
        return ResponseEntity.badRequest().body("Wrong message"); //400
    }

    @GetMapping
    @RequestMapping("/user/{id}")
    public Iterable<Message> getMessages(@PathVariable("id") String id) {
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        String email = token.getName();
        Set<String> roles = token.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        Iterable<Message> messagesToUser = messageRepository.getAllByReceiver(id);
        Iterable<Message> messagesFromUser = messageRepository.getAllBySender(id);
        List<Message> messages = new ArrayList<>();
        messagesToUser.forEach(messages::add);
        messagesFromUser.forEach(messages::add);
        if(roles.contains("PRIVILEGE_COMMUNICATOR_CLIENT")) {
            messagesToUser.forEach(message -> message.setDisplayed(true));
            messagesToUser.forEach(message -> messageRepository.save(message));
        }
        else if(roles.contains("PRIVILEGE_COMMUNICATOR_ADMIN")){
            messagesFromUser.forEach(message -> message.setDisplayed(true));
            messagesFromUser.forEach(message -> messageRepository.save(message));
        }
        else
            return null;

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
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping("/users")
    public Iterable<String> getUsers() {
        List<Message> messages = new ArrayList<>();
        messageRepository.findAll().forEach(messages::add);
        return messages.stream().map(Message::getSender).distinct().collect(Collectors.toList());
    }
}

