package com.mia.dProject.dmaker.service;

import com.mia.dProject.dmaker.dto.CreateDeveloper;
import com.mia.dProject.dmaker.entity.Developer;
import com.mia.dProject.dmaker.exception.DMakerErrorCode;
import com.mia.dProject.dmaker.exception.DMakerException;
import com.mia.dProject.dmaker.repository.DeveloperRepository;
import com.mia.dProject.dmaker.type.DeveloperLevel;
import com.mia.dProject.dmaker.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

import static com.mia.dProject.dmaker.exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.mia.dProject.dmaker.exception.DMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH;

@Service
@RequiredArgsConstructor // 자동으로 생성자를 만들어준다.
public class DMakerService {

    private final DeveloperRepository developerRepository;

    // ACID
    // Atomic
    // Consistency
    // Isolation
    // Durability
    @Transactional
    public void createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);
        Developer developer = Developer.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.FRONT_END)
                .experienceYears(2)
                .name("mia")
                .age(27)
                .build();

        developerRepository.save(developer);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        // business validation

        if (request.getDeveloperLevel() == DeveloperLevel.SENIOR
                && request.getExperienceYears() < 10) {
            throw new DMakerException(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH);
        }

        if (request.getDeveloperLevel() == DeveloperLevel.JUNGNIOR
                && (request.getExperienceYears() > 4
                || request.getExperienceYears() < 10)
        ) {
            throw new DMakerException(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH);
        }

        if (request.getDeveloperLevel() == DeveloperLevel.JUNIOR
                && request.getExperienceYears() > 10) {
            throw new DMakerException(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH);
        }

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer) -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                });

    }
}
