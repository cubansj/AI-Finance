package com.aifinance.mapper;

import com.aifinance.pojo.UserFinance;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserFinanceMapper {

    // 根据用户 ID 查询财务信息
    @Select("SELECT * FROM user_finance WHERE user_id = #{userId}")
    UserFinance findByUserId(Integer userId);

    // 更新财务信息
    @Update("UPDATE user_finance SET balance = #{balance}, has_loan = #{hasLoan},loan_amount = #{loanAmount}, monthly_salary = #{monthlySalary}, savings = #{savings}, investment_goal = #{investmentGoal}, risk_tolerance = #{riskTolerance}, update_time = NOW() WHERE user_id = #{userId}")
    void update(UserFinance userFinance);

    @Insert("INSERT INTO user_finance (user_id, balance,has_loan, loan_amount, monthly_salary, savings, investment_goal, risk_tolerance, create_time) VALUES (#{userId}, #{balance}, #{hasLoan}, #{loanAmount}, #{monthlySalary}, #{savings}, #{investmentGoal}, #{riskTolerance}, NOW())")
    void insert(UserFinance userFinance);
}
