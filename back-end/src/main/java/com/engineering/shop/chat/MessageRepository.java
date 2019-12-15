package com.engineering.shop.chat;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

    Iterable<Message> getAllBySender(long sender);

    Iterable<Message> getAllByReceiver(long receiver);
}
