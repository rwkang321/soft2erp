package com.soft2erp.erp.system.code;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonCodeService {

    private final CommonCodeMapper commonCodeMapper;

    public List<CommonCodeDto> findCommonCodes(CommonCodeSearchCondition condition) {
        return commonCodeMapper.findCommonCodes(condition);
    }
}

