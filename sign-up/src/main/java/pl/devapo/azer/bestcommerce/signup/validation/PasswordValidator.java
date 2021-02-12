package pl.devapo.azer.bestcommerce.signup.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.hasText;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return (validatePassword(email));
    }

    private boolean validatePassword(String email) {
        if (!hasText(email)){
            return false;
        }
        return Pattern.compile(PASSWORD_PATTERN).matcher(email).matches();
    }
}
