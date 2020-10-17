package com.anotes.init;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("default.admin")
@Data
@NoArgsConstructor
public class DefaultAdminProperties {

    private Boolean createDefault;
    private String nickname;
    private String password;
}
