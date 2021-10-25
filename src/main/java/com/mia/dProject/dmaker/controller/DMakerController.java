package com.mia.dProject.dmaker.controller;

import com.mia.dProject.dmaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController // 사용자 요청을 받아서 json 값으로 응답을 내려주겠다 명시
@RequiredArgsConstructor
public class DMakerController {
    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<String> getAllDevelopers() {
        log.info("GET /developers HTTP/1.1");

        return Arrays.asList("mia", "jackson", "tom");
    }

    @GetMapping("/create-developer")
    public List<String> createDevelopers() {
        log.info("GET /create-developer HTTP/1.1");

        dMakerService.createDeveloper();

        return List.of("mia");
    }
}
