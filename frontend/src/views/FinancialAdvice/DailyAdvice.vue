<script setup>
import { ref } from 'vue';
import { useUserFinanceInfoStore } from '@/stores/userFinanceInfo';
import { useTokenStore } from '@/stores/token';
import instance from '@/utils/request.js';

const userFinanceStore = useUserFinanceInfoStore();
const tokenStore = useTokenStore();
const advice = ref('');
const searchQuery = ref('');

const getDailyAdvice = async () => {
  try {
    const financeInfo = userFinanceStore.info;
    const prompt = `I have set a savings goal: ${financeInfo.investmentGoal}. Please generate daily spending advice based on the following financial information: Balance: ${financeInfo.balance}, Loan Amount: ${financeInfo.loanAmount}, Monthly Salary: ${financeInfo.monthlySalary}, Savings: ${financeInfo.savings}, Risk Tolerance: ${financeInfo.riskTolerance} ,with a corresponding chart`;
    const response = await instance.post('/api/chat/ask', { prompt });
    advice.value = response.data.choices[0]?.message?.content.replace(/\n/g, '<br>');
  } catch (error) {
    console.error('Failed to get daily advice:', error);
  }
};

const askQuestion = async () => {
  if (!searchQuery.value) return;
  try {
    const response = await instance.post('/api/chat/ask', { prompt: searchQuery.value });
    advice.value = response.data.choices[0]?.message?.content.replace(/\n/g, '<br>');
  } catch (error) {
    console.error('Failed to ask question:', error);
  }
};
</script>

<template>
  <div class="advice-container">
    <h2>Daily Spending Advice</h2>

    <!-- Display the table with financial info -->
    <div v-if="advice" class="finance-table">
      <h3>Financial Information Used for Advice</h3>
      <table>
        <thead>
          <tr>
            <th>Category</th>
            <th>Value</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>Goal</td>
            <td>{{ userFinanceStore.info.investmentGoal }}</td>
          </tr>
          <tr>
            <td>Balance</td>
            <td>{{ userFinanceStore.info.balance }}</td>
          </tr>
          <tr>
            <td>Loan Amount</td>
            <td>{{ userFinanceStore.info.loanAmount }}</td>
          </tr>
          <tr>
            <td>Monthly Salary</td>
            <td>{{ userFinanceStore.info.monthlySalary }}</td>
          </tr>
          <tr>
            <td>Savings</td>
            <td>{{ userFinanceStore.info.savings }}</td>
          </tr>
          <tr>
            <td>Risk Tolerance</td>
            <td>{{ userFinanceStore.info.riskTolerance }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Display the AI-generated advice -->
    <div v-html="advice" class="advice-content"></div>

    <!-- Button to get daily advice, centered -->
    <div class="button-container">
      <el-button type="primary" @click="getDailyAdvice">Get Daily Advice</el-button>
    </div>

    <!-- Search input and button to ask a custom question -->
    <div class="search-container">
      <el-input v-model="searchQuery" placeholder="Enter your question..."></el-input>
      <el-button type="primary" @click="askQuestion">Ask Question</el-button>
    </div>
  </div>
</template>

<style scoped>
.advice-container {
  max-width: 700px;
  margin: 0 auto;
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h2, h3 {
  text-align: center;
  color: #333;
  font-size: 24px;
  margin-bottom: 20px;
}

.finance-table {
  margin-bottom: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

table, th, td {
  border: 1px solid #ddd;
  text-align: left;
}

th, td {
  padding: 10px;
}

.advice-content {
  background-color: #fff;
  border: 1px solid #e0e0e0;
  padding: 15px;
  border-radius: 8px;
  margin-bottom: 20px;
  min-height: 100px;
  font-size: 16px;
  line-height: 1.5;
}

/* Center the button container */
.button-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.search-container {
  display: flex;
  gap: 10px;
  justify-content: center;
  align-items: center;
}

.el-input {
  flex: 1;
  max-width: 400px;
}

.el-button {
  background-color: #409eff;
  color: white;
  border: none;
}

.el-button:hover {
  background-color: #66b1ff;
}

</style>
