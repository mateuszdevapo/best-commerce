package pl.devapo.azer.bestcommerce.signup.validation;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {

    private String status;
    private String message;
    private List<ErrorDetails> errors;

    @Data
    @Builder
    public static class ErrorDetails {
        private String fieldName;
        private String message;
    }
}
