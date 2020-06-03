package com.utn.phones.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorResponseDto {

    Date timestamp;
    int code;
    String status;
    String message;
    private String path;
}
