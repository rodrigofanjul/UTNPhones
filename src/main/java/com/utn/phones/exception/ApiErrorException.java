package com.utn.phones.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiErrorException extends RuntimeException {

    public ApiErrorException(Integer apiCode) {
        super("An error has ocurred with the api connection.");
    }
}
