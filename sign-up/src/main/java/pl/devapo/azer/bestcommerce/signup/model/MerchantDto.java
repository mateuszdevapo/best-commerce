package pl.devapo.azer.bestcommerce.signup.model;

import lombok.*;
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
    @NotNull
    @NotEmpty
    private String type;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String ownerName;
    @NotNull
    @NotEmpty
    private String address;
    @NotNull
    @NotEmpty
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
