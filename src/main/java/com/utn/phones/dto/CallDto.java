package com.utn.phones.dto;

import com.utn.phones.model.Phoneline;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CallDto {
    Phoneline destination;
}
