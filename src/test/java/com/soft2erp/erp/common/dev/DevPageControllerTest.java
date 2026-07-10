package com.soft2erp.erp.common.dev;

import com.soft2erp.erp.common.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(DevPageController.class)
@Import(SecurityConfig.class)
class DevPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("개발 도구 페이지를 반환한다")
    void indexReturnsDevPage() throws Exception {
        mockMvc.perform(get("/admin/dev"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/dev/index"))
                .andExpect(content().string(containsString("품목 마스터")))
                .andExpect(content().string(containsString("id=\"itemForm\"")))
                .andExpect(content().string(containsString("/api/master/items")));
    }
}
