import request from '@/utils/request.js';

// 更新用户财务信息的 API 调用，发送 JSON 数据
export const userFinanceInfoUpdateService = (financeInfo) => {
    return request.post('/finance/update', financeInfo, {
        headers: {
            'Content-Type': 'application/json'
        }
    });
};

// 获取用户财务信息的 API 调用
export const userFinanceInfoGetService = () => {
    return request.get('/finance/info');
};
