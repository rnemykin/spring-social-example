package ru.rnemykin.spring.social.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rnemykin.spring.social.entity.User;
import ru.rnemykin.spring.social.repository.UserRepository;

@RestController
@RequiredArgsConstructor
public class IndexController {
    private final UserRepository userRepository;


    @GetMapping("/")
    public User index() {
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("user not found, accountId=" + accountId));
    }

}
