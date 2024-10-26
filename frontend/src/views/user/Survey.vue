<template>
  <el-card class="survey-card">
    <h3>Risk Assessment Survey</h3>
    <el-form :model="surveyAnswers" :rules="rules" label-position="top" label-width="300px" ref="formRef">

       <!-- Question 1 -->
       <el-form-item v-if="currentQuestion === 1" prop="q1" required
        label="1. Based on your current situation, which option best describes your household's income expectation over the next 5 years?">
        <el-radio-group v-model="surveyAnswers.q1">
          <el-radio label="1">Decrease</el-radio>
          <el-radio label="2">Uncertain</el-radio>
          <el-radio label="3">Remain stable</el-radio>
          <el-radio label="4">Slow growth</el-radio>
          <el-radio label="5">Rapid growth</el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- Question 2 -->
      <el-form-item v-if="currentQuestion === 2" prop="q2" required
        label="2. What percentage of your household's net assets can be used for financial investments (excluding savings deposits)?">
        <el-radio-group v-model="surveyAnswers.q2">
          <el-radio label="1">Less than 10%</el-radio>
          <el-radio label="2">10% to 25%</el-radio>
          <el-radio label="3">25% to 50%</el-radio>
          <el-radio label="4">More than 50%</el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- Question 3 -->
      <el-form-item v-if="currentQuestion === 3" prop="q3" required
        label="3. Do you have significant unpaid debt? If so, what is its nature?">
        <el-radio-group v-model="surveyAnswers.q3">
          <el-radio label="1">Yes, loan from relatives/friends</el-radio>
          <el-radio label="2">Yes, credit card or consumer loans</el-radio>
          <el-radio label="3">Yes, mortgage or long-term debt</el-radio>
          <el-radio label="4">No</el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- Question 4 -->
      <el-form-item v-if="currentQuestion === 4" prop="q4" required
        label="4. Which option best describes your investment experience?">
        <el-radio-group v-model="surveyAnswers.q4">
          <el-radio label="1">No experience, except bank savings</el-radio>
          <el-radio label="2">Bought low-risk financial products, bonds, or insurance</el-radio>
          <el-radio label="3">Familiar with funds and trust products</el-radio>
          <el-radio label="4">Experienced in stocks, foreign exchange, futures, or options</el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- Question 5 -->
      <el-form-item v-if="currentQuestion === 5" prop="q5" required
        label="5. How many years have you been investing in stocks, funds, or financial products?">
        <el-radio-group v-model="surveyAnswers.q5">
          <el-radio label="1">No experience</el-radio>
          <el-radio label="2">Less than 2 years</el-radio>
          <el-radio label="3">2 to 5 years</el-radio>
          <el-radio label="4">5 to 8 years</el-radio>
          <el-radio label="5">More than 8 years</el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- Question 6 -->
      <el-form-item v-if="currentQuestion === 6" prop="q6" required
        label="6. Do you accept that banks and managers cannot guarantee the security of your principal, and you may not receive returns or even lose your principal?">
        <el-radio-group v-model="surveyAnswers.q6">
          <el-radio label="1">No</el-radio>
          <el-radio label="2">Yes</el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- Question 7 -->
      <el-form-item v-if="currentQuestion === 7" prop="q7" required
        label="7. If a customer makes 15 transactions in a month, how do you assess the trading frequency?">
        <el-radio-group v-model="surveyAnswers.q7">
          <el-radio label="1">Too high</el-radio>
          <el-radio label="2">High</el-radio>
          <el-radio label="3">Neutral</el-radio>
          <el-radio label="4">Normal</el-radio>
          <el-radio label="5">Low</el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- Question 8 -->
      <el-form-item v-if="currentQuestion === 8" prop="q8" required
        label="8. Which option best describes your investment attitude?">
        <el-radio-group v-model="surveyAnswers.q8">
          <el-radio label="1">Avoid risk, want stable returns</el-radio>
          <el-radio label="2">Protect investment, accept some fluctuation</el-radio>
          <el-radio label="3">Seek growth, willing to take some loss</el-radio>
          <el-radio label="4">Pursue high returns, accept high risk of loss</el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- Question 9 -->
      <el-form-item v-if="currentQuestion === 9" prop="q9" required
        label="9. If you had 1 million capital, which investment opportunity would you choose?">
        <el-radio-group v-model="surveyAnswers.q9">
          <el-radio label="1">99% chance to win 1,000, guaranteed principal</el-radio>
          <el-radio label="2">50% chance to win 50,000, possible principal loss</el-radio>
          <el-radio label="3">25% chance to win 500,000, certain risk of loss</el-radio>
          <el-radio label="4">10% chance to win 1 million, high chance of loss</el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- Question 10 -->
      <el-form-item v-if="currentQuestion === 10" prop="q10" required
        label="10. What is your investment goal?">
        <el-radio-group v-model="surveyAnswers.q10">
          <el-radio label="1">Preserve assets</el-radio>
          <el-radio label="2">Steady asset growth</el-radio>
          <el-radio label="3">Rapid asset growth</el-radio>
        </el-radio-group>
      </el-form-item>

      <div class="button-container">
        <el-button type="primary" v-if="currentQuestion < 10" @click="nextQuestion">Next</el-button>
        <el-button type="primary" v-if="currentQuestion === 10" @click="submitSurvey">Submit Survey</el-button>
      </div>

    </el-form>
  </el-card>
