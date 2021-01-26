package com.interview.app.service;

import com.interview.app.entity.MessageEntity;
import com.interview.app.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public final class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public MessageEntity publish(final String content) {
        final MessageEntity messageEntity = new MessageEntity();
        messageEntity.setContent(content);
        messageEntity.setTimestamp(new Date());

        this.messageRepository.publish(messageEntity);

        return messageEntity;
    }

    @Override
    public Optional<MessageEntity> getLast() {
        return this.messageRepository.getLast();
    }

    @Override
    public List<MessageEntity> getByTime(final Date start, final Date end) {
        return this.messageRepository.getByTime(start, end);
    }

}
