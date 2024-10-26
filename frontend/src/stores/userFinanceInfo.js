import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserFinanceInfoStore = defineStore('userFinanceInfo', {
    state: () => ({
        info: {
            id: null,  
            userId:null, // 用户 ID
            accountBalance: 0,  // 用户的账户余额
            balance: 0,
            hasLoan: 0,
            loanAmount: 0,
            monthlySalary: 0,
            savings: 0,  // 用户的储蓄
            investmentGoal: 0,
            riskTolerance: 'low',  // 用户的风险承受能力
            investmentPortfolio: ''  // 用户的投资组合
        }
    }),
    actions: {
        // 更新金融信息
        setFinanceInfo(newFinanceInfo) {
            this.info = { ...newFinanceInfo }
        },

        // 更新单个属性
        updateAccountBalance(balance) {
            this.info.accountBalance = balance;
        },

        updateInvestmentPortfolio(portfolio) {
            this.info.investmentPortfolio = portfolio;
        },

        updateRiskTolerance(tolerance) {
            this.info.riskTolerance = tolerance;
        }
    },
    getters: {
        // 获取格式化的账户余额
        formattedBalance: (state) => {
            return `$${state.info.accountBalance.toFixed(2)}`; // 保留两位小数的金额显示
        },

        // 判断用户是否为高风险承受者
        isHighRiskTolerance: (state) => {
            return state.info.riskTolerance === 'high';
        }
    }
})
