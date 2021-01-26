package com.interview.app.repository;

import com.interview.app.entity.MessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public final class MessageRepositoryImpl implements MessageRepository {

    private final RedisTemplate<String, MessageEntity> redisTemplate;

    @Override
    public void publish(final MessageEntity messageEntity) {
        this.redisTemplate.opsForZSet()
                .add(MessageEntity.KEY_NAME, messageEntity, messageEntity.getTimestamp().getTime());
    }

    @Override
    public Optional<MessageEntity> getLast() {
        final Set<MessageEntity> set = this.redisTemplate.opsForZSet()
                .reverseRange(MessageEntity.KEY_NAME, 0, 0);

        if (Objects.nonNull(set) && !set.isEmpty()) {
            return Optional.of(set.iterator().next());
        } else {
            return  Optional.empty();
        }
    }

    @Override
    public List<MessageEntity> getByTime(final Date start, final Date end) {
        final Set<MessageEntity> set = this.redisTemplate.opsForZSet()
                .rangeByScore(MessageEntity.KEY_NAME, start.getTime(), end.getTime());

        if (Objects.isNull(set)) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(set);
        }
    }

    @Override
    public void clear() {
        this.redisTemplate.delete(MessageEntity.KEY_NAME);
    }

}
