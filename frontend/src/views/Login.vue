<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)

//Define data model
const registerData = ref({
    username:'',
    password:'',
    rePassword:''
})

//Define password validation function
const checkRePassword = (rule, value, callback) => {
    if(value==''){
        callback(new Error('Passwords cannot be empty'))
    }else if(value !== registerData.value.password){
        callback(new Error('Passwords do not match'))
    }else{
        callback()
    }
}

//Define form validation rules
const rules = {
    username: [
        {required:true, message:'Username is required', trigger:'blur'},    
        {min:5, max:16, message:'Username must be between 5 and 16 characters', trigger:'blur'}
    ],
    password: [
        {required:true, message:'Password is required', trigger:'blur'},
        {min:5, max:16, message:'Password must be between 5 and 16 characters', trigger:'blur'}
    ],
    rePassword: [
        {validator: checkRePassword, trigger:'blur'}
    ]
}

//Call register function
import { userRegisterService, userLoginService } from '@/api/user.js'
import { ElMessage } from 'element-plus';
const register = async() => {
    let result = await userRegisterService(registerData.value); 
    // if(result.code === 0){
    //     alert(result.msg?result.msg:'Register success');
    // }else{
    //     alert('Register failed');
    // }
    // alert(result.msg?result.msg:'Register success');
    ElMessage.success('Register success');
}

import { useRouter } from 'vue-router'
import {useTokenStore} from '@/stores/token.js'
const router = useRouter();
const tokenStore = useTokenStore();
const login = async() => {
    let result = await userLoginService(registerData.value); 
    // if(result.code === 0){
    //     alert(result.msg?result.msg:'Login success');
    // }else{
    //     alert('Login failed');
    // }
    // alert(result.msg?result.msg:'Login success');
    ElMessage.success('Login success');
    //把得到的token存入tokenStore
    console.log('tokenStore in login.vue----');
    tokenStore.setToken(result.data);
    //Use router to jump to the home page
    router.push('/');
}

const clearRegisterData = () => {           
    registerData.value = {
        username:'',
        password:'',
        rePassword:''
    }
}
</script>

<template>
    <el-row class="login-page">
        <el-col :span="12" class="bg"></el-col>
        <el-col :span="6" :offset="3" class="form">
            <!-- 注册表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules">
                <el-form-item>
                    <h1>Register</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="Please enter username" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input :prefix-icon="Lock" type="password" placeholder="Please enter password" v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword">
                    <el-input :prefix-icon="Lock" type="password" placeholder="Please re-enter password" v-model="registerData.rePassword"></el-input>
                </el-form-item>
                <!-- 注册按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="register">
                        Register
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = false;clearRegisterData()">
                        ← Back
                    </el-link>
                </el-form-item>
            </el-form>
            <!-- 登录表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-else :model="registerData" :rules="rules">
                <el-form-item>
                    <h1>Login</h1>
                </el-form-item>
                <el-form-item prop="usrname">
                    <el-input :prefix-icon="User" placeholder="Please enter username" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="Please enter password" v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox>Remember me</el-checkbox>
                    </div>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="login">Login</el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = true;clearRegisterData()">
                        Register →
                    </el-link>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<style lang="scss" scoped>

.login-page {
    height: 100vh;
    background-color: #fff;

    .bg {
        background: 
            url('@/assets/loginbg.jpg') no-repeat center / cover;
        border-radius: 0 20px 20px 0;
    }

    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
    }
}
</style>