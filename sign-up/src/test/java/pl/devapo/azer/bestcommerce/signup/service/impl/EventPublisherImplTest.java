package pl.devapo.azer.bestcommerce.signup.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import pl.devapo.azer.bestcommerce.signup.model.MerchantDto;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class EventPublisherImplTest {
    @MockBean
    private JmsTemplate jmsTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private EventPublisherImpl eventPublisher;

    @BeforeEach
    void setup() {
        eventPublisher = new EventPublisherImpl(jmsTemplate, objectMapper);
    }

    @Test
    void should_publish_message_when_invoke() {
        eventPublisher.publish("dest", mockDto());

        verify(jmsTemplate, times(1)).convertAndSend(anyString(), anyString());
    }

    private MerchantDto mockDto() {
        return MerchantDto.builder()
                .email("test@test.com")
                .build();
    }
}
