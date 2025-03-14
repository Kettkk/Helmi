<script setup>
import LoginFormComponent from "@/components/LoginFormComponent.vue";
</script>

<template>
<div id="viewContainer">
  <el-container>
    <el-aside width="900px">
      <div id="mainTitleContainer">
        智安行
      </div>
      <div id="twoTitle">
        户外智能头盔
      </div>

      <div id="bottomTitle">
        帮助中心 | 2024 ©TZC | 京ICP备2024000001号
      </div>
<!--      <img src="http://helmi.asia/video" style="width: 500px; height: auto;margin-top: 40px;margin-left: 30px">-->
    </el-aside>

    <el-main id="mainContainer">
      <login-form-component></login-form-component>
    </el-main>
  </el-container>
</div>
</template>

<style scoped>
#viewContainer {
  height: 1000px;
  width: 1500px;
  background-image: url(/src/assets/wallPaper.png);
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}
#mainContainer{
  margin-top: 100px;
}
#mainTitleContainer {
  font-size: 70px;
  font-weight: bold;
  color: white;
  margin-top: 220px;
  margin-left: 220px;
}
#twoTitle {
  font-size: 50px;
  font-family: "Microsoft YaHei";
  color: white;
  margin-top: 20px;
  margin-left: 220px;
}
#bottomTitle {
  font-size: 15px;
  font-family: "Microsoft YaHei";
  color: white;
  margin-top: 360px;
  margin-left: 90px;
}
</style>
