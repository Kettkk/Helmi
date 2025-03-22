<template>
  <div class="user-list">
    <div style="margin-bottom: 15px;">
      <el-input style="width: 240px" placeholder="搜索" :suffix-icon="Search" />
    </div>

    <el-table :data="tableData" border stripe style="width: 100%"
      :header-cell-style="{ background: '#f5f7fa', color: '#606266', fontSize: '14px' }"
      :cell-style="{ padding: '8px', fontSize: '14px' }">
      <el-table-column label="环境识别" width="120">
        <template #default="{ }">
          <el-button type="primary" size="small" @click="dialogVideoVisible = true">视频</el-button>
        </template>
      </el-table-column>

      <el-table-column prop="device_id" label="设备编号" width="120" />
      <el-table-column prop="user_id" label="用户编号" width="120" />
      <el-table-column prop="username" label="用户名" width="120" />
      <el-table-column prop="is_wear" label="是否佩戴" width="100">
        <template #default="{ row }">
          <el-tag :type="row.is_wear === '1' ? 'success' : 'danger'">
            {{ row.is_wear === '1' ? '是' : '否' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="temperature" label="体温" width="100" />
      <el-table-column prop="env_temperature" label="环境温度" width="120" />
      <el-table-column prop="env_humidity" label="环境湿度" width="120" />
      <el-table-column prop="heart_rate" label="心率" width="100" />
      <el-table-column prop="longitude" label="经度" width="120" />
      <el-table-column prop="latitude" label="纬度" width="120" />
      <el-table-column prop="high_pressure" label="收缩压" width="100" />
      <el-table-column prop="low_pressure" label="舒张压" width="100" />
      <el-table-column prop="body_pressure" label="摔倒压力" width="120" />
      <el-table-column prop="speed" label="配速" width="100" />
      <el-table-column prop="create_time" label="创建时间" width="180" />
      <el-table-column prop="last_update_time" label="更新时间" width="180" />

    </el-table>

    <el-dialog v-model="dialogVideoVisible" title="环境识别" width="800">
      <env-video-component :visible="dialogVideoVisible"></env-video-component>
    </el-dialog>
  </div>
</template>

<script setup>
import { Search } from '@element-plus/icons-vue'
import axios from 'axios';
import EnvVideoComponent from "@/components/EnvVideoComponent.vue"; // 引入 axios

import { ref, onMounted, onUnmounted } from 'vue';

const tableData = ref([]);
const dialogVideoVisible = ref(false);
let intervalId = null; // 存储定时器 ID

const fetchData = async () => {
  try {
    const response = await axios.get('http://124.71.159.132:8000/statu/getAllStatu');
    console.log(response.data)
    tableData.value = response.data;
  } catch (error) {
    console.error('请求数据失败:', error);
  }
};

onMounted(() => {
  fetchData();
  intervalId = setInterval(fetchData, 6500); // 每 6.5 秒更新数据
});

onUnmounted(() => {
  clearInterval(intervalId); // 组件销毁时清除定时器
});

</script>
<style lang="scss" scoped>
.user-list {
  padding: 20px;
}
</style>
