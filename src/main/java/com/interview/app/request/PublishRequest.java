package com.interview.app.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public final class PublishRequest {

    @NotBlank
    private String content;

}
