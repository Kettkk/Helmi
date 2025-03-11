<script lang="ts" setup>
import {onMounted, reactive, ref} from 'vue'
import {TabsPaneContext} from "element-plus";


const activeName = ref('first')

const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event)
}

const tableData = []
onMounted(() => {
  axios.post('http://localhost:5062/api/AssetManagement/GetAllAssets')
      .then(function (response) {
        tableData.push(response.data)
        console.log(response.data)
      })
      .catch(function (error) {
        console.log(error)
      })
})

import type { FormInstance, FormRules } from 'element-plus'
import axios from "axios";

const ruleFormRef = ref<FormInstance>()

const checkAge = (rule: any, value: any, callback: any) => {
  if (!value) {
    return callback(new Error('Please input the age'))
  }
  setTimeout(() => {
    if (!Number.isInteger(value)) {
      callback(new Error('Please input digits'))
    } else {
      if (value < 18) {
        callback(new Error('Age must be greater than 18'))
      } else {
        callback()
      }
    }
  }, 1000)
}

const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  } else {
    if (ruleForm.checkPass !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('checkPass')
    }
    callback()
  }
}
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== ruleForm.pass) {
    callback(new Error("两次输入的密码不相同"))
  } else {
    callback()
  }
}

const ruleForm = reactive({
  pass: '',
  checkPass: '',
})

const rules = reactive<FormRules<typeof ruleForm>>({
  pass: [{ validator: validatePass, trigger: 'blur' }],
  checkPass: [{ validator: validatePass2, trigger: 'blur' }],
})

const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      axios.post('http://localhost:5062/api/PassUpdate', {
        userID: document.cookie.split('=')[1],
        password: ruleForm.checkPass,
      })
          .then(function (response) {
            console.log(response.data)
            if (response.data == 'Password updated successfully') {
              console.log('提交成功!')
            } else {
              console.log('提交失败!')
            }
          })
          .catch(function (error) {
            console.log(error)
            console.log('提交出错!')
          })
    } else {
      console.log('提交出错!')
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>

<template>
  <div>
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
      <el-tab-pane label="个人中心" name="first">
        <el-form
            ref="ruleFormRef"
            style="max-width: 600px"
            :model="ruleForm"
            status-icon
            :rules="rules"
            label-width="auto"
            class="demo-ruleForm"
        >
          <el-form-item label="密码" prop="pass">
            <el-input v-model="ruleForm.pass" type="password" autocomplete="off" />
          </el-form-item>
          <el-form-item label="再次输入密码" prop="checkPass">
            <el-input
                v-model="ruleForm.checkPass"
                type="password"
                autocomplete="off"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm(ruleFormRef)">
              确认修改
            </el-button>
            <el-button @click="resetForm(ruleFormRef)">清空</el-button>
          </el-form-item>
        </el-form>

      </el-tab-pane>

      <el-tab-pane label="资产管理" name="second">
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column prop="资产编号" label="AssetID" width="180" />
          <el-table-column prop="用户名" label="UserID" width="180" />
          <el-table-column prop="资产名称" label="AssetName" />
          <el-table-column prop="数量" label="Quantity" />
          <el-table-column prop="购买日期" label="PurchaseDate" />
        </el-table>
      </el-tab-pane>

    </el-tabs>
  </div>


</template>

<style scoped>
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
</style>