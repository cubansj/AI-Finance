<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { sendAIRequest , AIUserProfile , AIRecommendation } from '@/api/investment.js';
import { fetchUserDetailsService } from '@/api/investment.js';

const router = useRouter();

const selectedAssets = ref([]);
const resultsVisible = ref(false);
const riskAdvice = ref('');
const assetAnalysis = ref([]);
const riskLevel = ref('');
const aiContent = ref('');
const aiRecommendation1 = ref('');
const aiRecommendation2 = ref('');

const truncated1 = ref('');
const truncated2 = ref('');

const userInfo = ref(null);
const userFinance = ref(null);
const errorMessage = ref(null);
const showRecommendations = ref(false);


const userProfile  = ref(null);
// 返回到之前的页面
const goBack = () => {
  router.push('/advice');
};

onMounted(async () => {
  try {
    //const username = 'xinwen';
    const response = await fetchUserDetailsService();


    userInfo.value = response.data.userInfo;
    userFinance.value = response.data.userFinance;
    console.log('User Info:', userInfo.value);
    console.log('User Finance:', userFinance.value);
  } catch (error) {
    console.error('Error fetching user details:', error);
    errorMessage.value = 'Failed to fetch user details. Please try again.';
  }

  try {
    console.log('User Finance Test:', userFinance.value);
    // 调用 AIUserProfile，将 userFinance 传递过去
    const response = await AIUserProfile(userFinance.value);  // 传递整个对象
    userProfile.value = response.data.choices[0]?.message?.content || 'No result available';  // 获取 AI 返回的用户画像
  } catch (error) {
    console.error("Error generating user profile:", error);
  }

});


const submitForm = async (e) => {
  e.preventDefault();

  if (!riskLevel.value) {
    alert("Please select a risk tolerance level.");
    return;
  }

  if (selectedAssets.value.length === 0) {
    alert("Please select at least one asset combination.");
    return;
  }

  if (selectedAssets.value.length > 1) {
    alert("Please select only one asset combination.");
    return;
  }

  const data = {
    riskLevel: riskLevel.value,
    investmentType: selectedAssets.value
  };

  console.log("Data:", data);

  let aiResponseContent = "No result available"; // 默认的 AI 返回内容

  try {
    let result = await sendAIRequest(data);
    // 提取 AI 响应中的 content
    aiResponseContent = result.data.choices[0]?.message?.content || 'No result available';
    console.log("Content:", aiResponseContent);
    aiContent.value = aiResponseContent.replace(/\n/g, '<br>');
    console.log("ContentCheck:", aiContent);
  } catch (error) {
    console.error("Error during AI request:", error);
    aiResponseContent = "Error fetching advice. Please try again later."; // 请求失败时使用的默认内容
  }

  // 截取前 10 个单词并在末尾添加 "..."
  const truncatedContent = aiResponseContent.split(' ').slice(0, 10).join(' ') + (aiResponseContent.split(' ').length > 10 ? '...' : '');

  // 使用截取后的内容替换 detail 字段
  const fakeResponse = {
    riskAdvice: `Your selected risk tolerance is: ${riskLevel.value}`,
    assetAnalysis: selectedAssets.value.map(asset => ({
      name: asset,
      detail: truncatedContent // 只显示前 10 个单词，并在后面加上 "..."
    }))
  };

  displayInvestmentAdvice(fakeResponse); // 显示生成的投资建议
  resultsVisible.value = true;  // 确保结果部分可见
};

const confirmUserInfo = async () => {
  let aiRecommendationContent = "No result available"; // 默认的 AI 返回内容

  try {
    let result = await AIRecommendation(userFinance.value);
    aiRecommendationContent = result.data.choices[0]?.message?.content || 'No result available';
    console.log("Content:", aiRecommendationContent);
    const no1Match = aiRecommendationContent.match(/No\.1 recommendation is: (.+?)(?=No\.2 recommendation is:|$)/s);
    const no2Match = aiRecommendationContent.match(/No\.2 recommendation is: (.+)/s);

    if (no1Match && no2Match) {
      const no1Recommendation = no1Match[1].trim();
      const no2Recommendation = no2Match[1].trim();

      console.log("No.1 Recommendation:", no1Recommendation);
      console.log("No.2 Recommendation:", no2Recommendation);

      aiRecommendation1.value = no1Recommendation.replace(/\n/g, '<br>');
      aiRecommendation2.value = no2Recommendation.replace(/\n/g, '<br>');
      //console.log("No.1 Recommendation:", aiRecommendation1);
      //console.log("No.2 Recommendation:", aiRecommendation2);

      truncated1.value = no1Recommendation.split(' ').slice(0, 10).join(' ') + (no1Recommendation.split(' ').length > 10 ? '...' : '');
      truncated2.value = no2Recommendation.split(' ').slice(0, 10).join(' ') + (no2Recommendation.split(' ').length > 10 ? '...' : '');
    } else {
      alert("error!please refresh");
      return;
    }

  } catch (error) {
    console.error("Error during AI request:", error);
    aiRecommendationContent = "Error fetching advice. Please try again later."; // 请求失败时使用的默认内容
  }

  showRecommendations.value = true;
};


// 显示投资建议
const displayInvestmentAdvice = (data) => {
  resultsVisible.value = true;
  riskAdvice.value = data.riskAdvice;
  assetAnalysis.value = data.assetAnalysis;
};

