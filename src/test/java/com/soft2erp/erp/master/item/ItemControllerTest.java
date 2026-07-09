package com.soft2erp.erp.master.item;

import com.soft2erp.erp.common.config.SecurityConfig;
import com.soft2erp.erp.common.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @Test
    @DisplayName("품목 목록을 조회한다")
    void findItemsReturnsItems() throws Exception {
        ItemDto item = item("ITEM001", "테스트 품목");

        when(itemService.findItems(any(ItemSearchCondition.class))).thenReturn(List.of(item));

        mockMvc.perform(get("/api/master/items")
                        .param("itemName", "테스트"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].itemCode").value("ITEM001"))
                .andExpect(jsonPath("$.data[0].itemName").value("테스트 품목"));
    }

    @Test
    @DisplayName("품목 단건을 조회한다")
    void findItemReturnsItem() throws Exception {
        when(itemService.findItem("ITEM001")).thenReturn(item("ITEM001", "테스트 품목"));

        mockMvc.perform(get("/api/master/items/{itemCode}", "ITEM001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.itemCode").value("ITEM001"))
                .andExpect(jsonPath("$.data.itemName").value("테스트 품목"));
    }

    @Test
    @DisplayName("품목을 생성한다")
    void createItemReturnsCreatedItem() throws Exception {
        when(itemService.createItem(any(ItemCreateRequest.class))).thenReturn(item("ITEM001", "테스트 품목"));

        mockMvc.perform(post("/api/master/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "itemCode": "ITEM001",
                                  "itemName": "테스트 품목",
                                  "itemTypeCode": "RAW",
                                  "unitCode": "EA",
                                  "useYn": "Y"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Created"))
                .andExpect(jsonPath("$.data.itemCode").value("ITEM001"));
    }

    @Test
    @DisplayName("품목을 수정한다")
    void updateItemReturnsUpdatedItem() throws Exception {
        when(itemService.updateItem(eq("ITEM001"), any(ItemUpdateRequest.class)))
                .thenReturn(item("ITEM001", "수정 품목"));

        mockMvc.perform(put("/api/master/items/{itemCode}", "ITEM001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "itemName": "수정 품목",
                                  "itemTypeCode": "RAW",
                                  "unitCode": "EA",
                                  "useYn": "Y"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Updated"))
                .andExpect(jsonPath("$.data.itemName").value("수정 품목"));
    }

    @Test
    @DisplayName("품목을 삭제한다")
    void deleteItemReturnsDeletedMessage() throws Exception {
        doNothing().when(itemService).deleteItem("ITEM001");

        mockMvc.perform(delete("/api/master/items/{itemCode}", "ITEM001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Deleted"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @DisplayName("품목 생성 필수값이 없으면 검증 오류를 반환한다")
    void createItemRejectsInvalidRequest() throws Exception {
        mockMvc.perform(post("/api/master/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "itemCode": "",
                                  "itemName": "",
                                  "itemTypeCode": "",
                                  "unitCode": ""
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    private static ItemDto item(String itemCode, String itemName) {
        ItemDto item = new ItemDto();
        item.setItemCode(itemCode);
        item.setItemName(itemName);
        item.setItemTypeCode("RAW");
        item.setUnitCode("EA");
        item.setUseYn("Y");
        return item;
    }
}
