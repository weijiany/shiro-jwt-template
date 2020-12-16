package com.example.demo;

import com.example.demo.controller.dto.LoginRequestDto;
import com.example.demo.controller.dto.LoginResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @Autowired
    private TestRestTemplate client;

    @Test
    void should_get_jwt_token_when_login() {
        ResponseEntity<LoginResponseDto> resp =
                client.postForEntity("/login", new LoginRequestDto("1", "1"), LoginResponseDto.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isNotNull();
        assertThat(resp.getBody().getToken()).isNotNull();
    }

    @Test
    void should_use_jwt_token_can_access_hello_api() {
        ResponseEntity<LoginResponseDto> resp =
                client.postForEntity("/login", new LoginRequestDto("1", "1"), LoginResponseDto.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isNotNull();


        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", resp.getBody().getToken());
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> helloResp = client.exchange("/hello", HttpMethod.GET, httpEntity, String.class);

        assertThat(helloResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(helloResp.getBody()).isEqualTo("hello");
    }
}
