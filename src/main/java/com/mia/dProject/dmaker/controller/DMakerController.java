package com.mia.dProject.dmaker.controller;

import com.mia.dProject.dmaker.dto.CreateDeveloper;
import com.mia.dProject.dmaker.dto.DeveloperDetailDto;
import com.mia.dProject.dmaker.dto.DeveloperDto;
import com.mia.dProject.dmaker.dto.EditDeveloper;
import com.mia.dProject.dmaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController // 사용자 요청을 받아서 json 값으로 응답을 내려주겠다 명시
@RequiredArgsConstructor
public class DMakerController {
    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {
        // Developer 쓰지 않고 Dto 사용하는 것 권장.
        // entity 와 응답 데이터를 분리해주는게 유연성 좋음.
        log.info("GET /developers HTTP/1.1");

        return dMakerService.getAllEmployedDevelopers();
    }

    @GetMapping("/developers/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(
            @PathVariable String memberId
    ) {
        log.info("GET /developers HTTP/1.1");

        return dMakerService.getDeveloper(memberId);
    }

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers(
            @Valid @RequestBody CreateDeveloper.Request request
            // request validation
            ) {
        log.info("request : {}", request);

        return dMakerService.createDeveloper(request);
    }

    @PutMapping("/developers/{memberId}")
    public DeveloperDetailDto editDeveloperDetail(
            @PathVariable String memberId,
            @Valid @RequestBody EditDeveloper.Request request
    ) {
        log.info("GET /developers HTTP/1.1");

        return dMakerService.updateDeveloper(memberId, request);
    }

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto delDeveloper(
            @PathVariable String memberId
    ) {
        return dMakerService.deleteDeveloper(memberId);
    }
}
