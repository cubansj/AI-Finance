<script setup>
import {
    Management,
    Promotion,
    UserFilled,
    User,
    Crop,
    EditPen,
    SwitchButton,
    CaretBottom
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'

// Import API functions
import { userInfoGetService } from '@/api/user.js'
// Import Pinia store
import { useUserInfoStore } from '@/stores/userInfo.js'
const userInfoStore = useUserInfoStore();
import { ref } from 'vue'

// Get user information
const getUserInf = async () => {
    let result = await userInfoGetService();
    // Store in Pinia
    userInfoStore.info = result.data;
}
getUserInf()

import { ElMessage, ElMessageBox } from 'element-plus'
import { useTokenStore } from '@/stores/token.js'
const tokenStore = useTokenStore();
import { useRouter } from 'vue-router';
const router = useRouter();
const handleCommand = (command) => {
    if (command === 'logout') {
        // Logout
        ElMessageBox.confirm(
            'Sure to logout?',
            'Warning',
            {
                confirmButtonText: 'Confirm',
                cancelButtonText: 'Cancel',
                type: 'warning',
            }
        )
            .then(async () => {
                // Clear token and user info in Pinia
                userInfoStore.info = {}
                tokenStore.token = ''
                // Redirect to login page
                router.push('/login')
            })
            .catch(() => {
                // User canceled
                ElMessage({
                    type: 'info',
                    message: 'Logout Cancelled',
                })
            })
    } else {
        // Route to the specified user page
        router.push('/user/' + command)
    }
}
</script>

<template>
    <el-container class="layout-container">
        <!-- Left-side menu -->
        <el-aside width="320px">
            <div class="el-aside__logo"></div>
            <el-menu active-text-color="#ffd04b" background-color="#232323" text-color="#fff" router>
                <el-menu-item index="/state">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>Financial State Management</span>
                </el-menu-item>

                <el-menu-item index="/advice">
                    <el-icon>
                        <Promotion />
                    </el-icon>
                    <span>Financial Advice</span>
                </el-menu-item>

                <el-sub-menu>
                    <template #title>
                        <el-icon>
                            <UserFilled />
                        </el-icon>
                        <span>User Info</span>
                    </template>
                    <el-menu-item index="/user/info">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>Basic Info</span>
                    </el-menu-item>
                    <el-menu-item index="/user/avatar">
                        <el-icon>
                            <Crop />
                        </el-icon>
                        <span>Change Avatar</span>
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>
        <!-- Right-side main area -->
        <el-container>
            <!-- Header area -->
            <el-header>
                <div>User: <strong>{{ userInfoStore.info.nickname ? userInfoStore.info.nickname : userInfoStore.info.username }}</strong></div>
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :src="userInfoStore.info.userPic ? userInfoStore.info.userPic : avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="info" :icon="User">Basic Info</el-dropdown-item>
                            <el-dropdown-item command="avatar" :icon="Crop">Change Avatar</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton">Logout</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <!-- Main content area -->
            <el-main>
                <router-view></router-view>
            </el-main>
            <!-- Footer area -->
            <el-footer>AI Finance©2024 Created by 5620-THU_Group14</el-footer>
        </el-container>
    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    height: 100vh;
    display: flex;

    .el-aside {
        background-color: #4A90E2; 
        padding: 20px; 

        &__logo {
            height: 150px;
            background: url('@/assets/indexlogo.jpg') no-repeat center / contain;
            margin-bottom: 30px; 
        }

        .el-menu {
            border-right: none;
            border-radius: 8px;
            background-color: #2E3A59; 
            padding: 15px; 

            .el-menu-item {
                margin-bottom: 15px; 
                border-radius: 6px;
                transition: background-color 0.3s;

                &:hover {
                    background-color: #3C4A70;
                }
            }

            .el-sub-menu {
                margin-top: 20px;
            }
        }
    }

    .el-header {
        background-color: #F5F7FA;
        padding: 20px 40px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        border-bottom: 1px solid #E0E0E0; 

        .el-dropdown__box {
            display: flex;
            align-items: center;
            cursor: pointer;

            .el-icon {
                color: #666;
                margin-left: 10px;
            }

            &:hover .el-icon {
                color: #4A90E2;
            }
        }
    }

    .el-main {
        padding: 30px;
        background-color: #EAEFF2; 
        flex: 1; 
    }

    .el-footer {
        background-color: #F5F5F5;
        padding: 10px 0;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
        border-top: 1px solid #E0E0E0; 
    }
}
</style>
