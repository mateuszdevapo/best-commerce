package pl.devapo.azer.bestcommerce.signup.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.devapo.azer.bestcommerce.signup.entity.Merchant;
import pl.devapo.azer.bestcommerce.signup.exception.UserAlreadyExistException;
import pl.devapo.azer.bestcommerce.signup.model.MerchantDto;
import pl.devapo.azer.bestcommerce.signup.repository.MerchantRepository;
import pl.devapo.azer.bestcommerce.signup.service.EventPublisher;
import pl.devapo.azer.bestcommerce.signup.service.MerchantService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class MerchantServiceImplTest {


    @MockBean
    private MerchantRepository merchantRepository;
    @MockBean
    private EventPublisher eventPublisher;
    @MockBean
    private PasswordEncoder encoder;

    private MerchantService merchantService;

    @BeforeEach
    void setup() {
        merchantService = new MerchantServiceImpl(merchantRepository, eventPublisher, encoder);
    }

    @Test
    void should_create_and_publish_merchant_when_do_not_exists() {
        doReturn(Optional.empty()).when(merchantRepository).findByEmail(anyString());
        doReturn(toEntity(mockDto())).when(merchantRepository).save(any(Merchant.class));
        doReturn("hashedPassword").when(encoder).encode(mockDto().getPassword());

        MerchantDto dto = merchantService.create(mockDto());

        assertEquals(mockDto().getEmail(), dto.getEmail());
        verify(eventPublisher, times(1)).publish(any(), any(MerchantDto.class));
    }

    @Test
    void should_throw_exception_when_merchant_exists() {
        doReturn(Optional.of(mockEntity())).when(merchantRepository).findByEmail(anyString());

        Assertions.assertThrows(UserAlreadyExistException.class, () -> merchantService.create(mockDto()));
    }

    private Merchant mockEntity() {
        return toEntity(mockDto());
    }


    private MerchantDto mockDto() {
        return MerchantDto.builder()
                .email("test@test.com")
                .build();
    }

    private Merchant toEntity(MerchantDto dto) {
        return new Merchant(
                dto.getId(),
                dto.getType(),
                dto.getName(),
                dto.getOwnerName(),
                dto.getAddress(),
                dto.getPhoneNumber(),
                dto.getEmail(),
                encoder.encode(dto.getPassword()));
    }
}
