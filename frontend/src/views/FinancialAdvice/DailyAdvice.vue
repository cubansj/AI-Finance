<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

// 返回函数
const goBack = () => {
  router.push('/advice') 
}
</script>

<template>
  <div class="daily-advice-container">
    <div>Daily Advice</div>
    <el-button 
      type="primary" 
      @click="goBack" 
      class="back-button">
      Back
    </el-button>
  </div>
</template>

<style lang="scss" scoped>
.daily-advice-container {
  position: relative;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
}

.back-button {
  position: absolute;
  bottom: 20px;
  right: 20px;
}
</style>
