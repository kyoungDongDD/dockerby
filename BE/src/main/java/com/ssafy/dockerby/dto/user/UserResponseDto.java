package com.ssafy.dockerby.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.dockerby.entity.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {
    @JsonProperty(value="id")
    private String principal;
    private String name;
    private String state;

    public void SuccessState() {
        state = "Success";
    }

    public void FailState() {
        state = "Fail";
    }
    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
            .name(user.getName())
            .principal(user.getPrincipal())
            .build();
    }

    @Override
    public String toString() {
        return "UserResponseDto{" +
          "principal='" + principal + '\'' +
          ", name='" + name + '\'' +
          ", state='" + state + '\'' +
          '}';
    }
}
