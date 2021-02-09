package pl.devapo.azer.bestcommerce.signin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.devapo.azer.bestcommerce.signin.model.MerchantDto;
import pl.devapo.azer.bestcommerce.signin.service.MerchantService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MerchantListenerImplTest {
    @Mock
    private MerchantService merchantService;
    @Mock
    private ObjectMapper objectMapper;

    private MerchantListenerImpl listener;

    @BeforeEach
    void setUp() {
        listener = new MerchantListenerImpl(merchantService, objectMapper);
    }

    @Test
    void should_process_when_invoke() {
        listener.createMerchantListener(asString(mockDto()));

        verify(merchantService, times(1)).create(any());
    }

    private MerchantDto mockDto() {
        return MerchantDto.builder()
                .email("test@test.com")
                .build();
    }

    private String asString(Object value) {
        try {
            return new ObjectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
