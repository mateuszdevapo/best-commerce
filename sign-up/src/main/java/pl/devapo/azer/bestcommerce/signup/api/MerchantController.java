package pl.devapo.azer.bestcommerce.signup.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devapo.azer.bestcommerce.signup.model.MerchantDto;
import pl.devapo.azer.bestcommerce.signup.service.MerchantService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "best-commerce/sign-up")
@RequiredArgsConstructor
class MerchantController {
    private final MerchantService merchantService;

    @PostMapping
    public MerchantDto create(@Valid @RequestBody MerchantDto merchantDto) {
        return merchantService.create(merchantDto);
    }
}
