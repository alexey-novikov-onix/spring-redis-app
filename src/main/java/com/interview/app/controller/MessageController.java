package com.interview.app.controller;

import com.interview.app.entity.MessageEntity;
import com.interview.app.request.GetByTimeRequest;
import com.interview.app.request.PublishRequest;
import com.interview.app.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public final class MessageController {

    private final MessageService messageService;

    @GetMapping("/")
    public ResponseEntity<Void> index() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("publish")
    public ResponseEntity<MessageEntity> publish(final @Valid @RequestBody PublishRequest publishRequest) {
        return ResponseEntity.ok(this.messageService.publish(publishRequest.getContent()));
    }

    @GetMapping("getLast")
    public ResponseEntity<MessageEntity> getLast() {
        return this.messageService.getLast()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("getByTime")
    public ResponseEntity<List<MessageEntity>> getByTime(final @Valid GetByTimeRequest getByTimeRequest) {
        return ResponseEntity.ok(this.messageService.getByTime(getByTimeRequest.getStart(), getByTimeRequest.getEnd()));
    }

}
