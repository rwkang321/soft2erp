package com.soft2erp.erp.system.code;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonCodeMapper {

    List<CommonCodeDto> findCommonCodes(CommonCodeSearchCondition condition);
}

