package com.mia.dProject.dmaker.dto;

import com.mia.dProject.dmaker.code.DMakerErrorCode;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DMakerErrorResponse {
    private DMakerErrorCode errorCode;
    private String errorMessage;


}
