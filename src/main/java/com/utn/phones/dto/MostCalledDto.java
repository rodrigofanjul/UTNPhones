package com.utn.phones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class MostCalledDto {
    Long destination;
    Long calls;
    Long seconds;
}
