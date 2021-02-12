package pl.devapo.azer.bestcommerce.signup.service;

import pl.devapo.azer.bestcommerce.signup.model.MerchantDto;

public interface EventPublisher {
    void publish(String destination, String message);

    void publish(String destination, MerchantDto merchantDto);
}