const showRecommendationDetail = (recommendation, content) => {
  router.push({
    path: `/recommendation/${recommendation}`,
    query: { content }
  });
};


</script>

<template>
  <div class="investment-advice-container">
    <header class="header-container">
      <el-button type="primary" @click="goBack" class="back-button">Back</el-button>
      <h1 class="advice-title">Get your investment advice now!</h1>
    </header>

    <div class="user-info">
      <p>{{ userProfile }}</p>
    </div>
    <div v-if="!showRecommendations">
      <el-button type="primary" @click="confirmUserInfo">Get Recommendations</el-button>
    </div>

    <!-- 推荐框，始终保留结构，只在确认后显示内容 -->
    <div class="recommendation-container">

      <div v-if="showRecommendations" class="recommendation-boxes">
        <h2 class="recommendations-title">
          <span>&nbsp;</span>
          <span>&nbsp;</span>
          <span>&nbsp;</span>
          <span>Our Top</span>
          <span>Recommendations</span>
        </h2>
        <div class="recommendation-box hover-effect">
          <img src="@/assets/recommendation1.png" alt="Recommendation 1 Image" class="recommendation-image" />
          <h3>No.1 Recommendation</h3>
          <p>{{truncated1}}</p>
          <el-button type="primary" @click="showRecommendationDetail('recommendation1', aiRecommendation1 )">View Details</el-button>
        </div>
        <div class="recommendation-box hover-effect">
          <img src="@/assets/recommendation2.png" alt="Recommendation 1 Image" class="recommendation-image" />
          <h3>No.2 Recommendation</h3>
          <p>{{truncated2}}</p>
          <el-button type="primary" @click="showRecommendationDetail('recommendation2', aiRecommendation2 )">View Details</el-button>
        </div>
      </div>
    </div>

    <div class="container">
      <h2>Please Enter Your Investment Preferences</h2>
      <form @submit="submitForm">
        <!-- 风险承受能力选择 -->
        <label for="risk-level">Select Risk Tolerance:</label>
        <select id="risk-level" v-model="riskLevel">
          <option value="low">Low Risk</option>
          <option value="medium">Medium Risk</option>
          <option value="high">High Risk</option>
        </select>

        <!-- 资产组合选择 -->
        <label>Select Investment Combinations:</label>
        <label class="label-container">
          Stocks <input type="checkbox" v-model="selectedAssets" value="stocks">
        </label>
        <label class="label-container">
          Bonds <input type="checkbox" v-model="selectedAssets" value="bonds">
        </label>
        <label class="label-container">
          Commodities <input type="checkbox" v-model="selectedAssets" value="commodities">
        </label>

        <button type="submit">Submit</button>
      </form>

      <!-- 显示结果 -->
      <div id="results" v-if="resultsVisible">
        <h2>Your Investment Advice</h2>
        <p>{{ riskAdvice }}</p>
        <ul>
          <li v-for="asset in assetAnalysis" :key="asset.name"
              @click="router.push({ path: `/investmentplan/${asset.name}/${riskLevel}`, query: { aiContent: aiContent } })">
            {{ asset.name }}: {{ asset.detail }}
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped>
.investment-advice-container {
  position: relative;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  background-color: #f5f5f5;
  padding-top: 20px;
  padding-bottom: 20px;
}

.header-container {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding: 10px 20px;
  background-color: #4A90E2;
  color: white;
}
h1.advice-title {
  font-size: 36px;
  text-align: center;
}

.header-container h1 {
  margin: 0;
}

.back-button {
  position: absolute;
  left: 20px;
  top: 50%;
  transform: translateY(-50%);
  padding: 5px 10px;
  font-size: 14px;
  width: auto;
}

.container {
  margin: 20px auto;
  max-width: 1000px;
  background-color: white;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h2 {
  color: #333;
}

label {
  display: block;
  margin: 15px 0 5px;
}

select, input[type="checkbox"], button {
  margin-top: 5px;
  display: block;
  width: 100%;
  padding: 10px;
  font-size: 16px;
}

button {
  background-color: #4A90E2;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #4A90E2;
}

#results {
  margin-top: 20px;
  padding: 10px;
  background-color: #e7f3fe;
  border-left: 6px solid #4A90E2;
}


.user-info {
  margin: 20px auto;
  text-align: center;
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  max-width: 800px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}


.recommendations-title span {
  display: block;
  white-space: nowrap;
}

.recommendation-container {
  margin: 20px auto;
  text-align: center;
  max-width: 800px;
}

.recommendation-boxes {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  gap: 20px;
}

.recommendation-box {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 15px;
  width: 45%;
  text-align: center;
}

@keyframes growShrink {
  from {
    transform: scale(1);
  }
  to {
    transform: scale(1.1);
  }
}

h2.recommendations-title {
  font-size: 24px;
  text-align: center;
  font-family: 'Roboto', sans-serif;
  animation: growShrink 1.5s ease-in-out infinite alternate;
}


.recommendation-box {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 15px;
  width: 45%;
  text-align: center;
  position: relative; /* 可以用于稍后调整图片位置 */
}

.recommendation-image {
  max-width: 80%;
  height: auto;
  margin-bottom: 5px;
}

.hover-effect:hover {
  transform: scale(1.05);
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
}

</style>