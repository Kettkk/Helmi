<script setup lang="ts">
import SideBarComponent from "../components/SideBarComponent.vue";

</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px">
        <SideBarComponent></SideBarComponent>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>
.el-main {
  padding: 0;
}
</style>
