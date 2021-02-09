package pl.devapo.azer.bestcommerce.signup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.devapo.azer.bestcommerce.signup.entity.Merchant;

import java.util.List;
import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
    Optional<Merchant> findByEmail(String email);
}
