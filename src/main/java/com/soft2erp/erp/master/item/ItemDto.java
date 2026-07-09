package com.soft2erp.erp.master.item;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ItemDto {

    private String itemCode;
    private String itemName;
    private String itemTypeCode;
    private String unitCode;
    private String spec;
    private String useYn;
    private OffsetDateTime createdAt;
    private String createdBy;
    private OffsetDateTime updatedAt;
    private String updatedBy;
}

