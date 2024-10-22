<script setup>
import { Plus, Upload } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { useUserInfoStore } from '@/stores/userInfo.js'
const userInfoStore = useUserInfoStore()

import avatar from '@/assets/default.png'
const uploadRef = ref()

// User avatar URL
const imgUrl = ref(userInfoStore.info.userPic)

// Read token information
import { useTokenStore } from '@/stores/token.js'
const tokenStore = useTokenStore()

// Callback for successful image upload
const uploadSuccess = (result) => {
    // Display uploaded image
    imgUrl.value = result.data
}

import { userAvatarUpdateService } from '@/api/user.js'
import { ElMessage } from 'element-plus'
const updateAvatar = async () => {
    let result = await userAvatarUpdateService(imgUrl.value)
    ElMessage.success(result.message ? result.message : 'Update successful')
    // Update data in Pinia store
    userInfoStore.info.userPic = imgUrl.value
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
                <el-upload 
                    ref="uploadRef"
                    class="avatar-uploader" 
                    :show-file-list="false"
                    :auto-upload="true"
                    action="/api/upload"
                    name="file"
                    :headers="{'Authorization': tokenStore.token}"
                    :on-success="uploadSuccess"
                    >
                    <img v-if="imgUrl" :src="imgUrl" class="avatar" />
                    <img v-else src="@/assets/avatar.jpg" width="278" />
                </el-upload>
                <br />
                <el-button type="primary" :icon="Plus" size="large" @click="uploadRef.$el.querySelector('input').click()">
                    Select Image
                </el-button>
                <el-button type="success" :icon="Upload" size="large" @click="updateAvatar">
                    Upload Avatar
                </el-button>
            </el-col>
        </el-row>
    </el-card>
</template>

<style lang="scss" scoped>
.avatar-uploader {
    :deep() {
        .avatar {
            width: 278px;
            height: 278px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 278px;
            height: 278px;
            text-align: center;
        }
    }
}
</style>
