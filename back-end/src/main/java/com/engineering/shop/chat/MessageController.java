package com.engineering.shop.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@RestController
@RequestMapping("/messages")
public class MessageController {

    private MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @PostMapping
    public Message addMessage(@RequestBody Message message){
        return messageRepository.save(message);
    }

    @GetMapping
    @RequestMapping("/user/{id}")
    public Iterable<Message> getMessages(@PathVariable("id") long id){
        Iterable<Message> messagesToUser = messageRepository.getAllByReceiver(id);
        Iterable<Message> messagesFromUser = messageRepository.getAllBySender(id);
        List<Message> messages = new ArrayList<>();
        messagesToUser.forEach(messages::add);
        messagesFromUser.forEach(messages::add);

        messages.sort(Comparator.comparing(Message::getDate));
        return messages;
    }
}

