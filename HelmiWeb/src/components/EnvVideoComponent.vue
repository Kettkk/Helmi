<script setup>
import { ref, watch, onMounted, onUnmounted } from "vue";
import { ElMessage } from "element-plus";

const props = defineProps({
  visible: Boolean, // 监听 dialogVideoVisible 变化
});

const canvasRef = ref(null);
let ws = null;

const startDetection = async () => {
  try {
    const response = await fetch("https://helmi.asia/start-detection", { method: "POST" });
    const result = await response.json();
    ElMessage.success(result.status);
    startWebSocket();
  } catch (error) {
    ElMessage.error("启动失败: " + error.message);
  }
};

const stopDetection = async () => {
  try {
    const response = await fetch("https://helmi.asia/stop-detection", { method: "POST" });
    const result = await response.json();
    ElMessage.success(result.status);

    stopWebSocket();

    const canvas = canvasRef.value;
    if (canvas) {
      const ctx = canvas.getContext("2d");
      ctx.clearRect(0, 0, canvas.width, canvas.height);
    }
  } catch (error) {
    ElMessage.error("停止失败: " + error.message);
  }
};

const startWebSocket = () => {
  ws = new WebSocket("wss://helmi.asia/ws");
  ws.binaryType = "arraybuffer";

  ws.onmessage = (event) => {
    const canvas = canvasRef.value;
    if (!canvas) return;

    const ctx = canvas.getContext("2d");
    const blob = new Blob([event.data], { type: "image/jpeg" });
    const url = URL.createObjectURL(blob);
    const img = new Image();

    img.onload = () => {
      ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
      URL.revokeObjectURL(url);
    };

    img.src = url;
  };

  ws.onclose = () => {
    console.log("WebSocket 连接已关闭");
  };
};

const stopWebSocket = () => {
  if (ws) {
    ws.close();
    ws = null;
  }
};

// 监听 dialogVideoVisible 变化
watch(() => props.visible, (newVal) => {
  if (newVal) {
    startDetection();
  } else {
    stopDetection();
  }
});

// 组件挂载时，如果 dialog 是打开的，直接开始检测
onMounted(() => {
  if (props.visible) {
    startDetection();
  }
});

// 组件卸载时，确保停止检测
onUnmounted(() => {
  stopDetection();
});
</script>

<template>
  <div>
    <canvas ref="canvasRef" width="640" height="480" style="border: 1px solid #ddd;"></canvas>
  </div>
</template>

<style scoped>
canvas {
  display: block;
  margin: auto;
  max-width: 100%;
  background-color: black;
}
</style>
