package pl.devapo.azer.bestcommerce.signin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.devapo.azer.bestcommerce.signin.entity.Merchant;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
    Optional<Merchant> findByEmail(String email);
}
