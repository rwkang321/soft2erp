package com.soft2erp.erp.master.item;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCreateRequest {

    @NotBlank
    private String itemCode;

    @NotBlank
    private String itemName;

    @NotBlank
    private String itemTypeCode;

    @NotBlank
    private String unitCode;

    private String spec;
    private String useYn = "Y";
}

