package com.ssafy.dockerby.core.docker.vo.docker;

public class DockerbyProperty {
  private String type;
  private String host;
  private String container;

  public DockerbyProperty() {
  }

  public DockerbyProperty(String type, String host, String container) {
    this.type = type;
    this.host = host;
    this.container = container;
  }

  public String command() {
    if("publish".equals(type)) {
      return new StringBuilder().append("-p ").append(host).append(":").append(container).toString();
    } else if ("volume".equals(type)) {
      return new StringBuilder().append("-v ").append(host).append(":").append(container).toString();
    } else if ("environment".equals(type)) {
      return new StringBuilder().append("-e ").append(host).append("=").append(container)
          .toString();
    } else {
      throw new IllegalArgumentException(type);
    }
  }

  public String getType() {
    return type;
  }

  public String getHost() {
    return host;
  }

  public String getContainer() {
    return container;
  }

  public void updateContainer(String container) {
    this.container = container;
  }
}
