package ru.rnemykin.spring.social.entity;


import lombok.Data;
import ru.rnemykin.spring.social.entity.enums.OAuthProviderEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@Entity
public class UserAccount {
    @Id
    String id;
    String displayName;
    String firstName;
    String lastName;
    String email;
    String userName;

    @Column(columnDefinition = "CLOB")
    String profileUrl;

    @Column(columnDefinition = "CLOB")
    String imageUrl;

    @Enumerated(EnumType.STRING)
    OAuthProviderEnum provider;
}
