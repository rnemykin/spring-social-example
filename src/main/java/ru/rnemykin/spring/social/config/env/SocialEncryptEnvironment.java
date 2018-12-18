package ru.rnemykin.spring.social.config.env;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("spring.social.encrypt")
public class SocialEncryptEnvironment {
    private String key;
    private String salt;
}
