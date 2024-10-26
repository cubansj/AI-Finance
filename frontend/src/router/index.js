import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserAvatarVUe from '@/views/user/UserAvatar.vue'
import UserResetPasswordVue from '@/views/user/UserResetPassword.vue'

import DailyAdvice from '@/views/FinancialAdvice/DailyAdvice.vue'
import InvestmentAdvice from '@/views/FinancialAdvice/InvestmentAdvice.vue'
import Advice from '@/views/FinancialAdvice/Advice.vue'

import StateManagement from '../views/FinancialState/StateManagement.vue'

import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/login', 
        component: LoginVue 
    },
    {
        path: '/', 
        component: LayoutVue,
        redirect: '/advice',
        children: [
            { path: '/advice', component: Advice },
            { path: '/advice/daily', component: DailyAdvice },
            { path: '/advice/investment', component: InvestmentAdvice },
            { path: '/state', component: StateManagement},
            { path: '/user/info', component: UserInfoVue },
            { path: '/user/avatar', component: UserAvatarVUe },
            { path: '/user/password', component: UserResetPasswordVue }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

export default router