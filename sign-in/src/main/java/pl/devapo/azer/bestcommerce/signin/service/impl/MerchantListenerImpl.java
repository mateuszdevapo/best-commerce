package pl.devapo.azer.bestcommerce.signin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import pl.devapo.azer.bestcommerce.signin.model.MerchantDto;
import pl.devapo.azer.bestcommerce.signin.service.MerchantListener;
import pl.devapo.azer.bestcommerce.signin.service.MerchantService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
class MerchantListenerImpl implements MerchantListener {
    private final MerchantService merchantService;
    private final ObjectMapper objectMapper;

    @Override
    @JmsListener(destination = "${queue.register-merchant}")
    public void createMerchantListener(String message) {
        log.debug("Received new message from merchant register queue");
        try {
            merchantService.create(objectMapper.readValue(message, MerchantDto.class));
        } catch (IOException e) {
            log.error("Could not map merchant message: {}", message, e);
        }
    }
}
