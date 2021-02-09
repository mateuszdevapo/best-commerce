package pl.devapo.azer.bestcommerce.signin.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.devapo.azer.bestcommerce.signin.entity.Merchant;
import pl.devapo.azer.bestcommerce.signin.exception.UserAlreadyExistException;
import pl.devapo.azer.bestcommerce.signin.model.MerchantDto;
import pl.devapo.azer.bestcommerce.signin.repository.MerchantRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class MerchantServiceImplTest {
    @Mock
    private MerchantRepository merchantRepository;

    private MerchantServiceImpl merchantService;

    @BeforeEach
    void setUp() {
        merchantService = new MerchantServiceImpl(merchantRepository);
    }

    @Test
    void should_create_merchant_when_do_not_exists() {
        doReturn(toEntity(mockDto())).when(merchantRepository).save(any(Merchant.class));
        doReturn(Optional.empty()).when(merchantRepository).findByEmail(anyString());

        MerchantDto dto = merchantService.create(mockDto());

        assertEquals(mockDto().getEmail(), dto.getEmail());
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
                dto.getEmail(),
                dto.getPassword());
    }
}
