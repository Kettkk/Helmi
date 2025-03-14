<script setup>
import { ref } from 'vue'

const value1 = ref(true)
const value2 = ref(true)
</script>

<template>

  <el-switch
    v-model="value2"
    class="ml-2"
    style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
  />
</template>

<style scoped>


</style>
