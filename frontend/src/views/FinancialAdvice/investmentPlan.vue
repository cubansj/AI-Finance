git stash pop<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();

const riskLevel = ref(route.params.riskLevel || 'Default Risk');
const investmentType = ref(route.params.asset || 'Default Investment');
const aiContent = ref(route.query.aiContent || 'No AI response available');
const description = ref('');

// 返回到之前的页面
const goBack = () => {
  router.push('/advice/investment');
};

// 页面挂载时获取路由参数
onMounted(() => {
  description.value = ` We sincerely advise you: <br> `;

  // 将 AI 返回的结果直接加到 description 中
  if (aiContent.value) {
    description.value += ` ${aiContent.value}`;  // 直接追加 AI 返回的结果
  }
});
</script>

<template>
  <div class="recommendation-detail-container">
    <header class="header-container">
      <el-button type="primary" @click="goBack" class="back-button">Back</el-button>
      <h1 class="recommendation-title">{{ investmentType }} - {{ riskLevel }}</h1>
    </header>

    <div class="recommendation-content">
      <h2>Investment Details</h2>
      <p v-html="description"></p>
    </div>
  </div>
</template>

<style scoped>
.recommendation-detail-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  padding: 40px;
  min-height: 100vh;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
}

.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  margin-bottom: 30px;
  border-bottom: 2px solid #4A90E2;
  padding-bottom: 10px;
}

.recommendation-title {
  font-size: 32px;
  font-weight: bold;
  color: #4A90E2;
  margin: 0;
  animation: fadeIn 1.5s ease-in-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.back-button {
  font-size: 16px;
  padding: 10px 20px;
  background-color: #4A90E2;
  color: white;
  border-radius: 8px;
}

.recommendation-content {
  max-width: 800px;
  padding: 30px;
  background-color: #ffffff;
  border-radius: 15px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  text-align: left;
  line-height: 1.8;
  transition: all 0.3s ease-in-out;
}

.recommendation-content:hover {
  transform: scale(1.02);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
}

p {
  font-size: 18px;
  color: #333;
}


</style>