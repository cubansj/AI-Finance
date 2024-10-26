import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserAvatarVue from '@/views/user/UserAvatar.vue'
import UserResetPasswordVue from '@/views/user/UserResetPassword.vue'
import SurveyVue from '@/views/user/Survey.vue'

import DailyAdvice from '@/views/FinancialAdvice/DailyAdvice.vue'
import InvestmentAdvice from '@/views/FinancialAdvice/InvestmentAdvice.vue'
import Advice from '@/views/FinancialAdvice/Advice.vue'
import InvestmentRecommendation from '@/views/FinancialAdvice/InvestmentRecommendation.vue'
import InvestmentPlan from '@/views/FinancialAdvice/InvestmentPlan.vue'

import MarketList from '../views/DocQuest/MarketList.vue'
import Doc from '../views/DocQuest/Doc.vue'

import StateManagement from '@/views/FinancialState/StateManagement.vue'
import StateManagementVue from '@/views/FinancialState/StateManagement.vue'

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
            { path: '/state', component: StateManagement },
            { path: '/FinancialState', component: StateManagementVue },
            { path: '/user/info', component: UserInfoVue },
            { path: '/user/avatar', component: UserAvatarVue },
            { path: '/user/password', component: UserResetPasswordVue },
            { path: '/survey', component: SurveyVue },
            { path: '/docu', component: MarketList },
            { path: '/docu/:stock/:question', component: Doc },
            { path: '/recommendation/:recommendation', component: InvestmentRecommendation },
            { path: '/investmentplan/:asset/:riskLevel', component: InvestmentPlan }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

export default router
