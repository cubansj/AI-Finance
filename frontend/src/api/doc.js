import request from '@/utils/request.js'

export const DocQuest = (data) => {
    const prompt = data.question 
                                    // + "Please refer to the following paragraph for the format" +
                                    // `In the 2023 10-K, Apple\'s product revenue distribution is as follows (in millions) \n
                                    // iPhone: $200,583 \n
                                    // Mac: $29,357 \n
                                    // iPad: $28,300 \n
                                    // Wearables, Home and Accessories: $39,845 \n
                                    // Total product revenue for 2023 was $298,085 million. \n
                                    // Sources: \n
                                    // AAPL 10-K 20230930 \n
                                    // AAPL`;

    const payload = {
        prompt: prompt
    };
    return request.post('/api/chat/ask', payload)
        .then(response => {
            // console.log("Response:", response);
            return response;
        })
        .catch(error => {
            console.error("Request failed:", error);
        });

};