<template>
    <div class="container">
        <div class="flex gap-4">
            <el-input v-model="input3" style="width: 170px; height: 50px" placeholder="Type ticker or name">
            <template #prefix>
                <el-icon class="el-input__icon"><OfficeBuilding /></el-icon>
            </template>
            </el-input>
            <el-input
            v-model="input4"
            style="width: 680px; height: 50px"
            placeholder="Ask about filings and documents..."
            >
            <template #suffix>
                <el-button :icon="Search" circle @click="router.push({ path: `/docu/${encodeURIComponent(input3)}/${encodeURIComponent(input4)}` })"/>
            </template>
            </el-input>
        </div>

        <!-- <el-avatar class="avator" :src="userInfoStore.info.userPic ? userInfoStore.info.userPic : avatar" /> -->
        <div class="output">
            <div class="question">
                <el-avatar class="avator" :src="userInfoStore.info.userPic ? userInfoStore.info.userPic : avatar" />
                {{ question }}
            </div>
            <div class="answer" v-loading="loading" element-loading-background="transparent">
                <p v-html="answer"></p>
            </div>
        </div>
    </div>
    
</template>

<script setup>
    import { ref, onMounted, watch } from 'vue'
    import { OfficeBuilding, Search } from '@element-plus/icons-vue'
    import { useRoute, useRouter } from 'vue-router';
    import { DocQuest } from '@/api/doc.js';
    import { useUserInfoStore } from '@/stores/userInfo.js'
    import avatar from '@/assets/default.png'
    
    const userInfoStore = useUserInfoStore();

    const route = useRoute();
    const router = useRouter();

    const stock = ref(route.params.stock)
    const question = ref(route.params.question)
    const input3 = ref(route.params.stock);
    const input4 = ref(route.params.question);
    const answer = ref('')
    const loading = ref('true')

    const searchFor = async (input3, input4) => {
        answer.value = ''
        loading.value = true
        
        const data = {
            stockCode: input3,
            question: input4
        };
        console.log("Data:", data);

        try {
            let result = await DocQuest(data);
            // console.log(result)
            const originAnswer = result.data.choices[0]?.message?.content || 'Something wrong'
            const formatAnswer = originAnswer.replace(/\n/g, '<br>')
                                        .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
                                        .replace(/\d\.\s/g, '');
            answer.value = formatAnswer
            loading.value = false
        } catch (error) {
            console.error("Error during AI request:", error);
        }
    }

    onMounted(() => {
        searchFor(input3.value, input4.value);
    });

    watch(() => route.params, (newParams, oldParams) => {
      if (newParams.stock !== oldParams.stock || newParams.question !== oldParams.question) {
        searchFor(input3.value, input4.value);
        stock.value = input3.value
        question.value = input4.value
      }
    });

</script>


<style lang="scss" scoped>
    .container {
        display: flex;
        flex-direction: column;
        gap: 20px;
        padding: 20px;
        justify-content: center;
        align-items: center;
    }

    .flex {
        display: flex;
        gap: 10px;
        justify-content: center;
        width: 100%;
        max-width: 800px;
        padding: 10px 0;
    }


    .output {
        border-radius: 5px;
        max-width: 800px;
        width: 100%;
    }

    .question {
        background-color: white;
        border: 1px solid #ddd;
        border-radius: 5px;
        padding: 20px;
        font-size: 16px;
        position: relative;
    }

    .avator {
        position: absolute;
        left: -55px;
        top: 10px;
    }

    .answer {
        padding: 20px;
        font-size: 16px;
        line-height: 1.5;
    }

</style>