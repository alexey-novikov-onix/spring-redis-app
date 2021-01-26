package com.interview.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.app.entity.MessageEntity;
import com.interview.app.repository.MessageRepository;
import com.interview.app.request.PublishRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {

    public static final String TEST_CONTENT = "testContent";
    public static final String TEST_CONTENT_2 = "testContent2";
    public static final String TEST_CONTENT_3 = "testContent3";
    public static final String TEST_CONTENT_4 = "testContent4";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    void init() {
        this.messageRepository.clear();
    }

    @Test
    public void publish() throws Exception {
        final PublishRequest publishRequest = new PublishRequest();
        publishRequest.setContent(TEST_CONTENT);

        this.mockMvc.perform(post("/publish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(new PublishRequest())))
                .andDo(print())
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(post("/publish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(publishRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.content", is(TEST_CONTENT)));
    }

    @Test
    public void getLast() throws Exception {
        this.mockMvc.perform(get("/getLast")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setContent(TEST_CONTENT);
        messageEntity.setTimestamp(new Date(100));
        this.messageRepository.publish(messageEntity);
        messageEntity = new MessageEntity();
        messageEntity.setContent(TEST_CONTENT_2);
        messageEntity.setTimestamp(new Date(200));
        this.messageRepository.publish(messageEntity);

        this.mockMvc.perform(get("/getLast")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.content", is(TEST_CONTENT_2)));
    }

    @Test
    public void getByTime() throws Exception {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setContent(TEST_CONTENT);
        messageEntity.setTimestamp(new Date(1000));
        this.messageRepository.publish(messageEntity);
        messageEntity = new MessageEntity();
        messageEntity.setContent(TEST_CONTENT_2);
        messageEntity.setTimestamp(new Date(3000));
        this.messageRepository.publish(messageEntity);
        messageEntity = new MessageEntity();
        messageEntity.setContent(TEST_CONTENT_3);
        messageEntity.setTimestamp(new Date(5000));
        this.messageRepository.publish(messageEntity);
        messageEntity = new MessageEntity();
        messageEntity.setContent(TEST_CONTENT_4);
        messageEntity.setTimestamp(new Date(7000));
        this.messageRepository.publish(messageEntity);
        this.mockMvc.perform(get("/getByTime")
                .contentType(MediaType.APPLICATION_JSON)
                .param("start", "1970-01-01T00:00:03.000+00:00")
                .param("end", "1970-01-01T00:00:05.000+00:00")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content", is(TEST_CONTENT_2)))
                .andExpect(jsonPath("$[1].content", is(TEST_CONTENT_3)));
    }

}
