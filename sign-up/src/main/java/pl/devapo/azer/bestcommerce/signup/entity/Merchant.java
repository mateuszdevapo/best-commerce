package pl.devapo.azer.bestcommerce.signup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "SIGN_UP_MERCHANT")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private String name;
    private String ownerName;
    private String address;
    private String phoneNumber;
    private String email;
    private String password;
}
