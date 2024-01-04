<script setup>
import {ref,reactive,onMounted} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
const tableData=reactive([])
const add=()=>{
  axios({
    url:"http://127.0.0.1:18080/Contamination/contamination/insert",
    method:"post",
    data:fomr
  }).then(res=>{
    console.log(res)
    if (res.data){
      ElMessage({
        message: '添加成功',
        type: 'success',
      })
    }
  })
}
onMounted(()=>{
  getAll()
})
const getAll=()=>{
  axios({
    url:"http://127.0.0.1:18080/Product/product/All"
  }).then(res=>{
    tableData.length=0
    Object.assign(tableData,res.data)
  })
}
const fomr =reactive({
  product:"",
  description:"",
  status:"",
})
</script>

<template>
<div>
  <el-select v-model="fomr.product" placeholder="placeholder">
    <el-option
        v-for="item in tableData"
        :key="item.productId"
        :label="item.productId"
        :value="item">
    </el-option>
  </el-select>
  <el-input v-model="fomr.description" placeholder="placeholder"></el-input>
  <el-input v-model="fomr.status" placeholder="placeholder"></el-input>
  <el-button type="primary" @click="add">添加到污染表</el-button>
</div>
</template>

<style scoped lang="scss">

</style>