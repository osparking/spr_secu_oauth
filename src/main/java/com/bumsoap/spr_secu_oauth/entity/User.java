package com.bumsoap.spr_secu_oauth.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role; //ADMIN, MANAGER, USER

    private String provider; //어떤 OAuth인지(google, naver 등)
    private String provideId; // 해당 OAuth 의 key(id)

    private LocalDateTime createDate;

    @Builder
    public User(String username, String password, String email, Role role,
                String provider, String provideId, LocalDateTime createDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.provideId = provideId;
        this.createDate = createDate;
    }
}
