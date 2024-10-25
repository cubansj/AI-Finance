<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserInfoStore } from '@/stores/userInfo.js'
import { useTokenStore } from '@/stores/token.js'
import { userAvatarUpdateService } from '@/api/user.js'

// 引用 Pinia Store
const userInfoStore = useUserInfoStore()
const tokenStore = useTokenStore()

const imgUrl = ref(userInfoStore.info.userPic || '') // 存储头像 URL
const fileInputRef = ref(null) // 隐藏的文件选择框
let selectedFile = null // 存储用户选择的文件

// 打开文件选择框
const openFileSelector = () => {
  fileInputRef.value.click()
}

// 文件选择后的预览逻辑
const handleFileChange = (event) => {
  const file = event.target.files[0]
  if (file) {
    selectedFile = file // 存储选中的文件
    const reader = new FileReader()
    reader.onload = (e) => {
      imgUrl.value = e.target.result // 设置预览 URL
    }
    reader.readAsDataURL(file) // 将文件读取为 Data URL
  }
}

// 上传文件到 GCS 并更新头像
const uploadAvatar = async () => {
  if (!selectedFile) {
    ElMessage.warning('Please select an image first!')
    return
  }

  const formData = new FormData()
  formData.append('file', selectedFile) // 将文件添加到 FormData

  try {
    // 上传到 GCS 的 API 调用
    const response = await fetch('/api/upload', {
      method: 'POST',
      headers: {
        'Authorization': tokenStore.token, // 添加用户令牌
      },
      body: formData,
    })

    const result = await response.json()
    if (response.ok) {
      const url = result.url // 获取上传后的 GCS URL

      // 调用头像更新服务，将 GCS URL 设置为用户头像
      await userAvatarUpdateService(url)

      ElMessage.success('Avatar uploaded and updated successfully!')
      userInfoStore.info.userPic = url // 更新 Pinia Store 中的头像
      imgUrl.value = url // 更新预览 URL
    } else {
      throw new Error(result.message || 'Failed to upload avatar.')
    }
  } catch (error) {
    console.error('Upload failed:', error)
    ElMessage.error('Upload failed.')
  }
}
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>Change Avatar</span>
      </div>
    </template>

    <el-row>
      <el-col :span="12">
        <!-- 头像预览区域 -->
        <div class="avatar-uploader">
          <img v-if="imgUrl" :src="imgUrl" class="avatar" />
          <img v-else src="@/assets/avatar.jpg" class="avatar" />
        </div>

        <br />

        <!-- 文件选择按钮 -->
        <el-button type="primary" icon="el-icon-plus" size="large" @click="openFileSelector">
          Select Image
        </el-button>

        <!-- 上传按钮 -->
        <el-button type="success" icon="el-icon-upload" size="large" @click="uploadAvatar">
          Upload Avatar
        </el-button>

        <!-- 隐藏的文件选择框 -->
        <input
          type="file"
          ref="fileInputRef"
          accept="image/*"
          style="display: none"
          @change="handleFileChange"
        />
      </el-col>
    </el-row>
  </el-card>
</template>

<style lang="scss" scoped>
.avatar-uploader {
  .avatar {
    width: 278px;
    height: 278px;
    display: block;
    border-radius: 50%;
    object-fit: cover;
    border: 1px dashed var(--el-border-color);
  }
}
</style>
