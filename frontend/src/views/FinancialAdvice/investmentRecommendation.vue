<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();

const recommendation = ref(route.params.recommendation || 'Investment Product');
const recommendationContent = route.query.content || 'No content available';

// 返回到之前的页面
const goBack = () => {
  router.push('/advice/investment');
};

onMounted(() => {
  if (recommendation.value === 'recommendation1') {
    recommendation.value = 'No.1 Recommendation';
  } else if (recommendation.value === 'recommendation2') {
    recommendation.value = 'No.2 Recommendation';
  } else {
    recommendation.value = 'Investment Product';
  }
});

</script>

<template>
  <div class="recommendation-detail-container">
    <!-- 返回按钮 -->
    <header class="header-container">
      <el-button type="primary" @click="goBack" class="back-button">Back</el-button>
      <h1>{{ recommendation }}</h1>
    </header>

    <!-- 产品详情 -->
    <div class="recommendation-details">
      <p v-html="recommendationContent"></p>
    </div>
  </div>
</template>

<style scoped>
.recommendation-detail-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  padding: 20px;
}

.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  margin-bottom: 20px;
}

h1 {
  font-size: 24px;
  margin: 0;
}

.back-button {
  font-size: 14px;
  padding: 5px 10px;
}

.recommendation-detail-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  padding: 40px;
  min-height: 100vh;
}

.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  margin-bottom: 30px;
  border-bottom: 2px solid #4A90E2; /* 底部边框 */
  padding-bottom: 10px;
}

h1 {
  font-size: 32px; /* 更大的字体 */
  font-weight: bold;
  color: #4A90E2;
  margin: 0;
  animation: fadeIn 1.5s ease-in-out; /* 字体淡入效果 */
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

.recommendation-details {
  max-width: 800px;
  padding: 30px;
  background-color: #ffffff;
  border-radius: 15px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  text-align: left;
  line-height: 1.8;
  transition: all 0.3s ease-in-out;
}

.recommendation-details:hover {
  transform: scale(1.02); /* 鼠标悬停时放大效果 */
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
}

p {
  font-size: 18px;
  color: #333;
}
</style>