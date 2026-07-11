package com.soft2erp.erp.common.home;

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

@WebMvcTest(HomePageController.class)
@Import(SecurityConfig.class)
class HomePageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("soft2erp 메인 페이지를 반환한다")
    void indexReturnsHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"))
                .andExpect(content().string(containsString("soft2erp 전체 메뉴")))
                .andExpect(content().string(containsString("시스템 관리")))
                .andExpect(content().string(containsString("기준 정보")))
                .andExpect(content().string(containsString("/admin/dev")))
                .andExpect(content().string(containsString("id=\"themeButton\"")));
    }
}
