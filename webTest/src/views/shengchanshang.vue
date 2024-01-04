<script setup>
import {ref,reactive,onMounted} from "vue";
import axios from "axios";
onMounted(()=>{
  getAll()
})
const tableData=reactive([])
const form=reactive({
  productName:"",
  productDate:"",
  productExpirationDate:"",
  productEnterpriseId:"",
  productProductionId:"",
  productionPlace:"",
})
const dialogFormVisible=ref(false)
const title=ref("生产")
const add=()=>{
  dialogFormVisible.value=true;
  title.value="生产"
}
const handleEdit=(row)=>{
  dialogFormVisible.value=true;
  title.value="修改"
  axios({
    url:"http://127.0.0.1:18080/Product/product/selectOne",
    method:"post",
    data:row
  }).then(res=>{
    Object.assign(form,res.data)
  })
}
const handleDelete=(row)=>{
  axios({
    url:"http://127.0.0.1:18080/Product/product/delete",
    method:"delete",
    data:row
  }).then(res=>{
    getAll()
  })
}
const rest=()=>{
  Object.assign(form,{
    productId:"",
    productName:"",
    productDate:"",
    productExpirationDate:"",
    productEnterpriseId:"",
    productProductionId:"",
    productionPlace:"",
  })
}
const save=()=>{
  if (form.productId){
    axios({
      url:"http://127.0.0.1:18080/Product/product/update",
      method:"post",
      data:form
    }).then(res=>{
      getAll()
    })
  }else{
    axios({
      url:"http://127.0.0.1:18080/Product/products/insert",
      method:"post",
      data:form
    }).then(res=>{
      getAll()
    })
  }
}
const getAll=()=>{
  axios({
    url:"http://127.0.0.1:18080/Product/product/All"
  }).then(res=>{
    tableData.length=0
    Object.assign(tableData,res.data)
    dialogFormVisible.value=false
  })
}
</script>

<template>
<el-button type="primary" @click="add">生产</el-button>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="productName" label="productName" min-width="100px" align="center"/>
    <el-table-column prop="productDate" label="productDate" min-width="100px" align="center"/>
    <el-table-column prop="productExpirationDate" label="productExpirationDate" min-width="100px" align="center"/>
    <el-table-column prop="productEnterpriseId" label="productEnterpriseId" min-width="100px" align="center"/>
    <el-table-column prop="productProductionId" label="productProductionId" min-width="100px" align="center"/>
    <el-table-column prop="productionPlace" label="productionPlace" min-width="100px" align="center"/>
    <el-table-column label="操作" min-width="200px" align="center">
      <template v-slot="scope">
        <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button type="danger" @click="handleDelete(scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog v-model="dialogFormVisible" @close="rest">
    <h2>{{ title }}商品</h2>
    <el-form :model="form" label-width="150px">
      <el-form-item label="productName">
        <el-input v-model="form.productName"/>
      </el-form-item>
      <el-form-item label="productDate">
        <el-date-picker
            v-model="form.productDate"
            type="date"
            placeholder="Pick a day"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item label="productExpirationDate">
        <el-date-picker
            v-model="form.productExpirationDate"
            type="date"
            placeholder="Pick a day"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item label="productEnterpriseId">
        <el-input v-model="form.productEnterpriseId"/>
      </el-form-item>
      <el-form-item label="productProductionId">
        <el-input v-model="form.productProductionId"/>
      </el-form-item>
      <el-form-item label="productionPlace">
        <el-input v-model="form.productionPlace"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false;rest()">取消</el-button>
        <el-button type="primary" @click="save">
          保存
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss">

</style>