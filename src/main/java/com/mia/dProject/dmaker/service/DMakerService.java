package com.mia.dProject.dmaker.service;

import com.mia.dProject.dmaker.code.StatusCode;
import com.mia.dProject.dmaker.dto.CreateDeveloper;
import com.mia.dProject.dmaker.dto.DeveloperDetailDto;
import com.mia.dProject.dmaker.dto.DeveloperDto;
import com.mia.dProject.dmaker.dto.EditDeveloper;
import com.mia.dProject.dmaker.entity.Developer;
import com.mia.dProject.dmaker.entity.RetiredDeveloper;
import com.mia.dProject.dmaker.exception.DMakerErrorCode;
import com.mia.dProject.dmaker.exception.DMakerException;
import com.mia.dProject.dmaker.repository.DeveloperRepository;
import com.mia.dProject.dmaker.repository.RetiredDeveloperRepository;
import com.mia.dProject.dmaker.type.DeveloperLevel;
import com.mia.dProject.dmaker.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static com.mia.dProject.dmaker.exception.DMakerErrorCode.*;

@Service
@RequiredArgsConstructor // 자동으로 생성자를 만들어준다.
public class DMakerService {

    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;

    // ACID
    // Atomic
    // Consistency
    // Isolation
    // Durability
    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);

        // business logic start
        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .memberId(request.getMemberId())
                .name(request.getName())
                .age(request.getAge())
                .statusCode(StatusCode.EMPLOYED)
                .build();

        developerRepository.save(developer);
        return CreateDeveloper.Response.fromEntity(developer);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        // business validation

        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer) -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                });
    }

    private void validateDeveloperLevel(DeveloperLevel developerLevel, Integer experienceYears) {
        if (developerLevel == DeveloperLevel.SENIOR
                && experienceYears < 10) {
            throw new DMakerException(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH);
        }

        if (developerLevel == DeveloperLevel.JUNGNIOR
                && (experienceYears < 4 || experienceYears < 10)
        ) {
            throw new DMakerException(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH);
        }

        if (developerLevel == DeveloperLevel.JUNIOR
                && experienceYears > 10) {
            throw new DMakerException(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH);
        }
    }

    public List<DeveloperDto> getAllEmployedDevelopers() {
        return developerRepository.findDevelopersByStatusCodeEquals(StatusCode.EMPLOYED)
                .stream().map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    public DeveloperDetailDto getDeveloper(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }

    @Transactional
    public DeveloperDetailDto updateDeveloper(String memberId, EditDeveloper.Request request) {
        validateEditDeveloperRequest(request, memberId);

        // 해당 memberId 없으면 에러
        Developer developer = developerRepository.findByMemberId(memberId).orElseThrow(
                ()-> new DMakerException(NO_DEVELOPER));

        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());
        developer.setName(request.getName());
        developer.setAge(request.getAge());

        return DeveloperDetailDto.fromEntity(developer);
    }

    private void validateEditDeveloperRequest(EditDeveloper.Request request, String memberId) {
        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());
    }

    @Transactional
    public DeveloperDetailDto deleteDeveloper(String memberId) {
        // 데이터 삭제 X . 상태값 설정
        // EMPLOYED -> RETIRED
        Developer developer = developerRepository.findByMemberId(memberId).
                orElseThrow(()-> new DMakerException(NO_DEVELOPER));
        developer.setStatusCode(StatusCode.RETIRED);

        // Save into RetiredDeveloper
        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(memberId)
                .name(developer.getName())
                .build();

        retiredDeveloperRepository.save(retiredDeveloper);
        return DeveloperDetailDto.fromEntity(developer);
    }
}
