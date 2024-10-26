<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router' // 引入 router

// 引入已存在的 store 和服务
import { useUserFinanceInfoStore } from '@/stores/userFinanceInfo.js'
import { userFinanceInfoUpdateService } from '@/api/userFinance.js'
import { ElMessage } from 'element-plus'
import { userInfoGetService } from '@/api/user.js'

const userFinanceStore = useUserFinanceInfoStore()
const userFinanceInfo = ref({ ...userFinanceStore.info })
const router = useRouter()  // 使用 router 实例

// 跳转到调查问卷页面的函数
const goToSurvey = () => {
    router.push('/survey')  // 跳转到调查问卷页面
}
const getUserInf = async () => {
    let result = await userInfoGetService();
    // Store in Pinia
    userInfoStore.info = result.data;
}
const updateUserFinanceInfo = async () => {
    console.log("----------", userFinanceStore);

    let res = await userInfoGetService();
    console.log("---------------------------------aaaa", res.data);

    const financeInfo = {

        ...userFinanceStore.info.userId = res.data.id
    }
    let result = await userFinanceInfoUpdateService(userFinanceInfo.value)

    ElMessage.success(result.message ? result.message : 'Update successful')
    userFinanceStore.setFinanceInfo(userFinanceInfo.value)
}

const rules = {
    balance: [
        { required: true, message: 'Please enter your balance', trigger: 'blur' }
    ],
    investment: [
        { required: true, message: 'Please enter your investment', trigger: 'blur' }
    ],
    investmentGoal: [
        { required: true, message: 'Please enter your investment goal', trigger: 'blur' }
    ],
    risk: [
        { required: true, message: 'Please select your risk tolerance', trigger: 'blur' }
    ],
    hasLoan: [
        { required: true, message: 'Please specify if you have a loan', trigger: 'change' }
    ],
    loanAmount: [
        { required: true, message: 'Please enter your loan amount', trigger: 'blur' }
    ],
    monthlySalary: [
        { required: true, message: 'Please enter your monthly salary', trigger: 'blur' }
    ],
    savings: [
        { required: true, message: 'Please enter your household savings', trigger: 'blur' }
    ]
}
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>Financial Information</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="userFinanceInfo" :rules="rules" label-width="180px" size="large">

                    <el-form-item label="Balance" prop="balance">
                        <el-input v-model="userFinanceInfo.balance" type="number"></el-input>
                    </el-form-item>
                    <el-form-item label="Investment" prop="investment">
                        <el-input v-model="userFinanceInfo.investment"></el-input>
                    </el-form-item>
                    <el-form-item label="Investment Goal" prop="investmentGoal">
                        <el-input v-model="userFinanceInfo.investmentGoal"></el-input>
                    </el-form-item>
                    <el-form-item label="Risk Tolerance" prop="riskTolerance">
                        <el-select v-model="userFinanceInfo.riskTolerance">
                            <el-option label="Low" value="low"></el-option>
                            <el-option label="Medium" value="medium"></el-option>
                            <el-option label="High" value="high"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="Has Loan" prop="hasLoan">
                        <el-switch v-model="userFinanceInfo.hasLoan" active-value="1" inactive-value="0"></el-switch>
                    </el-form-item>
                    <el-form-item v-if="userFinanceInfo.hasLoan == 1" label="Loan Amount" prop="loanAmount">
                        <el-input v-model="userFinanceInfo.loanAmount" type="number"></el-input>
                    </el-form-item>
                    <el-form-item label="Monthly Salary" prop="monthlySalary">
                        <el-input v-model="userFinanceInfo.monthlySalary" type="number"></el-input>
                    </el-form-item>
                    <el-form-item label="Household Savings" prop="savings">
                        <el-input v-model="userFinanceInfo.savings" type="number"></el-input>
                    </el-form-item>
                    <!-- 新的调查问卷按钮 -->

                    <el-form-item>
                        <el-button type="primary" @click="goToSurvey">Survey</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateUserFinanceInfo">Submit</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>
