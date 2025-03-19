<template>
  <div class="header">
    <span>控制台</span>
    <div class="header-right">
      <img src="../assets/home.png" alt="主页" class="logout-btn" />
      <el-avatar
        src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
        class="avatar"
      />
      <span class="admin-name">admin-name</span>
      <img src="../assets/exit.png" alt="退出" class="logout-btn" />
    </div>
  </div>
  <div class="box">
    <!-- 整体布局 -->
    <div class="main-layout">
      <!-- 左侧饼图区域 -->
      <div class="left-pies">
        <el-card class="chart-card">
          <template #header>
            <div class="chart-card-header">营收分布图</div>
          </template>
          <div ref="pieChartRef1" class="chart"></div>
        </el-card>
        <el-card class="chart-card">
          <template #header>
            <div class="chart-card-header">成本分布图</div>
          </template>
          <div ref="pieChartRef2" class="chart"></div>
        </el-card>
      </div>
      <!-- 右侧内容区域 -->
      <div class="right-content">
        <!-- 第一个饼图右侧内容 -->
        <div class="right-top">
          <el-row :gutter="16">
            <el-col :span="8">
              <div class="statistic-card">
                <el-statistic :value="693700">
                  <template #title>
                    <div style="display: inline-flex; align-items: center">
                      本月营收
                      <el-tooltip
                        effect="dark"
                        content="本月至目前为止的所有收益"
                        placement="top"
                      >
                        <el-icon style="margin-left: 4px" :size="12">
                          <Warning />
                        </el-icon>
                      </el-tooltip>
                    </div>
                  </template>
                </el-statistic>
                <div class="statistic-footer">
                  <div class="footer-item">
                    <span>同比上个月</span>
                    <span class="red">
                      24%
                      <el-icon>
                        <CaretTop />
                      </el-icon>
                    </span>
                  </div>
                </div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="statistic-card">
                <el-statistic :value="98500">
                  <template #title>
                    <div style="display: inline-flex; align-items: center">
                      本月销售量
                      <el-tooltip
                        effect="dark"
                        content="本月至目前为止的产品销售总量"
                        placement="top"
                      >
                        <el-icon style="margin-left: 4px" :size="12">
                          <Warning />
                        </el-icon>
                      </el-tooltip>
                    </div>
                  </template>
                </el-statistic>
                <div class="statistic-footer">
                  <div class="footer-item">
                    <span>同比上个月</span>
                    <span class="red">
                      12%
                      <el-icon>
                        <CaretTop />
                      </el-icon>
                    </span>
                  </div>
                </div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="statistic-card">
                <el-statistic :value="102000" title="用户数量">
                  <template #title>
                    <div style="display: inline-flex; align-items: center">
                      用户数量
                      <el-tooltip
                        effect="dark"
                        content="产品发布至今的用户总量"
                        placement="top"
                      >
                        <el-icon style="margin-left: 4px" :size="12">
                          <Warning />
                        </el-icon>
                      </el-tooltip>
                    </div>
                  </template>
                </el-statistic>
                <div class="statistic-footer">
                  <div class="footer-item">
                    <span>同比去年</span>
                    <span class="red">
                      16%
                      <el-icon>
                        <CaretTop />
                      </el-icon>
                    </span>
                  </div>
                  <div class="footer-item">
                    <el-icon :size="14">
                      <ArrowRight />
                    </el-icon>
                  </div>
                </div>
              </div>
            </el-col>
          </el-row>
          <el-card class="chart-card">
            <template #header>
              <div class="chart-card-header">营收对比图</div>
            </template>
            <div ref="lineChartRef" class="chart"></div>
          </el-card>
        </div>
        <!-- 第二个饼图右侧内容 -->
        <div class="right-bottom">
          <el-card class="chart-card">
            <template #header>
              <div class="chart-card-header">销售量对比图</div>
            </template>
            <div ref="barChartRef" class="chart"></div>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import 'element-plus/dist/index.css';
import * as echarts from 'echarts';
import 'echarts-gl';
import {
  ArrowRight,
  CaretBottom,
  CaretTop,
  Warning,
} from '@element-plus/icons-vue'

// 图表引用
const barChartRef = ref(null);
const pieChartRef1 = ref(null);
const pieChartRef2 = ref(null);
const lineChartRef = ref(null);

