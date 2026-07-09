package com.soft2erp.erp.master.item;

import com.soft2erp.erp.common.exception.BusinessException;
import com.soft2erp.erp.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;

    @Transactional(readOnly = true)
    public List<ItemDto> findItems(ItemSearchCondition condition) {
        return itemMapper.findItems(condition);
    }

    @Transactional(readOnly = true)
    public ItemDto findItem(String itemCode) {
        return itemMapper.findItem(itemCode)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Item not found"));
    }

    @Transactional
    public ItemDto createItem(ItemCreateRequest request) {
        validateUseYn(request.getUseYn());

        if (itemMapper.existsItem(request.getItemCode()) > 0) {
            throw new BusinessException("Item code already exists");
        }

        ItemDto item = new ItemDto();
        item.setItemCode(request.getItemCode());
        item.setItemName(request.getItemName());
        item.setItemTypeCode(request.getItemTypeCode());
        item.setUnitCode(request.getUnitCode());
        item.setSpec(request.getSpec());
        item.setUseYn(request.getUseYn());
        item.setCreatedBy("system");

        itemMapper.insertItem(item);
        return findItem(request.getItemCode());
    }

    @Transactional
    public ItemDto updateItem(String itemCode, ItemUpdateRequest request) {
        validateUseYn(request.getUseYn());
        findItem(itemCode);

        ItemDto item = new ItemDto();
        item.setItemCode(itemCode);
        item.setItemName(request.getItemName());
        item.setItemTypeCode(request.getItemTypeCode());
        item.setUnitCode(request.getUnitCode());
        item.setSpec(request.getSpec());
        item.setUseYn(request.getUseYn());
        item.setUpdatedBy("system");

        itemMapper.updateItem(item);
        return findItem(itemCode);
    }

    @Transactional
    public void deleteItem(String itemCode) {
        findItem(itemCode);
        itemMapper.deleteItem(itemCode);
    }

    private void validateUseYn(String useYn) {
        if (!"Y".equals(useYn) && !"N".equals(useYn)) {
            throw new BusinessException("useYn must be Y or N");
        }
    }
}

