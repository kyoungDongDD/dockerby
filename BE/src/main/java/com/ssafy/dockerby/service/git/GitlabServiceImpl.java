package com.ssafy.dockerby.service.git;

import com.ssafy.dockerby.dto.git.GitTokenRequestDto;
import com.ssafy.dockerby.dto.git.GitTokenResponseDto;
import com.ssafy.dockerby.dto.project.GitConfigDto;
import com.ssafy.dockerby.entity.git.GitlabAccessToken;
import com.ssafy.dockerby.entity.git.GitlabConfig;
import com.ssafy.dockerby.entity.project.Project;
import com.ssafy.dockerby.repository.git.GitlabAccessTokenRepository;
import com.ssafy.dockerby.repository.git.GitlabConfigRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GitlabServiceImpl implements GitlabService {

  private final GitlabAccessTokenRepository tokenRepository;
  private final GitlabConfigRepository configRepository;
  @Override
  public GitlabConfig createConfig(Project project, GitConfigDto configDto) {
    GitlabConfig config = GitlabConfig.from(configDto);

    GitlabAccessToken token = tokenRepository.findById(configDto.getAccessTokenId())
        .orElseThrow(()-> new NotFoundException());

    config.setProject(project);
    config.setToken(token);

    configRepository.save(config);

    return config;
  }

  @Override
  public GitlabConfig updateConfig(Project project, GitConfigDto configDto) {
    GitlabConfig config = configRepository.findByProjectId(project.getId())
        .orElseThrow(() -> new NotFoundException());

    config.update(configDto);

    if(config.getToken().getId() != configDto.getAccessTokenId()) {
      GitlabAccessToken newToken = tokenRepository.findById(configDto.getAccessTokenId())
          .orElseThrow(() -> new NotFoundException());
      config.setToken(newToken);
    }
    return config;
  }

  @Override
  public Optional<GitlabConfig> config(Long projectId) {
    return configRepository.findByProjectId(projectId);
  }

  @Override
  public void deleteConfig(Long proejctId) {
    configRepository.deleteById(proejctId);
  }

  @Override
  public void createToken(GitTokenRequestDto requestDto) {
    GitlabAccessToken token = GitlabAccessToken.from(requestDto);
    tokenRepository.save(token);
  }

  @Override
  public void updateToken(GitTokenRequestDto requestDto) {
    GitlabAccessToken token = tokenRepository.findById(requestDto.getId())
        .orElseThrow(() -> new NotFoundException());
    token.update(requestDto);
  }

  @Override
  public List<GitTokenResponseDto> tokens() {
    List<GitTokenResponseDto> results = new ArrayList<>();
    tokenRepository.findAllByAccessTokenIsNotNull()
        .forEach(value -> results.add(GitTokenResponseDto.from(value)));
    return results;
  }

  @Override
  public GitlabAccessToken token(Long id) {
    return tokenRepository.findById(id)
        .orElseThrow(() -> new NotFoundException());
  }

  @Override
  public void deleteToken(Long id) {
    GitlabAccessToken token = tokenRepository.findById(id)
        .orElseThrow(() -> new NotFoundException());

    token.softDelete();
  }
}


