package com.interview.app.service;

import com.interview.app.entity.MessageEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MessageService {

    MessageEntity publish(String content);

    Optional<MessageEntity> getLast();

    List<MessageEntity> getByTime(Date start, Date end);

}
