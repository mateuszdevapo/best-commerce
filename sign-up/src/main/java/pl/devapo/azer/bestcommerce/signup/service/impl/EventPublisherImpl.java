package pl.devapo.azer.bestcommerce.signup.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import pl.devapo.azer.bestcommerce.signup.model.MerchantDto;
import pl.devapo.azer.bestcommerce.signup.service.EventPublisher;

@Slf4j
@Service
@RequiredArgsConstructor
class EventPublisherImpl implements EventPublisher {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public void publish(String destination, String message) {
        try {
            jmsTemplate.convertAndSend(destination, message);
            log.debug("Message {} has been sent to {} queue", message, destination);
        } catch (JmsException e) {
            log.error("Could not send message {} to queue {}", message, destination, e);
        }
    }

    public void publish(String destination, MerchantDto merchantDto) {
        publish(destination, toString(merchantDto));
    }

    private <T> String toString(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Could not convert message to String: {}", object, e);
            return null;
        }
    }
}
