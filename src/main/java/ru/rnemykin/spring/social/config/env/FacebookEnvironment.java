package ru.rnemykin.spring.social.config.env;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.social.facebook")
public class FacebookEnvironment extends CredentialsProperties {
}
