package com.aifinance.controller;

import com.aifinance.pojo.Result;
import com.aifinance.pojo.UserFinance;
import com.aifinance.service.UserFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/finance")
public class UserFinanceController {

    @Autowired
    private UserFinanceService userFinanceService;

    // 更新用户财务信息 - 接收 JSON
    @PostMapping("/update")
    public ResponseEntity<?> updateFinance(@RequestBody UserFinance userFinance) {
        // 逻辑：更新或创建财务信息
        userFinanceService.update(userFinance);
        return ResponseEntity.ok("Finance updated successfully");
    }

    // 获取用户财务信息
    @GetMapping("/info/{userId}")
    public Result<UserFinance> getUserFinanceInfo(@PathVariable Integer userId) {
        UserFinance userFinance = userFinanceService.findByUserId(userId);
        return Result.success(userFinance);
    }
}
