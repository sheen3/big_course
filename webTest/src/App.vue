<script setup>
import { RouterLink, RouterView } from 'vue-router'
import {useRouter} from "vue-router";
const router = useRouter()
</script>

<template>
  <div style="display: flex;">
    <el-button @click="router.push('login')">登录</el-button>
    <el-button @click="router.push('register')">注册</el-button>
    <el-button @click="router.push('guanliyuan')">管理员</el-button>
    <el-button @click="router.push('shengchanshang')">生产商</el-button>
    <el-button @click="router.push('wuliu')">物流</el-button>
    <el-button @click="router.push('zhijian')">质检</el-button>
    <el-button @click="router.push('/')">首页</el-button>
  </div>
  <RouterView />
</template>

<style scoped>
</style>
