<template>
    <el-header>
    <h2>DocuQuest for In-depth Insights</h2>
    </el-header>
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
            <el-button :icon="Search" circle @click="router.push({ path: `/docu/${encodeURIComponent(input3)}/${cleanInput(input4)}` })"/>
        </template>
        </el-input>
    </div>
    <div class="guide">
        <el-text class="mx-1" size="default">Try these question to get started</el-text>
    </div>

    <div class="samplequestion-container">
        <div class="list">
            <el-card 
            class="hover-effect"
            style="width: 400px" 
            :body-style="{ padding: '0px' }" 
            shadow="always" v-for="(item, index) in items" 
            :key="index"
            @click="router.push({ path: `/docu/${encodeURIComponent(item.stockCode)}/${cleanInput(item.question)}` })">
                <div class="card-header">
                    <span class="stock-code">{{ item.stockCode }}</span>
                </div>
                <div class="question">
                    {{ item.question }}
                </div>
            </el-card>
        </div>
    </div>
</template>
  
<script setup>
    import { ref } from 'vue'
    import { OfficeBuilding, Search } from '@element-plus/icons-vue'
    import { useRouter } from 'vue-router';

    const router = useRouter()
    const input3 = ref('')
    const input4 = ref('')
    
    const cleanInput = (str) => {
        return str.replace('.', ' ').trim();
    };
    const items = ref([
    { stockCode: 'AAPL', question: 'What is Apple\'s product revenue distribution in the 2023 10-K?' },
    { stockCode: 'ABNB', question: 'How many hosts and active listings does Airbnb have globally?' },
    { stockCode: 'AMZN', question: 'Tell me more about company\'s revenue growth strategies.' },
    { stockCode: 'COST', question: 'What strategies is Costco using to acquire new members and increase wallet share?' },
    { stockCode: 'DASH', question: 'How does DoorDash differentiate itself in terms of services, pricing, logistics capabolities?' },
    { stockCode: 'DIS', question: 'What is the revenue and profit breakdown between advertising, affiliate fees, etc?' },
    { stockCode: 'GOOOGL', question: 'How is Google enhancing its ad tech capabilities and competing with adtech rivals?' },
    { stockCode: 'MSFT', question: 'How does Azure\'s growth, market share, and offerings compare to AWS and Google Cloud?' },
    ]);
</script>
  
<style lang="scss" scoped>
    .el-header {
      margin-bottom: 30px;
      text-align: center;
  
      h2 {
        font-size: 32px;
        font-weight: bold;
        color: #333;
      }
    }

    .flex {
        display: flex;
        justify-content: center;
        align-items: center;
        gap: 16px;
    }
    
    .guide {
        margin: 20px;
        text-align: center;
    }

    .samplequestion-container {
        padding: 20px;
    }

    .list {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        gap: 20px;
    }

    .el-card {
        height: 70px;

        .card-header {
            padding: 6px;
        }

        .stock-code {
            color: #2d2e2e;
            font-weight: bold;
            font-size: 14px;
        }

        .question {
            font-size: 12px;
            color: rgba(0, 0, 0, 0.6);
            margin-left: 6px;
        }
    }

    .hover-effect:hover {
        transform: scale(1.05);
        box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
        cursor: pointer;
    }
</style>