package pl.devapo.azer.bestcommerce.signup.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.devapo.azer.bestcommerce.signup.entity.Merchant;
import pl.devapo.azer.bestcommerce.signup.validation.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantDto {

    private Long id;
    private String type;
    private String name;
    private String ownerName;
    private String address;
    private String phoneNumber;
    @NotNull
    @NotEmpty
    @Email
    private String email;
    @NotNull
    @NotEmpty
    @ValidPassword
    private String password;

    public static MerchantDto of(Merchant entity) {
        return MerchantDto.builder()
                .id(entity.getId())
                .type(entity.getType())
                .name(entity.getName())
                .ownerName(entity.getOwnerName())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }
}
