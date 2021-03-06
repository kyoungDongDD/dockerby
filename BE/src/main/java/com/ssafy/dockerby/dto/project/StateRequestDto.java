package com.ssafy.dockerby.dto.project;

import com.ssafy.dockerby.entity.project.enums.BuildType;
import com.ssafy.dockerby.entity.project.enums.StateType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StateRequestDto {

  @NonNull
  private Long projectId;

  @Enumerated(value = EnumType.STRING)
  private BuildType buildType;

  @Override
  public String toString() {
    return "StateRequestDto{" +
      "projectId=" + projectId +
      ", buildType=" + buildType +
      '}';
  }
}
