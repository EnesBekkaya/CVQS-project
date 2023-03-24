package com.cvqs.terminalservice.dto;

import com.cvqs.terminalservice.model.Section;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data

public class TerminalDto {
    private String name;

    private Boolean active;
    private Date createDate;


    private List<Section> sections;
}
