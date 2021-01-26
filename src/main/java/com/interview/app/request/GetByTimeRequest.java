package com.interview.app.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Getter
@Setter
public final class GetByTimeRequest {

    @DateTimeFormat(iso = DATE_TIME)
    @NotNull
    private Date start;
    @DateTimeFormat(iso = DATE_TIME)
    @NotNull
    private Date end;

}
