package pl.devapo.azer.bestcommerce.signin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.devapo.azer.bestcommerce.signin.repository.MerchantRepository;

class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private MerchantRepository merchantRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return merchantRepository.findByEmail(username)
                .map(merchant -> new UserPrincipal(merchant))
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
