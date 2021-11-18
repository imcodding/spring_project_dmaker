package com.mia.dProject.dmaker.dto;

import com.mia.dProject.dmaker.entity.Developer;
import com.mia.dProject.dmaker.type.DeveloperLevel;
import com.mia.dProject.dmaker.type.DeveloperSkillType;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditDeveloper {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        @NotNull
        private DeveloperLevel developerLevel;

        @NotNull
        private DeveloperSkillType developerSkillType;

        @NotNull
        @Min(0)
        private Integer experienceYears;

        @NotNull
        @Size(min = 2, max = 50, message = "invalid name")
        private String name;

        @NotNull
        @Min(18)
        private Integer age;
    }
}
