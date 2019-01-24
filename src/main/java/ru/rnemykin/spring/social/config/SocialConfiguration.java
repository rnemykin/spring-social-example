package ru.rnemykin.spring.social.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.vkontakte.connect.VKontakteConnectionFactory;
import org.springframework.social.yandex.config.env.YandexEnvironment;
import org.springframework.social.yandex.connect.YandexConnectionFactory;
import ru.rnemykin.spring.social.config.env.FacebookEnvironment;
import ru.rnemykin.spring.social.config.env.SocialEncryptEnvironment;
import ru.rnemykin.spring.social.config.env.VkontakteEnvironment;

import javax.sql.DataSource;

import static org.springframework.security.crypto.encrypt.Encryptors.noOpText;

@EnableSocial
@Configuration
@RequiredArgsConstructor
public class SocialConfiguration implements SocialConfigurer {
    private final DataSource dataSource;
    private final FacebookEnvironment fbEnv;
    private final VkontakteEnvironment vkEnv;
    private final SocialEncryptEnvironment encryptEnv;
    private final ConnectionSignUp connectionSignUpService;


    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionCfg, Environment env) {
        connectionCfg.addConnectionFactory(new FacebookConnectionFactory(fbEnv.getClientId(), fbEnv.getClientSecret()));
        connectionCfg.addConnectionFactory(new VKontakteConnectionFactory(vkEnv.getClientId(), vkEnv.getClientSecret()));
        connectionCfg.addConnectionFactory(new YandexConnectionFactory(yandexEnvironment()));
    }

    @Bean
    @ConfigurationProperties("spring.social.yandex")
    public YandexEnvironment yandexEnvironment() {
        return new YandexEnvironment();
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator locator) {
        JdbcUsersConnectionRepository connectionRepository = new JdbcUsersConnectionRepository(dataSource, locator, noOpText());
        connectionRepository.setConnectionSignUp(connectionSignUpService);
        return connectionRepository;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator locator, UsersConnectionRepository repository) {
        return new ProviderSignInUtils(locator, repository);
    }

}
