package com.soft2erp.erp.system.code;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonCodeDto {

    private String codeGroup;
    private String code;
    private String codeName;
    private String description;
    private String useYn;
    private Integer sortOrder;
    private String extraValue1;
    private String extraValue2;
}

