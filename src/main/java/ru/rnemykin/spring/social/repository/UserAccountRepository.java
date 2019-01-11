package ru.rnemykin.spring.social.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rnemykin.spring.social.entity.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, String> {
}
