package com.soft2erp.erp.master.item;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ItemMapper {

    List<ItemDto> findItems(ItemSearchCondition condition);

    Optional<ItemDto> findItem(@Param("itemCode") String itemCode);

    int existsItem(@Param("itemCode") String itemCode);

    int insertItem(ItemDto item);

    int updateItem(ItemDto item);

    int deleteItem(@Param("itemCode") String itemCode);
}

