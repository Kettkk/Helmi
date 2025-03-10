<script setup>
import SignUpComponent from "@/components/SignUpComponent.vue";

</script>

<template>
  <div id="viewContainer">
    <el-container>
      <el-aside width="900px">
        <div>
          <img src="/Users/fantant/Documents/DevFile/Project/dotNetProject/dotnetEXAM/src/assets/nameTitle.png" style="width: 500px; height: auto;margin-top: 40px;margin-left: 30px">
        </div>

        <div id="mainTitleContainer" style="margin-left: 220px">
          Title
        </div>

        <div id="twoTitle">
          2nd Title lalalalalallalalala
        </div>

        <div id="bottomTitle">
          帮助中心 | 2024 ©HDU Asp.Net
        </div>


      </el-aside>

      <el-main id="mainContainer">
        <sign-up-component></sign-up-component>
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>
#viewContainer {
  height: 1000px;
  width: 1500px;
  background-image: url(/Users/fantant/Documents/DevFile/Project/dotNetProject/dotnetEXAM/src/assets/Catalina Mountains Island Sunset.jpg);
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}
#mainContainer{
  margin-top: 100px;
}
#mainTitleContainer {
  font-size: 65px;
  font-weight: bold;
  color: white;
  margin-top: 220px;
}
#twoTitle {
  font-size: 45px;
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