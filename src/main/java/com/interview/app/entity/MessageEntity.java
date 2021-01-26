package com.interview.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class MessageEntity {

    public static final String KEY_NAME = "message";

    private String content;
    private Date timestamp;

}
