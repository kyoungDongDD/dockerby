package com.ssafy.dockerby.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.ssafy.dockerby.dto.UserDto;
import com.ssafy.dockerby.entity.User;
import com.ssafy.dockerby.service.UserService;
import com.ssafy.dockerby.util.FileMannagerUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserControllerTest {


    @Autowired
    UserController userController;

    @Test
    public void 회원가입() throws Exception ,ExecutionException, InterruptedException, FileNotFoundException {
        // given
        UserDto userDto = UserDto.builder()
            .id(0L)
            .credential("아이디")
            .principal("비밀번호")
            .name("김민현")
            .build();
        // when
        UserDto result1 = userController.signUp(userDto);
        // then
        Assertions.assertThat(result1.getPrincipal()).isEqualTo(userDto.getPrincipal());
        Assertions.assertThat(result1.getCredential()).isEqualTo(userDto.getCredential());
        Assertions.assertThat(result1.getName()).isEqualTo(userDto.getName());

    }



}