package com.come1997.backboard.entity;


import com.come1997.backboard.security.MemberRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long memberid;

    @Column(unique = true, length = 100)
    private String username;

    @Column(unique = true, length = 150)
    private String email;

    private String password;

    @CreatedDate
    @Column(name = "regDate", updatable = false)
    private LocalDateTime regDate; // 회원가입일

    @Enumerated(EnumType.STRING)    // Enum 타입이 STRING "ROLE_ADMIN", "ROLE_USER"이기 때문에
    @Column(length = 15)
    private MemberRole memberRole;  // 맴버 역할

}
