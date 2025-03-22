<template>
  <el-menu :default-active="$route.path" router class="el-menu-demo" mode="vertical">
    <el-menu-item index="/adminView/home">
      <el-icon><HomeFilled /></el-icon>
      <span>首页</span>
    </el-menu-item>
    <el-menu-item index="/adminView/userList">
      <el-icon>
        <User />
      </el-icon>
      <span>用户列表</span>
    </el-menu-item>
  </el-menu>
</template>

<script setup>
// 你的脚本代码
</script>

<style scoped>


/* 自定义选中菜单项的样式 */
.el-menu-item.is-active {
  background-color: #409EFF;
  /* 背景颜色为蓝色 */
  color: white;
  /* 文字颜色为白色 */
}

/* 鼠标悬停时的样式 */
.el-menu-item:hover {
  background-color: #ecf5ff;
  /* 背景颜色为浅蓝色 */
  color: #409EFF;
  /* 文字颜色为蓝色 */
}
.html{
  padding: 0px;
}
</style>
