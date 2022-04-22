package com.ssafy.dockerby.entity.User;

import com.ssafy.dockerby.dto.User.UserDto;
import com.ssafy.dockerby.entity.BaseEntity;
import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 60, unique = true)
    private String principal;

    @NotNull
    private String credential;

    @NotNull
    @Column(length = 60, unique = true)
    private String name;
    //생성 매서드

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.credential = passwordEncoder.encode(this.credential);
    }

    public static User of(UserDto userDto, String encodePassword) {
        return User.builder()
            .id(userDto.getId())
            .credential(encodePassword)
            .name(userDto.getName())
            .principal(userDto.getPrincipal())
            .build();
    }
}