<template>
    <el-container class="advice-container">
      <el-header>
        <h2>Financial Advice</h2>
      </el-header>
      <el-main>
        <div class="button-group">
          <el-button 
            type="primary" 
            size="large" 
            @click="navigateTo('/advice/daily')"
            class="left-button">
            Daily Advice
          </el-button>
          <div class="button-space"></div> 
          <el-button 
            type="success" 
            size="large" 
            @click="navigateTo('/advice/investment')"
            class="right-button">
            Investment Advice
          </el-button>
        </div>
      </el-main>
    </el-container>
  </template>
  
  <script setup>
  import { useRouter } from 'vue-router'
  
  const router = useRouter()
  
  // 定义跳转函数
  const navigateTo = (path) => {
    router.push(path)
  }
  </script>
  
  <style lang="scss" scoped>
  .advice-container {
    height: 100vh;
    background-color: #e9eef3;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  
    .el-header {
      margin-bottom: 30px;
      text-align: center;
  
      h2 {
        font-size: 32px;
        font-weight: bold;
        color: #333;
      }
    }
  
    .button-group {
      display: flex;
      align-items: center;
  
      .left-button {
        border-top-right-radius: 0;
        border-bottom-right-radius: 0;
      }
  
      .right-button {
        border-top-left-radius: 0;
        border-bottom-left-radius: 0;
      }
  
      .button-space {
        width: 30px; /* 设置两个按钮之间的距离 */
      }
  
      .el-button {
        width: 200px;
        height: 60px;
        font-size: 18px;
      }
    }
  }
  </style>
  