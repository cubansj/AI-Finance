// Import axios  npm install axios
import axios from 'axios';
import { ElMessage } from 'element-plus';
// Define a variable to record the common prefix, baseURL
const baseURL = '/api';
const instance = axios.create({ baseURL });
import { useTokenStore } from '@/stores/token.js';

// Add request interceptor
instance.interceptors.request.use(
    (config) => {
        // Actions before sending request
        let tokenStore = useTokenStore();
        // Attach token if it exists
        if (tokenStore.token) {
            config.headers.Authorization = tokenStore.token;
        }
        return config;
    },
    (err) => {
        // Handle request error
        return Promise.reject(err);
    }
);

import router from '@/router';
// Add response interceptor
instance.interceptors.response.use(
    (result) => {
        // Check the status code
        if (result.data.code == 0) {
            return result.data;
        } else if (result.status == 200) {
            return result
        }
        ElMessage.error(result.data.msg ? result.data.msg : 'Service exception');
        return Promise.reject(result.data);
    },
    (err) => {
        // If response status is 401, prompt the user and redirect to the login page
        if (err.response.status === 401) {
            ElMessage.error('Login first!');
            router.push('/login');
        } else {
            ElMessage.error('Service exception');
        }
        return Promise.reject(err); // Transform to a failed asynchronous state
    }
);

export default instance;
