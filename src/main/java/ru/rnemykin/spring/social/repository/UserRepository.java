package ru.rnemykin.spring.social.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rnemykin.spring.social.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByAccountId(String accountId);
}
