<script setup>
import {reactive,ref} from 'vue'
import axios from "axios";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
const form=reactive({
  userName:"",
  password:"",
  phone:"",
  email:"",
  role:""
})
const router=useRouter()
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
const handleLogin=()=>{
  if (activeName.value=="userName"){
    const data={
      userName:form.userName,
      userPassword:form.password,
      roleName:form.role
    }
    axios({
      method:"post",
      url:"http://localhost:18080/UserService/loginByUserName",
      data:data
    }).then(res=>{
      console.log(res)
      if(res.data){
        ElMessage({
          message: '登录成功',
          type: 'success',
        })
        if (data.roleName=="系统管理员"){
          router.push("/guanliyuan")
        }
        else if (data.roleName=="生产商"){
          router.push("/shengchanshang")
        }
        else if (data.roleName=="物流和超市负责人"){
          router.push("/wuliu")
        }
        else if (data.roleName=="质监部门"){
          router.push("/zhijian")
        }
      }else {
        ElMessage({
          message: '登录失败 请检查信息真实性',
          type: 'warning',
        })
      }
    })
  }
  else if (activeName.value=="phone"){
    const data={
      userTelephone:form.phone,
      userPassword:form.password,
      roleName:form.role
    }
    axios({
      method:"post",
      url:"http://localhost:18080/UserService/loginByUserTelephone",
      data:data
    }).then(res=>{
      console.log(res)
      if(res.data){
        ElMessage({
          message: '登录成功',
          type: 'success',
        })
        if (data.roleName=="系统管理员"){
          router.push("/guanliyuan")
        }
        else if (data.roleName=="生产商"){
          router.push("/shengchanshang")
        }
        else if (data.roleName=="物流和超市负责人"){
          router.push("/wuliu")
        }
        else if (data.roleName=="质监部门"){
          router.push("/zhijian")
        }
      }else {
        ElMessage({
          message: '登录失败 请检查信息真实性',
          type: 'warning',
        })
      }
    })
    console.log(data)

  }
  else if (activeName.value=="email"){
    const data={
      userSysEmail:form.email,
      userPassword:form.password,
      roleName:form.role
    }
    axios({
      method:"post",
      url:"http://localhost:18080/UserService/loginByUserEmail",
      data:data
    }).then(res=>{
      console.log(res)
      if(res.data){
        ElMessage({
          message: '登录成功',
          type: 'success',
        })
        if (data.roleName=="系统管理员"){
          router.push("/guanliyuan")
        }
        else if (data.roleName=="生产商"){
          router.push("/shengchanshang")
        }
        else if (data.roleName=="物流和超市负责人"){
          router.push("/wuliu")
        }
        else if (data.roleName=="质监部门"){
          router.push("/zhijian")
        }
      }else {
        ElMessage({
          message: '登录失败 请检查信息真实性',
          type: 'warning',
        })
      }
    })
    console.log(data)

  }
}
const goRegister=()=>{
  router.push("/register")
}
const activeName = ref('userName')
</script>

<template>
<div class="loginBox">
  <el-tabs v-model="activeName" class="demo-tabs" >
    <el-tab-pane label="用户名" name="userName">
      <el-form :model="form" label-width="50px">
        <el-form-item label="账号">
          <el-input v-model="form.userName" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" />
        </el-form-item>
        <el-form-item label="身份">
          <el-select v-model="form.role" placeholder="请选择身份">
            <el-option
                v-for="item in roleList"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin">登录</el-button>
          <el-button type="primary" @click="goRegister">注册</el-button>
        </el-form-item>
      </el-form>
    </el-tab-pane>
    <el-tab-pane label="手机号" name="phone">
      <el-form :model="form" label-width="80px">
        <el-form-item label="手机号">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" />
        </el-form-item>
        <el-form-item label="身份">
          <el-select v-model="form.role" placeholder="请选择身份">
            <el-option
                v-for="item in roleList"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin">登录</el-button>
          <el-button type="primary" @click="goRegister">注册</el-button>
        </el-form-item>
      </el-form>
    </el-tab-pane>
    <el-tab-pane label="邮箱" name="email">
      <el-form :model="form" label-width="50px">
        <el-form-item label="邮箱">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" />
        </el-form-item>
        <el-form-item label="身份">
          <el-select v-model="form.role" placeholder="请选择身份">
            <el-option
                v-for="item in roleList"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin">登录</el-button>
          <el-button type="primary" @click="goRegister">注册</el-button>
        </el-form-item>
      </el-form>
    </el-tab-pane>
  </el-tabs>
</div>
</template>

<style scoped lang="scss">
.loginBox{
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  width: 500px;
  height: max-content;
  background: #ccc;
  padding: 20px;
  margin:auto;
  border-radius: 10px;
}
</style>