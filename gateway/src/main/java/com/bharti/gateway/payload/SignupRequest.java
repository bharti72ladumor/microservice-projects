package com.bharti.gateway.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SignupRequest {

    @NotNull(message = "null")
    @NotBlank(message = "blank")
    @NotEmpty(message = "empty")
    private String userName;

    @NotEmpty(message = "Password is required")
    private String password;

    private List<String> roles;
}