</template>

<script setup>
import { ref } from 'vue';
import { updateScore } from '@/api/user.js'
import { useUserInfoStore } from '@/stores/userInfo.js'
import { ElMessage } from 'element-plus'

const userInfoStore = useUserInfoStore();

const surveyAnswers = ref({
  q1: '', q2: '', q3: '', q4: '', q5: '', q6: '', q7: '', q8: '', q9: '', q10: ''
});

// Define current question
const currentQuestion = ref(1);

// Define validation rules
const rules = ref({
  q1: [{ required: true, message: 'Please select an option', trigger: 'blur' }],
  q2: [{ required: true, message: 'Please select an option', trigger: 'blur' }],
  q3: [{ required: true, message: 'Please select an option', trigger: 'blur' }],
  q4: [{ required: true, message: 'Please select an option', trigger: 'blur' }],
  q5: [{ required: true, message: 'Please select an option', trigger: 'blur' }],
  q6: [{ required: true, message: 'Please select an option', trigger: 'blur' }],
  q7: [{ required: true, message: 'Please select an option', trigger: 'blur' }],
  q8: [{ required: true, message: 'Please select an option', trigger: 'blur' }],
  q9: [{ required: true, message: 'Please select an option', trigger: 'blur' }],
  q10: [{ required: true, message: 'Please select an option', trigger: 'blur' }]
});

const formRef = ref();

const nextQuestion = () => {
  formRef.value.validateField(`q${currentQuestion.value}`, (valid) => {
    if (valid) {
      currentQuestion.value++;  // Move to the next question
    } else {
      console.error('Form validation failed');
    }
  });
};

const submitSurvey = async () => {
  await formRef.value.validate(async (valid) => {
    if (valid) {
      const total = Object.values(surveyAnswers.value).reduce((sum, value) => sum + Number(value), 0);
      console.log(total, 'Survey data:', surveyAnswers.value);
      try {
        const response = await updateScore({
          userId: userInfoStore.info.id,
          score: total
        });
        console.log("Survey submitted successfully", response);
        ElMessage.success(response.message ? response.message : 'Survey submitted successfully');
      } catch (error) {
        console.error("Error submitting survey:", error);
      }
    } else {
      console.error('Form validation failed');
    }
  });
};
</script>

<style scoped>
.survey-card {
  max-width: 800px;
  margin: auto;
  display: flex;
  flex-direction: column;
}

.survey-form {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  flex-grow: 1;
}

.el-form-item {
  margin-bottom: 20px;
}

h3 {
  text-align: center;
  margin-bottom: 30px;
}

.button-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.el-button {
  margin: 0 10px;
}
</style>