<script setup>
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
onMounted(()=>{
  getAll()
  getAllsm()
})
const lsm=reactive({
  refId:"",
  logisticId:"",
  supermarketId:"",
})
const lsmRes=reactive([{
  refId:"",
  logisticId:"",
  supermarketId:"",
}])
const tableData=reactive([])
const tableDatasm=reactive([])
const form=reactive({
  logisticId:"",
  logisticCompanyId:"",
  logisticBatchId:"",
  logisticVehicleInfo:"",
  logisticTime:"",
  logisticDestinationSupermarket:""
})
const formsm=reactive({
  supermarketId:"",
  supermarketName:"",
  supermarketAddress:"",
  supermarketContact:"",
})
const dialogFormVisible=ref(false)
const dialogFormVisiblesm=ref(false)
const title=ref("新建")
const handleEdit=(row)=>{
  dialogFormVisible.value=true;
  title.value="修改"
  axios({
    url:"http://127.0.0.1:18080/Logistic/logistic/selectOne",
    method:"post",
    data:row
  }).then(res=>{
    Object.assign(form,res.data)
  })
}
const handleEditsm=(row)=>{
  dialogFormVisiblesm.value=true;
  title.value="修改"
  axios({
    url:"http://127.0.0.1:18080/Supermarket/supermarket/selectOne",
    method:"post",
    data:row
  }).then(res=>{
    Object.assign(formsm,res.data)
  })
}
const handleDelete=(row)=>{
  axios({
    url:"http://127.0.0.1:18080/Logistic/logistic/delete",
    method:"delete",
    data:row
  }).then(res=>{
    getAll()
  })
}
const handleDeletesm=(row)=>{
  axios({
    url:"http://127.0.0.1:18080/Supermarket/supermarket/delete",
    method:"delete",
    data:row
  }).then(res=>{
    getAllsm()
  })
}
const getAll=()=>{
  axios({
    url:"http://127.0.0.1:18080/Logistic/logistic/All"
  }).then(res=>{
    tableData.length=0
    Object.assign(tableData,res.data)
    dialogFormVisible.value=false
  })
}
const getAllsm=()=>{
  axios({
    url:"http://127.0.0.1:18080/Supermarket/supermarket/All"
  }).then(res=>{
    tableDatasm.length=0
    Object.assign(tableDatasm,res.data)
    dialogFormVisiblesm.value=false
  })
}
const save=()=>{
  if (form.logisticId){
    axios({
      url:"http://127.0.0.1:18080/Logistic/logistic/update",
      method:"post",
      data:form
    }).then(res=>{
      getAll()
    })
  }else{
    axios({
      url:"http://127.0.0.1:18080/Logistic/logistic/insert",
      method:"post",
      data:form
    }).then(res=>{
      getAll()
    })
  }
}
const savesm=()=>{
  if (formsm.supermarketId){
    axios({
      url:"http://127.0.0.1:18080/Supermarket/supermarket/update",
      method:"post",
      data:formsm
    }).then(res=>{
      getAllsm()
    })
  }else{
    axios({
      url:"http://127.0.0.1:18080/Supermarket/supermarket/insert",
      method:"post",
      data:formsm
    }).then(res=>{
      getAllsm()
    })
  }
}
const rest=()=>{
  Object.assign(form,{
    logisticId:"",
    logisticCompanyId:"",
    logisticBatchId:"",
    logisticVehicleInfo:"",
    logisticTime:"",
    logisticDestinationSupermarket:""
  })
}
const restsm=()=>{
  Object.assign(formsm,{
    supermarketId:"",
    supermarketName:"",
    supermarketAddress:"",
    supermarketContact:"",
  })
}
const sendSupermarket=()=>{
  axios({
    url:"http://127.0.0.1:18080/Logistic/logistic/supermarkrt",
    method:"post",
    data:lsm
  }).then(res=>{
    lsmRes[0]=res.data
    console.log(res)
  })
}
const add=()=>{
  dialogFormVisible.value=true;
  title.value="生产"
}
const addsm=()=>{
  dialogFormVisiblesm.value=true;
  title.value="新建"
}
</script>

