package com.utn.phones.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorResponseDto {

    Date timestamp;
    int code;
    String message;
    private String path;
}
