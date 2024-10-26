<script setup>
import { ref } from 'vue'
// const userInfo = ref({
//     id: 0,
//     username: 'zhangsan',
//     nickname: 'zs',
//     email: 'zs@163.com',
// })
import { useUserInfoStore } from '@/stores/userInfo.js'
const userInfoStore = useUserInfoStore()
const userInfo = ref({ ...userInfoStore.info })

import { userInfoUpdateService } from '@/api/user.js'
import { ElMessage } from 'element-plus'
const updateUserInfo = async () => {
    let result = await userInfoUpdateService(userInfo.value)
    ElMessage.success(result.message ? result.message : 'Update successful')
    // Update data in Pinia store
    userInfoStore.info.nickname = userInfo.value.nickname
    userInfoStore.info.email = userInfo.value.email
}

const rules = {
    nickname: [
        { required: true, message: 'Please enter a nickname', trigger: 'blur' },
        {
            pattern: /^\S{2,10}$/,
            message: 'Nickname must be 2-10 non-whitespace characters',
            trigger: 'blur'
        }
    ],
    email: [
        { required: true, message: 'Please enter an email address', trigger: 'blur' },
        { type: 'email', message: 'Invalid email format', trigger: 'blur' }
    ]
}
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>Basic Information</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="userInfo" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="Username">
                        <el-input v-model="userInfo.username" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="Nickname" prop="nickname">
                        <el-input v-model="userInfo.nickname"></el-input>
                    </el-form-item>
                    <el-form-item label="Email" prop="email">
                        <el-input v-model="userInfo.email"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateUserInfo">Submit</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>
