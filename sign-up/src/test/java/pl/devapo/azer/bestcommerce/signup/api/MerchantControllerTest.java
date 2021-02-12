package pl.devapo.azer.bestcommerce.signup.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import pl.devapo.azer.bestcommerce.signup.model.MerchantDto;
import pl.devapo.azer.bestcommerce.signup.service.MerchantService;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MerchantControllerTest extends BaseWebMvcControllerTest {

    @MockBean
    private MerchantService merchantService;

    private final String PATH = "/best-commerce/sign-up";

    @Test
    void should_return_success_when_entity_is_valid() throws Exception {
        mockMvc.perform(post(PATH)
                .content(asString(validDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    void should_return_unprocessable_entity_when_entity_is_not_valid(String email, String password) throws Exception {
        mockMvc.perform(post(PATH)
                .content(asString(mockDto(email, password)))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    private static Stream<Arguments> provideTestData() {
        return Stream.of(
                Arguments.of("test", "test"),
                Arguments.of(null, "test"),
                Arguments.of("test", null),
                Arguments.of(null, null),
                Arguments.of("", ""),
                Arguments.of("test@aaa.com", null),
                Arguments.of(null, "zaq1@WSX")
        );
    }

    private MerchantDto mockDto(String email, String password) {
        return MerchantDto.builder()
                .type("type")
                .name("John Doe")
                .ownerName("Owner")
                .address("NYC")
                .phoneNumber("123456789")
                .email(email)
                .password(password)
                .build();
    }

    private MerchantDto validDto() {
        return MerchantDto.builder()
                .type("type")
                .name("John Doe")
                .ownerName("Owner")
                .address("NYC")
                .phoneNumber("123456789")
                .email("test@gmail.com")
                .password("Zim@2021")
                .build();
    }
}
