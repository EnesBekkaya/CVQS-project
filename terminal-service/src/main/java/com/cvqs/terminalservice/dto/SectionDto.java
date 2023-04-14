package com.cvqs.terminalservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SectionDto {
    @NotNull(message = "name alanı girilmek zorundadır")
    @NotEmpty(message = "name alanı boş bırakılamaz")
    private String name;
}
