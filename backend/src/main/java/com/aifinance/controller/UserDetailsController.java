package com.aifinance.controller;

import com.aifinance.pojo.Result;
import com.aifinance.pojo.User;
import com.aifinance.pojo.UserFinance;
import com.aifinance.mapper.UserMapper;
import com.aifinance.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserDetailsController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/details")
    public Result<Map<String, Object>> getUserDetails() {

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userMapper.findByUserName(username);
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 查询用户财务信息
        UserFinance userFinance = userMapper.findFinanceByUserId(user.getId());

        // 将查询结果放入 Map 中组合并返回
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("userInfo", user);          // 用户基本信息
        userDetails.put("userFinance", userFinance); // 用户财务信息

        return Result.success(userDetails);
    }
}