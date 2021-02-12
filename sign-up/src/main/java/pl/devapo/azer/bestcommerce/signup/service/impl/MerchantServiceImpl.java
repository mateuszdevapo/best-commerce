package pl.devapo.azer.bestcommerce.signup.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.devapo.azer.bestcommerce.signup.entity.Merchant;
import pl.devapo.azer.bestcommerce.signup.exception.UserAlreadyExistException;
import pl.devapo.azer.bestcommerce.signup.model.MerchantDto;
import pl.devapo.azer.bestcommerce.signup.repository.MerchantRepository;
import pl.devapo.azer.bestcommerce.signup.service.EventPublisher;
import pl.devapo.azer.bestcommerce.signup.service.MerchantService;

@Service
@RequiredArgsConstructor
@Slf4j
class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository merchantRepository;
    private final EventPublisher eventPublisher;
    private final PasswordEncoder encoder;
    @Value("${queue.register-merchant}")
    private String QUEUE_NAME;

    public MerchantDto create(MerchantDto dto) {
        if (merchantExists(dto)) {
            log.error("User with given email already exists");
            throw new UserAlreadyExistException();
        }
        Merchant entity = merchantRepository.save(toEntity(dto));
        MerchantDto merchantDto = MerchantDto.of(entity);
        eventPublisher.publish(QUEUE_NAME, merchantDto);
        return merchantDto;
    }

    private boolean merchantExists(MerchantDto dto) {
        return merchantRepository.findByEmail(dto.getEmail()).isPresent();
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
