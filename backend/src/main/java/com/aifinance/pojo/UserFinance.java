package com.aifinance.pojo;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class UserFinance {
    private Integer userId;
    private BigDecimal balance;
    private Integer hasLoan;
    private BigDecimal loanAmount;
    private BigDecimal monthlySalary;
    @Setter
    private BigDecimal savings;  // 确保存在 savings 字段
    private String investmentGoal;
    private String riskTolerance;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    private BigDecimal accountBalance;
    private String investmentPortfolio;

}

