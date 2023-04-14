package com.cvqs.terminalservice.dto;

import com.cvqs.terminalservice.model.Section;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data

public class TerminalDto {
    @NotNull(message = "name alanı girilmek zorundadır")
    @NotEmpty(message = "name alanı boş bırakılamaz")
    private String name;

    private Boolean active;

    private Date createDate;

    @NotNull(message = "sections değeri null olamaz")
    @Size(min = 1,message = "en az 1 adet section girilmedilir.")
    @Valid
    private List<Section> sections;
}
