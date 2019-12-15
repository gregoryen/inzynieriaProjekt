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

    @Autowired
    public MessageController(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    @PostMapping
    public Message addMessage(@RequestBody Message message){
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        String email = token.getName();
        Set<String> roles = token.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toSet());

        if(roles.contains(UserRoleType.ADMIN.name()))
            message.setSender(null);
        else
            message.setSender(email);

        return messageRepository.save(message);
    }

    @GetMapping
    @RequestMapping("/user/{id}")
    public Iterable<Message> getMessages(@PathVariable("id") String id){
        Iterable<Message> messagesToUser = messageRepository.getAllByReceiver(id);
        Iterable<Message> messagesFromUser = messageRepository.getAllBySender(id);
        List<Message> messages = new ArrayList<>();
        messagesToUser.forEach(messages::add);
        messagesFromUser.forEach(messages::add);

        messages.sort(Comparator.comparing(Message::getDate));
        return messages;
    }

    @GetMapping
    @RequestMapping("/user")
    public Iterable<Message> getUserMessages(){
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        String email = token.getName();
        return getMessages(email);
    }


    @GetMapping
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping("/users")
    public Iterable<String> getUsers(){
        List<Message> messages = new ArrayList<>();
        messageRepository.findAll().forEach(messages::add);
        return messages.stream().map(Message::getSender).distinct().collect(Collectors.toList());
    }
}

