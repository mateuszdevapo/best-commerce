package pl.devapo.azer.bestcommerce.signin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.devapo.azer.bestcommerce.signin.entity.Merchant;
import pl.devapo.azer.bestcommerce.signin.exception.UserAlreadyExistException;
import pl.devapo.azer.bestcommerce.signin.model.MerchantDto;
import pl.devapo.azer.bestcommerce.signin.repository.MerchantRepository;
import pl.devapo.azer.bestcommerce.signin.service.MerchantService;

@Service
@RequiredArgsConstructor
@Slf4j
class MerchantServiceImpl implements MerchantService {
    private final MerchantRepository merchantRepository;

    public MerchantDto create(MerchantDto dto) {
        if (merchantExists(dto)) {
            log.error("User with given email already exists");
            throw new UserAlreadyExistException();
        }
        Merchant entity = merchantRepository.save(toEntity(dto));
        return MerchantDto.of(entity);
    }

    private boolean merchantExists(MerchantDto dto) {
        return merchantRepository.findByEmail(dto.getEmail()).isPresent();
    }

    private Merchant toEntity(MerchantDto dto) {
        return new Merchant(
                dto.getId(),
                dto.getEmail(),
                dto.getPassword());
    }
}
