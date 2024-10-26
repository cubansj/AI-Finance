package com.aifinance.service.impl;

import com.aifinance.mapper.UserFinanceMapper;
import com.aifinance.pojo.UserFinance;
import com.aifinance.service.UserFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFinanceServiceImpl implements UserFinanceService {

    @Autowired
    private UserFinanceMapper userFinanceMapper;

    @Override
    public UserFinance findByUserId(Integer userId) {
        return userFinanceMapper.findByUserId(userId);
    }

    @Override
    public void update(UserFinance userFinance) {
        UserFinance userFinanceTemp = userFinanceMapper.findByUserId(Math.toIntExact(userFinance.getUserId()));
        if (userFinanceTemp == null){
            userFinanceMapper.insert(userFinance);
        }else{
            userFinanceMapper.update(userFinance);
        }
    }

    @Override
    public void create(UserFinance userFinance) {
        if(userFinance.getHasLoan() == 0){
            userFinance.setLoanAmount(null);
        }
        userFinanceMapper.insert(userFinance);
    }
}
