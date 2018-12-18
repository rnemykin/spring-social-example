package ru.rnemykin.spring.social.config.env;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.social.vk")
public class VkontakteEnvironment extends CredentialsProperties {
}
