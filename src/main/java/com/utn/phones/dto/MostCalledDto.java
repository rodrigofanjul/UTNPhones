package com.utn.phones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MostCalledDto {
    Long id;
    Long calls;
    Long seconds;
}
