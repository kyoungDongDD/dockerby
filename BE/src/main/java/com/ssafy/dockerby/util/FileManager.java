package com.ssafy.dockerby.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class FileManager {

  //Json 파일 저장
  public static <T> void saveJsonFile(String filePath, String fileName, T file)
      throws IOException {
    log.info("saveJsonFile Start : filePath = {} , fileName = {}", filePath, fileName);
    ObjectMapper mapper = new ObjectMapper();
    //저장 경로
    String savePath = makePath(filePath, "/", fileName);
    //저장 경로 확인
    checkAndMakeDir(filePath);
    //파일 저장
    mapper.writeValue(new File(savePath), file);
    log.info("saveJsonFile Done");
  }

  //type으로 반환함
  public static <T> T loadJsonFile(String filePath, String fileName, Class<T> type)
      throws IOException {
    log.info("loadJsonFile Start : filePath = {} , fileName = {}", filePath, fileName);
    ObjectMapper mapper = new ObjectMapper();
    File file = new File(makePath(filePath, "/", fileName));
    if (file.exists()) {
      return mapper.readValue(file, type);
    }
    log.error("not found {}", fileName);
    throw new FileNotFoundException();

  }

  public static <T> List<T> loadJsonFileToList(String filePath, String fileName, Class<T> type)
      throws IOException {
    log.info("start load Json: {} {} {}", filePath, fileName, type.toString());
    ObjectMapper mapper = new ObjectMapper();
    File file = new File(makePath(filePath, "/", fileName));
    if (file.exists()) {
      return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, type));
    }
    log.error("not found {}", fileName);
    throw new FileNotFoundException();
  }

  //String type 문자열 파일 저장
  public static void saveFile(String filePath, String fileName, String str)
      throws IOException {
    // 폴더 경로 체크
    log.info("saveFile Start : filePath = {} , fileName = {}", filePath, fileName);

    checkAndMakeDir(filePath);
    String savePath = makePath(filePath, "/", fileName);
    BufferedWriter writer = new BufferedWriter(new FileWriter(savePath));
    writer.write(str);
    writer.close();
    log.info("saveFile Done : success read : {} ", fileName);

  }

  //일반 파일 String으로 반환함
  public static String loadFile(String filePath, String fileName) throws IOException {
    log.info("loadFile Start : filePath = {} , fileName = {}", filePath, fileName);
    String loadPath = makePath(filePath, "/", fileName);
    byte[] bytes = Files.readAllBytes(Paths.get(loadPath));
    log.info("loadFile Done :  {} ", new String(bytes));
    return new String(bytes);
  }

  //배열 타입 문자열 저장
  public static void saveFile(String filePath, String fileName, List<String> strs)
      throws IOException {
    // 폴더 경로 체크
    log.info("start save ListFile {} {}", filePath, fileName);
    checkAndMakeDir(filePath);
    String savePath = makePath(filePath, "/", fileName);
    BufferedWriter writer = new BufferedWriter(new FileWriter(savePath));
    for (String str : strs) {
      writer.append(str + "\n");
    }
    writer.close();
  }

  //파일 저장 경로 생성
  private static String makePath(String... strs) {
    log.info("makePath Start");
    StringBuilder savePath = new StringBuilder();
    for (String str : strs) {
      savePath.append(str);
      log.info("makePath Done : savePath = {}",savePath.toString());
    }
    return savePath.toString();
  }

  public static void checkAndMakeDir(String filePath) {
    log.info("checkAndMakeDir Start : filePath = {}",filePath);

    //폴더가 없을시 생성
    log.info("start checkAndMakeDir {} {}", filePath);
    if (!new File(filePath).exists()) {
      new File(filePath).mkdirs();
      log.info("checkAndMakeDir : direction created : {}",filePath);
    }
    log.info("checkAndMakeDir Done : filePath = {}",filePath);
  }

}
