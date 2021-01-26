package com.interview.app.repository;

import com.interview.app.entity.MessageEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MessageRepository {

    void publish(MessageEntity messageEntity);

    Optional<MessageEntity> getLast();

    List<MessageEntity> getByTime(Date start, Date end);

    void clear();

}
