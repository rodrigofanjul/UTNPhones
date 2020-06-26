package com.utn.phones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MostCalledDto {
    Long destination;
    Long calls;
    Long seconds;
}
