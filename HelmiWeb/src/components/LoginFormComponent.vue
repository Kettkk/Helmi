<script lang="ts" setup>
import { reactive, ref } from 'vue'
import { FormInstance, FormProps } from "element-plus";
import axios from 'axios';
import router from "../router";

const labelPosition = ref<FormProps['labelPosition']>('top');

const formLabelAlign = reactive({
  username: '',
  password: '',
});

// 表单验证规则
const formRules = reactive({
  userID: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { pattern: /^[a-zA-Z0-9]{1,20}$/, message: '用户名只能包含英文字母和数字，且长度为1-20字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { pattern: /^[a-zA-Z0-9]{1,20}$/, message: '密码只能包含英文字母和数字，且长度为1-20字符', trigger: 'blur' }
  ],
});

const formRef = ref<FormInstance>();

const handleLogin = async () => {
   //发送表单到localhost:8000/admin/login，返回的data中若为1则登录成功，否则登录失败
  const res = await axios.post('http://localhost:8000/admin/login', {
    username: formLabelAlign.username,
    password: formLabelAlign.password
  });
  if (res.data === "success") {
    console.log('登录成功');
  } else {
    console.log('登录失败');
    console.log(res.data);

  }

};

const go2SignUp = () => {
  console.log('go2SignUp');
  router.push({name: 'signupView'})
}
</script>

<template>
  <div id="formContainer">
    <div id="loginTitle">欢迎登录</div>

    <el-form
        ref="formRef"
        :model="formLabelAlign"
        :rules="formRules"
        :label-position="labelPosition"
        label-width="auto"
        style="max-width: 400px"
    >
      <el-form-item label="用户名" prop="userID">
        <el-input v-model="formLabelAlign.username" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="formLabelAlign.password" show-password />
      </el-form-item>

      <el-form-item>
        <div id="loginBtn" @click="handleLogin">登录</div>
      </el-form-item>
    </el-form>

    <div id="txtBtn" @click="go2SignUp">还没有账号？立即注册</div>
  </div>
</template>

<style scoped>
#formContainer {
  height: 500px;
  width: 450px;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  opacity: 0.9;
  padding-top: 110px;
  padding-left: 40px;
}
#loginTitle {
  font-size: 32px;
  font-weight: bold;
  color: black;
  margin-bottom: 20px;
  font-family: "Apple Braille";
}
#loginBtn{
  width: 350px;
  height: 45px;
  background-color: #409EFF;
  color: white;
  text-align: center;
  line-height: 40px;
  border-radius: 5px;
  margin-top: 60px;
  margin-left: 20px;
  cursor: pointer;
  border-radius: 15px;
}
#loginBtn:hover{
  background-color: #66b1ff;
}
#loginBtn:active {
  background-color: #3a8ee6;
}
#txtBtn{
  font-size: 12px;
  color: #409EFF;
  margin-top: 20px;
  margin-left: 130px;
  cursor: pointer;
}
#txtBtn:hover{
  text-decoration: underline;
}
#txtBtn:active {
  color: #66b1ff;
}
</style>