onMounted(() => {
  // 初始化柱形图
  const barChart = echarts.init(barChartRef.value);
  const barOption = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    xAxis: {
      type: 'category',
      data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '今年每月销售量',
        type: 'bar',
        data: [120, 150, 180, 200, 220, 250, 230, 260, 280, 300, 320, 350] // 今年每月销售量数据
      },
      {
        name: '去年每月销售量',
        type: 'bar',
        data: [80, 100, 120, 140, 160, 180, 150, 170, 190, 210, 230, 250] // 去年每月销售量数据
      }
    ],
    legend: {
      right: 10,
      orient: 'vertical'
    }
  };
  barChart.setOption(barOption);
  window.addEventListener('resize', () => barChart.resize());

  // 初始化第一个饼图
  const pieChart1 = echarts.init(pieChartRef1.value);
  const pieOption1 = {
    tooltip: {
      trigger: 'item'
    },
    series: [
      {
        type: 'pie',
        radius: '50%',
        data: [
          { value: 335, name: '直接访问' },
          { value: 310, name: '邮件营销' },
          { value: 234, name: '联盟广告' },
          { value: 135, name: '视频广告' },
          { value: 1548, name: '搜索引擎' }
        ]
      }
    ]
  };
  pieChart1.setOption(pieOption1);
  window.addEventListener('resize', () => pieChart1.resize());

  // 初始化第二个饼图
  const pieChart2 = echarts.init(pieChartRef2.value);
  const pieOption2 = {
    tooltip: {
      trigger: 'item'
    },
    series: [
      {
        type: 'pie',
        radius: '50%',
        data: [
          { value: 335, name: '直接访问' },
          { value: 310, name: '邮件营销' },
          { value: 234, name: '联盟广告' },
          { value: 135, name: '视频广告' },
          { value: 1548, name: '搜索引擎' }
        ]
      }
    ]
  };
  pieChart2.setOption(pieOption2);
  window.addEventListener('resize', () => pieChart2.resize());

  // 初始化折线图
  const lineChart = echarts.init(lineChartRef.value);
  const lineOption = {
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '今年实际营收', // 添加名称，用于图例显示
        data: [850, 1270, 1150, 1591, 1788, 1860, 1790, 1899, 2500, 2490, 3070, 4090],
        type: 'line'
      },
      {
        name: '今年预计营收', // 添加名称，用于图例显示
        data: [800, 910, 1020, 1320, 1440, 1590, 1700, 1750, 2100, 2190, 2560, 2999], // 假设的去年营收数据
        type: 'line'
      }
    ],
    legend: {
      right: 10, // 图例在右边显示
      orient: 'vertical' // 图例垂直排列
    }
  };
  lineChart.setOption(lineOption);
  window.addEventListener('resize', () => lineChart.resize());
});
</script>

<style scoped lang="scss">
.header {
  height: 50px;
  padding: 0 20px;
  background-color: #333;
  color: white;
  display: flex;
  align-items: center;
  border-radius: 4px;
  justify-content: space-between;
}

.header-right {
  display: flex;
  align-items: center;
}

.avatar {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 10px;
  margin-left: 20px;
}

.admin-name {
  font-size: 14px;
  line-height: 50px;
  margin-right: 15px;
  color: #d4d1d1;
}

.logout-btn {
  width: 20px;
  height: 20px;
  cursor: pointer;
}

.box {
  padding: 20px;
  background-color: whitesmoke;
}

.main-layout {
  display: flex;
  gap: 20px;
}

.left-pies {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 30%;
}

.right-content {
  width: 70%;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.right-top {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.statistic-card {
  padding: 20px;
  border-radius: 8px;
  background-color: var(--el-bg-color-overlay);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.statistic-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  font-size: 12px;
  color: var(--el-text-color-regular);
  margin-top: 16px;
}

.statistic-footer .footer-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.statistic-footer .footer-item span:last-child {
  display: inline-flex;
  align-items: center;
  margin-left: 4px;
}

.green {
  color: var(--el-color-success);
}
.red {
  color: var(--el-color-error);
}

.chart-card {
  background-color: white;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}

.chart-card-header {
  font-size: 18px;
  font-weight: bold;
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
}

.chart {
  width: 100%;
  height: 400px;
  border-bottom-left-radius: 8px;
  border-bottom-right-radius: 8px;
}

.el-card__footer {
  padding: 10px;
  text-align: center;
}
</style>
