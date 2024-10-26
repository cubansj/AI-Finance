package com.aifinance.pojo;
import lombok.Data;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserFinance {
    private Long id;
    private Long userId;
    private BigDecimal balance;
    private Integer hasLoan;
    private BigDecimal loanAmount;
    private BigDecimal monthlySalary;

    @Setter
    private BigDecimal savings;
    private String investmentGoal;
    private String riskTolerance;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private BigDecimal accountBalance;
    private String investmentPortfolio;

    // Getters and Setters




}