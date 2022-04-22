package com.ssafy.dockerby.entity.project.states;


import com.ssafy.dockerby.entity.BaseEntity;
import com.ssafy.dockerby.entity.project.ProjectState;
import com.ssafy.dockerby.entity.project.enums.StateType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DockerRun extends BaseEntity  {

  @Id
  @Column(name = "docker_run_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  private StateType stateType;

  @OneToOne
  @JoinColumn(name = "project_state_id")
  private ProjectState projectState;

  public void updateProjectState(ProjectState projectState){
    this.projectState = projectState;
  }

}
