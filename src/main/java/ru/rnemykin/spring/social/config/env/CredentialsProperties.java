package ru.rnemykin.spring.social.config.env;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class CredentialsProperties {
    private String clientId;
    private String clientSecret;
}
