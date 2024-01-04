<script setup>
import {ref,reactive,onMounted} from "vue";
import axios from "axios";

const product=ref("")
const logistic=ref("")
const productList=reactive([])
const logisticList=reactive([])
const res1=ref("")
const res2=ref("")
onMounted(()=>{
  axios({
    url:"http://127.0.0.1:18080/Product/product/All"
  }).then(res=>{
    Object.assign(productList,res.data)
  })

  axios({
    url:"http://127.0.0.1:18080/Logistic/logistic/All"
  }).then(res=>{
    Object.assign(logisticList,res.data)
  })
})
const sao1=()=>{
  axios({
    url:"http://127.0.0.1:18080/Qrcode/qrCode/scanProductQrCode",
    method:"post",
    data:product.value
  }).then(res=>{
    res1.value=res.data
  })
}
const sao2=()=>{
  axios({
    url:"http://127.0.0.1:18080/Qrcode/qrCode/scanLogisticQrCode",
    method:"post",
    data:logistic.value
  }).then(res=>{
    res2.value=res.data
  })
}
</script>

<template>
  <el-select v-model="product" placeholder="商品">
    <el-option
        v-for="item in productList"
        :key="item.productId"
        :label="item.productId"
        :value="item">
    </el-option>
  </el-select>
  <el-button type="primary" @click="sao1">扫描二维码</el-button>
  <el-input v-model="res1" type="textarea" placeholder="placeholder"></el-input>
  <br>
  <el-select v-model="logistic" placeholder="物流">
    <el-option
        v-for="item in logisticList"
        :key="item.logisticId"
        :label="item.logisticId"
        :value="item">
    </el-option>
  </el-select>
  <el-button type="primary" @click="sao2">扫描二维码</el-button>
  <el-input v-model="res2" type="textarea" placeholder="placeholder"></el-input>
</template>

<style scoped lang="scss">

</style>