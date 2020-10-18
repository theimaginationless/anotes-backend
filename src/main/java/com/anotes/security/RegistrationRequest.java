package com.anotes.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    @NotEmpty
    private String nickname;
    @NotEmpty
    private String password;
    private String fullName;
}
