<script setup>
import {reactive} from 'vue'
import axios from "axios";
import {ElMessage} from "element-plus";
const form=reactive({
  userName:"",
  phone:"",
  email:"",
  nickName:"",
  gender:"",
  born:"",
  idCard:"",
  company:"",
  home:"",
  personalProfil:"",
  ip:"",
  password:"",
  createTime:"",
  role:""
})
const roleList=reactive([
  {
    value:"系统管理员",
    label:"系统管理员"
  },
  {
    value:"质监部门",
    label:"质监部门"
  },
  {
    value:"物流和超市负责人",
    label:"物流和超市负责人"
  },
  {
    value:"生产商",
    label:"生产商"
  }
])
const handleregister=()=>{
  console.log(form)
  const data={
    userName:form.userName,
    userTelephone:form.phone,
    userSysEmail:form.email,
    userPassword:form.password,
    userNickName:form.nickName,
    userGender:form.gender,
    userBornDay:form.born,
    userIdCard:form.idCard,
    userCompany:form.company,
    userHome:form.home,
    userIp:form.ip,
    userFlag:"1",
    userPersonalProfile:form.personalProfil,
    userCreateTime:form.createTime,
    roleName:form.role,
  }
  axios({
    method:"post",
    url:"http://localhost:18080/UserService/users/insert",
    data:data
  }).then(res=>{
    console.log(res)
    if (res.data){
      ElMessage({
        message: '注册成功.',
        type: 'success',
      })
    }else {
      ElMessage({
        message: '注册失败',
        type: 'warning',
      })
    }
  })
}
</script>

<template>
  <div class="registerBox">
    <el-form :model="form" label-width="50px">
      <el-form-item label="userName">
        <el-input v-model="form.userName" />
      </el-form-item>
      <el-form-item label="phone">
        <el-input v-model="form.phone" />
      </el-form-item>
      <el-form-item label="email">
        <el-input v-model="form.email" />
      </el-form-item>
      <el-form-item label="nickName">
        <el-input v-model="form.nickName" />
      </el-form-item>
      <el-form-item label="gender">
        <el-select v-model="form.gender" placeholder="性别">
          <el-option label="男" value="男"/>
          <el-option label="女" value="女"/>
        </el-select>
      </el-form-item>
      <el-form-item label="born">
        <el-date-picker
            v-model="form.born"
            type="date"
            placeholder="Pick a day"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item label="idCard">
        <el-input v-model="form.idCard" />
      </el-form-item>
      <el-form-item label="home">
        <el-input v-model="form.home" />
      </el-form-item>
      <el-form-item label="company">
        <el-input v-model="form.company" />
      </el-form-item>
      <el-form-item label="personalProfil">
        <el-input v-model="form.personalProfil" />
      </el-form-item>
      <el-form-item label="ip">
        <el-input v-model="form.ip" />
      </el-form-item>
      <el-form-item label="createTime">
        <el-date-picker
            v-model="form.createTime"
            type="date"
            placeholder="Pick a day"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" />
      </el-form-item>
      <el-form-item label="角色">
        <el-select v-model="form.role" placeholder="请选择角色">
          <el-option
              v-for="item in roleList"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleregister">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped lang="scss">
.registerBox{
  width: 500px;
  height: max-content;
  background: #ccc;
  padding: 20px;
  margin:0 auto;
  border-radius: 10px;
}
</style>