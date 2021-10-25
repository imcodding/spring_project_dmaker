package com.mia.dProject.dmaker.service;

import com.mia.dProject.dmaker.entity.Developer;
import com.mia.dProject.dmaker.repository.DeveloperRepository;
import com.mia.dProject.dmaker.type.DeveloperLevel;
import com.mia.dProject.dmaker.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // 자동으로 생성자를 만들어준다.
public class DMakerService {

    private final DeveloperRepository developerRepository;

    @Transactional
    public void createDeveloper() {
        Developer developer = Developer.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.FRONT_END)
                .experienceYears(2)
                .name("mia")
                .age(27)
                .build();

        developerRepository.save(developer);
    }
}
