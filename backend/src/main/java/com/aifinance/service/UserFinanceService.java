package com.aifinance.service;

import com.aifinance.pojo.UserFinance;

public interface UserFinanceService {
    // 根据用户ID查询财务信息
    UserFinance findByUserId(Integer userId);

    // 更新用户财务信息
    void update(UserFinance userFinance);

    void create(UserFinance userFinance);
}
