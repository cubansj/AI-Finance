import request from "@/utils/request";

export const sendAIRequest = (data) => {
    const prompt = `Generate an investment plan for a ${data.riskLevel} risk tolerance investor, focusing on the following assets: ${data.investmentType.join(', ')}. 
    The response should follow this structure:
    1. Investment Objective: A brief description of the main objective of the plan.
    2. Asset Allocation: A detailed breakdown of how assets should be allocated (e.g., percentage of stocks, bonds, etc.).
    3. Risk Management: Strategies to manage risks associated with the chosen assets.
    4. Expected Returns: A summary of the expected returns for the investor based on the asset allocation.
    Answer should Start with 'We sincerely advise you to choose()... () should be the product name'`;
    const payload = {
        prompt: prompt
    };
    console.log("payload", payload);
    return request.post('/api/chat/ask', payload)
        .then(response => {
            console.log("Response:", response);
            return response;
        })
        .catch(error => {
            console.error("Request failed:", error);
        });

};

export const AIUserProfile = (userFinance) => {
    const userFinanceString = JSON.stringify(userFinance); // 将对象转为字符串
    const prompt = `Generate a user profile based on the following financial data: ${userFinanceString}
                            .Start with 'You'. Fewer than 100 words. Do not show the user's specific private data in the response`;
    const payload = {
        prompt: prompt
    };
    console.log("payload", payload);
    return request.post('/api/chat/ask', payload)
        .then(response => {
            console.log("Response:", response);
            return response;
        })
        .catch(error => {
            console.error("Request failed:", error);
        });
};

export const AIRecommendation = (userdata) => {
    const userFinanceString = JSON.stringify(userdata.userFinance); // 将对象转为字符串
    const prompt = `Generate two top recommendations of investment about Stocks,Bonds, Commodities or other projects based on the following financial data: ${userFinanceString}
                            and the description about the user: ${userdata.userprofile}. Each recommendation should be around 500 words.
                            No.1 Start with 'No.1 recommendation is: ',
                            No.2 Start with 'No.2 recommendation is: '.`;
    const payload = {
        prompt: prompt
    };
    console.log("payload", payload);
    return request.post('/api/chat/ask', payload)
        .then(response => {
            console.log("Response:", response);
            return response;
        })
        .catch(error => {
            console.error("Request failed:", error);
        });
};

export const fetchUserDetailsService = () => {
    return request.get('/user/details');
};