<template>
  <el-button type="primary" @click="add">新建运输</el-button>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="logisticId" label="logisticId" min-width="100px" align="center"/>
    <el-table-column prop="logisticCompanyId" label="logisticCompanyId" min-width="100px" align="center"/>
    <el-table-column prop="logisticBatchId" label="logisticBatchId" min-width="100px" align="center"/>
    <el-table-column prop="logisticVehicleInfo" label="logisticVehicleInfo" min-width="100px" align="center"/>
    <el-table-column prop="logisticTime" label="logisticTime" min-width="100px" align="center"/>
    <el-table-column prop="logisticDestinationSupermarket" label="logisticDestinationSupermarket" min-width="100px" align="center"/>
    <el-table-column label="操作" min-width="200px" align="center">
      <template v-slot="scope">
        <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button type="danger" @click="handleDelete(scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog v-model="dialogFormVisible" @close="rest">
    <h2>{{ title }}物流</h2>
    <el-form :model="form" label-width="150px">
      <el-form-item label="logisticCompanyId">
        <el-input v-model="form.logisticCompanyId"/>
      </el-form-item>
      <el-form-item label="logisticTime">
        <el-date-picker
            v-model="form.logisticTime"
            type="date"
            placeholder="Pick a day"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item label="logisticBatchId">
        <el-input v-model="form.logisticBatchId"/>
      </el-form-item>
      <el-form-item label="logisticVehicleInfo">
        <el-input v-model="form.logisticVehicleInfo"/>
      </el-form-item>
      <el-form-item label="logisticDestinationSupermarket">
        <el-input v-model="form.logisticDestinationSupermarket"/>
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
  <br>
  <el-button type="primary" @click="addsm">新建超市</el-button>
  <el-table :data="tableDatasm" style="width: 100%">
    <el-table-column prop="supermarketId" label="supermarketId" min-width="100px" align="center"/>
    <el-table-column prop="supermarketName" label="supermarketName" min-width="100px" align="center"/>
    <el-table-column prop="supermarketAddress" label="supermarketAddress" min-width="100px" align="center"/>
    <el-table-column prop="supermarketContact" label="supermarketContact" min-width="100px" align="center"/>
    <el-table-column label="操作" min-width="200px" align="center">
      <template v-slot="scope">
        <el-button type="primary" @click="handleEditsm(scope.row)">编辑</el-button>
        <el-button type="danger" @click="handleDeletesm(scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog v-model="dialogFormVisiblesm" @close="restsm">
    <h2>{{ title }}物流</h2>
    <el-form :model="formsm" label-width="150px">
      <el-form-item label="supermarketName">
        <el-input v-model="formsm.supermarketName"/>
      </el-form-item>
      <el-form-item label="supermarketAddress">
        <el-input v-model="formsm.supermarketAddress"/>
      </el-form-item>
      <el-form-item label="supermarketContact">
        <el-input v-model="formsm.supermarketContact"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisiblesm = false;restsm()">取消</el-button>
        <el-button type="primary" @click="savesm">
          保存
        </el-button>
      </span>
    </template>
  </el-dialog>
  <el-select v-model="lsm.logisticId" placeholder="placeholder">
    <el-option
        v-for="item in tableData"
        :key="item.logisticId"
        :label="item.logisticId"
        :value="item.logisticId">
    </el-option>
  </el-select>
  <el-select v-model="lsm.supermarketId" placeholder="placeholder">
    <el-option
        v-for="item in tableDatasm"
        :key="item.supermarketId"
        :label="item.supermarketId"
        :value="item.supermarketId">
    </el-option>
  </el-select>
  <el-button type="primary" @click="sendSupermarket">生成物流和超市订单</el-button>
  <el-table :data="lsmRes" style="width: 100%">
    <el-table-column prop="refId" label="refId" min-width="100px" align="center"/>
    <el-table-column prop="logisticId" label="logisticId" min-width="100px" align="center"/>
    <el-table-column prop="supermarketId" label="supermarketId" min-width="100px" align="center"/>
  </el-table>
</template>

<style scoped lang="scss">

</style>