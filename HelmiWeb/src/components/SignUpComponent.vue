<script lang="ts" setup>
import { reactive, ref } from 'vue';
import { ElMessage, FormProps } from 'element-plus';
import router from '../router/index.js';
import axios from 'axios';

const labelPosition = ref<FormProps['labelPosition']>('top');

const formLabelAlign = reactive({
  username: '',
  password: '',
  email: ''
});

const formRef = ref(null); // 引用表单实例

// 表单验证规则
const FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { pattern: /^[a-zA-Z]{1,10}$/, message: '用户名只能包含英文字母，且长度为1-10字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { pattern: /^[a-zA-Z0-9]{6,20}$/, message: '密码只能包含英文字母和数字，且长度为6-20字符', trigger: 'blur' }
  ],
};

const SignUpMsg = () => {
  ElMessage({
    message: '注册成功',
    type: 'success'
  });
};

const go2Login = () => {
  console.log('go2Login');
  router.push('/');
};

const submit = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      axios.post('http://localhost:5062/api/test/InsertTest', {
        username: formLabelAlign.username,
        password: formLabelAlign.password,
        email: formLabelAlign.email
      })
          .then(function (response) {
            console.log(response.data);
            if (response.data == 'insert successful') {
              router.push('/');
              SignUpMsg();
            } else {
              ElMessage.error('注册失败');
            }
          })
          .catch(function (error) {
            console.log(error);
            ElMessage.error('请求失败');
          });
    } else {
      console.log('验证失败');
      ElMessage.error('请检查表单输入');
      return false;
    }
  });
};
</script>

<template>
  <div id="formContainer">
    <div id="loginTitle">用户注册</div>

    <el-form
        :label-position="labelPosition"
        label-width="auto"
        :model="formLabelAlign"
        :rules="FormRules"
        ref="formRef"
        style="max-width: 400px"
    >
      <el-form-item label="用户名" prop="username">
        <el-input v-model="formLabelAlign.username" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="formLabelAlign.password" :show-password="true"/>
      </el-form-item>
    </el-form>

    <div style="display: flex">
      <div id="loginBtn" @click="submit">注册</div>
      <div id="loginBtn" style="margin-left: 120px" @click="go2Login">返回</div>
    </div>
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
  font-family: 'Apple Braille';
}
#loginBtn {
  width: 100px;
  height: 40px;
  background-color: #409eff;
  color: white;
  text-align: center;
  line-height: 40px;
  border-radius: 5px;
  margin-top: 60px;
  margin-left: 20px;
  cursor: pointer;
  border-radius: 15px;
}
#loginBtn:hover {
  background-color: #66b1ff;
}
#loginBtn:active {
  background-color: #3a8ee6;
}
</style>
