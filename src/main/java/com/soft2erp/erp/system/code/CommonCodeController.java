package com.soft2erp.erp.system.code;

import com.soft2erp.erp.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/system/common-codes")
@RequiredArgsConstructor
public class CommonCodeController {

    private final CommonCodeService commonCodeService;

    @GetMapping
    public ApiResponse<List<CommonCodeDto>> findCommonCodes(CommonCodeSearchCondition condition) {
        return ApiResponse.ok(commonCodeService.findCommonCodes(condition));
    }
}

