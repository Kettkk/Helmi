<script setup>
import LoginFormComponent from "@/components/LoginFormComponent.vue";
</script>

<template>
  <div id="viewContainer">
    <el-container>
      <el-aside width="900px">

      </el-aside>

      <el-main id="mainContainer">
        <login-form-component></login-form-component>
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>
/* 使背景图片铺满整个界面 */
#viewContainer {
  height: 100vh; /* 使用视口高度 */
  width: 100vw; /* 使用视口宽度 */
  background-image: url(/src/assets/wallPaper1.png);
  background-size: cover; /* 背景图片覆盖整个容器 */
  background-repeat: no-repeat;
  background-position: center;
}

#mainContainer {
  margin-top: 100px;
}

#mainTitleContainer {
  font-size: 70px;
  font-weight: bold;
  color: black;
  margin-top: 220px;
  margin-left: 550px;
}

#twoTitle {
  font-size: 50px;
  font-family: "Microsoft YaHei";
  color: black;
  margin-top: 20px;
  margin-left: 550px;
}

#bottomTitle {
  font-size: 15px;
  font-family: "Microsoft YaHei";
  color: whitesmoke;
  margin-top: 360px;
  margin-left: 90px;
}
</style>
