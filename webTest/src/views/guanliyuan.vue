<script setup>
import {reactive, onMounted, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";

onMounted(() => {
  axios({
    url: "http://127.0.0.1:18080/UserService/users/All",
  }).then(res => {
    Object.assign(tableData, res.data)
  })

  axios({
    url: "http://127.0.0.1:18080/Role/roles/All",
  }).then(res => {
    Object.assign(tableDataRole, res.data)
  })

  axios({
    url:"http://127.0.0.1:18080/Excel/excel/ProductExcelGet"
  }).then(res=>{
    baobiao.value=res.data
  })
})
const baobiaoStatus = ref(false)
const baobiao=ref("")
const tableData = reactive([])
const dialogFormVisible = ref(false)
const dialogFormVisibleRole = ref(false)
const title = ref("新增")
const form = reactive({
  userId: "",
  userName: "",
  userTelephone: "",
  userSysEmail: "",
  userPassword: "",
  userNickName: "",
  userGender: "",
  userBornDay: "",
  userIdCard: "",
  userCompany: "",
  userHome: "",
  userIp: "",
  userFlag: "1",
  userPersonalProfile: "",
  userCreateTime: "",
  roleName: "",
})
const roleList = reactive([
  {
    value: "系统管理员",
    label: "系统管理员"
  },
  {
    value: "质监部门",
    label: "质监部门"
  },
  {
    value: "物流和超市负责人",
    label: "物流和超市负责人"
  },
  {
    value: "生产商",
    label: "生产商"
  }
])
const handleAdd = () => {
  title.value = "新增"
  dialogFormVisible.value = true
}
const handleEdit = (row) => {
  axios({
    url: "http://127.0.0.1:18080/UserService/users/selectOne",
    method: "post",
    data: row
  }).then(res => {
    title.value = "修改"
    dialogFormVisible.value = true
    Object.assign(form, res.data)
  })

}
const handleDelete = (row) => {
  axios({
    url: "http://127.0.0.1:18080/UserService/users/delete",
    method: "delete",
    data: {
      userId: row.userId
    }
  }).then(res => {
    if (res.data) {
      ElMessage({
        message: '删除成功.',
        type: 'success',
      })

      axios({
        url: "http://127.0.0.1:18080/UserService/users/All",
      }).then(resres => {
        dialogFormVisible.value = false
        tableData.length = 0
        Object.assign(tableData, resres.data)
      })
    } else {
      ElMessage({
        message: '删除失败.',
        type: 'warning',
      })
    }
  })
}
const saveUserInfo = () => {
  if (form.userId) {
    axios({
      url: "http://127.0.0.1:18080/UserService/users/update",
      method: "post",
      data: form,
    }).then(res => {
      if (res.data) {
        ElMessage({
          message: '修改成功.',
          type: 'success',
        })
        dialogFormVisible.value = false
        axios({
          url: "http://127.0.0.1:18080/UserService/users/All",
        }).then(res => {
          Object.assign(tableData, res.data)
        })
      } else {
        ElMessage({
          message: '修改失败.',
          type: 'warning',
        })
      }
    })
  } else {
    axios({
      url: "http://127.0.0.1:18080/UserService/users/insert",
      method: "post",
      data: form,
    }).then(res => {
      if (res.data) {
        ElMessage({
          message: '新建成功.',
          type: 'success',
        })
        dialogFormVisible.value = false
        axios({
          url: "http://127.0.0.1:18080/UserService/users/All",
        }).then(res => {
          Object.assign(tableData, res.data)
        })
      } else {
        ElMessage({
          message: '新建失败.',
          type: 'warning',
        })
      }
    })
  }
}
const rest = () => {
  Object.assign(form, {
    userId: "",
    userName: "",
    userTelephone: "",
    userSysEmail: "",
    userPassword: "",
    userNickName: "",
    userGender: "",
    userBornDay: "",
    userIdCard: "",
    userCompany: "",
    userHome: "",
    userIp: "",
    userFlag: "1",
    userPersonalProfile: "",
    userCreateTime: "",
    roleName: "",
  })
}


const handleAddRole = () => {
  dialogFormVisibleRole.value = true
  title.value = "新增"
}

const tableDataRole = reactive([
  {
    roleId: "",
    roleName: "",
    roleCreateTime: "",
    roleStatusFlag: "",
    roleRemark: "",
  }
])
const formRole = reactive({
  roleId: "",
  roleName: "",
  roleCreateTime: "",
  roleStatusFlag: "",
  roleRemark: "",
})
const restRole = () => {
  Object.assign(formRole, {
    roleId: "",
    roleName: "",
    roleCreateTime: "",
    roleStatusFlag: "",
    roleRemark: "",
  })
}
const handleEditRole = (row) => {

  axios({
    url: "http://127.0.0.1:18080/Role/roles/selectOne",
    method: "post",
    data: row
  }).then(res => {
    dialogFormVisibleRole.value = true
    title.value = "修改"
    Object.assign(formRole, res.data)
  })
}
const handleDeleteRole = (row) => {
  axios({
    url: "http://127.0.0.1:18080/Role/roles/delete",
    method:"delete",
    data: {
      roleId: row.roleId
    }
  }).then(res => {
    dialogFormVisibleRole.value = false
    if (res.data) {
      ElMessage({
        message: '删除成功.',
        type: 'success',
      })
      axios({
        url: "http://127.0.0.1:18080/Role/roles/All",
      }).then(res => {
        tableDataRole.length=0
        Object.assign(tableDataRole, res.data)
      })
    } else {
      ElMessage({
        message: '删除失败.',
        type: 'warning',
      })
    }
  })
}
const saveRole = () => {
  if (formRole.roleId){
    axios({
      url: "http://127.0.0.1:18080/Role/role/update",
      method: "post",
      data: formRole
    }).then(res => {
      dialogFormVisibleRole.value = false
      if (res.data) {
        ElMessage({
          message: '修改成功.',
          type: 'success',
        })
        axios({
          url: "http://127.0.0.1:18080/Role/roles/All",
        }).then(res => {
          Object.assign(tableDataRole, res.data)
        })
      } else {
        ElMessage({
          message: '修改失败.',
          type: 'warning',
        })
      }
    })
  }else{
    axios({
      url: "http://127.0.0.1:18080/Role/role/insert",
      method: "post",
      data: formRole
    }).then(res => {
      dialogFormVisibleRole.value = false
      if (res.data) {
        ElMessage({
          message: '新建成功.',
          type: 'success',
        })
        axios({
          url: "http://127.0.0.1:18080/Role/roles/All",
        }).then(res => {
          Object.assign(tableDataRole, res.data)
        })
      } else {
        ElMessage({
          message: '新建失败.',
          type: 'warning',
        })
      }
    })
  }
}
const out1=()=>{
  baobiaoStatus.value=true;
  axios({
    url:"http://127.0.0.1:18080/Excel/excel/ProductExcelGet",
  }).then(res=>{
    baobiao.value=res.data
  })
}
const out2=()=>{
  baobiaoStatus.value=true;
  axios({
    url:"http://127.0.0.1:18080/Excel/excel/LogisticExcelGet",
  }).then(res=>{
    baobiao.value=res.data
  })
}
const out3=()=>{
  baobiaoStatus.value=true;
  axios({
    url:"http://127.0.0.1:18080/Excel/excel/SupermarketExcelGet",
  }).then(res=>{
    baobiao.value=res.data
  })
}
const out4=()=>{
  baobiaoStatus.value=true;
  axios({
    url:"http://127.0.0.1:18080/Excel/excel/ContaminationExcelGet",
  }).then(res=>{
    baobiao.value=res.data
  })
}
</script>

<template>
  <div>
    <el-button
        type="primary"
        @click="handleAdd"
    >
      新增
    </el-button>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="userName" label="userName" min-width="100px" align="center"/>
      <el-table-column prop="userTelephone" label="userTelephone" min-width="100px" align="center"/>
      <el-table-column prop="userSysEmail" label="userSysEmail" min-width="100px" align="center"/>
      <el-table-column prop="userNickName" label="userNickName" min-width="100px" align="center"/>
      <el-table-column prop="userGender" label="userGender" min-width="100px" align="center"/>
      <el-table-column prop="userBornDay" label="userBornDay" min-width="100px" align="center"/>
      <el-table-column prop="userBornDay" label="userBornDay" min-width="100px" align="center"/>
      <el-table-column prop="userIdCard" label="userIdCard" min-width="100px" align="center"/>
      <el-table-column prop="userCompany" label="userCompany" min-width="100px" align="center"/>
      <el-table-column prop="userHome" label="userHome" min-width="100px" align="center"/>
      <el-table-column prop="userIp" label="userIp" min-width="100px" align="center"/>
      <el-table-column prop="userFlag" label="userFlag" min-width="100px" align="center"/>
      <el-table-column prop="userPersonalProfile" label="userPersonalProfile" min-width="100px" align="center"/>
      <el-table-column prop="userCreateTime" label="userCreateTime" min-width="100px" align="center"/>
      <el-table-column prop="roleName" label="roleName" min-width="100px" align="center"/>
      <el-table-column label="操作" min-width="200px" align="center">
        <template v-slot="scope">
          <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-button type="primary" @click="out1">导出产品报表</el-button>
    <el-button type="primary" @click="out2">导出物流报表</el-button>
    <el-button type="primary" @click="out3">导出超市报表</el-button>
    <el-button type="primary" @click="out4">导出污染报表</el-button>
  </div>
  <el-dialog v-model="dialogFormVisible" @close="rest">
    <h2>{{ title }}用户</h2>
    <el-form :model="form" label-width="100px">
      <el-form-item label="userName">
        <el-input v-model="form.userName"/>
      </el-form-item>
      <el-form-item label="phone">
        <el-input v-model="form.userTelephone"/>
      </el-form-item>
      <el-form-item label="email">
        <el-input v-model="form.userSysEmail"/>
      </el-form-item>
      <el-form-item label="nickName">
        <el-input v-model="form.userNickName"/>
      </el-form-item>
      <el-form-item label="gender">
        <el-select v-model="form.userGender" placeholder="性别">
          <el-option label="男" value="男"/>
          <el-option label="女" value="女"/>
        </el-select>
      </el-form-item>
      <el-form-item label="born">
        <el-date-picker
            v-model="form.userBornDay"
            type="date"
            placeholder="Pick a day"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item label="idCard">
        <el-input v-model="form.userIdCard"/>
      </el-form-item>
      <el-form-item label="home">
        <el-input v-model="form.userHome"/>
      </el-form-item>
      <el-form-item label="company">
        <el-input v-model="form.userCompany"/>
      </el-form-item>
      <el-form-item label="personalProfil">
        <el-input v-model="form.userPersonalProfile"/>
      </el-form-item>
      <el-form-item label="ip">
        <el-input v-model="form.userIp"/>
      </el-form-item>
      <el-form-item label="createTime">
        <el-date-picker
            v-model="form.userCreateTime"
            type="date"
            placeholder="Pick a day"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.userPassword"/>
      </el-form-item>
      <el-form-item label="角色">
        <el-select v-model="form.roleName" placeholder="请选择角色">
          <el-option
              v-for="item in roleList"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false;rest()">取消</el-button>
        <el-button type="primary" @click="saveUserInfo">
          保存
        </el-button>
      </span>
    </template>
  </el-dialog>
  <br>
  <div>
    <el-button
        type="primary"
        @click="handleAddRole"
    >
      新增
    </el-button>
    <el-table :data="tableDataRole" style="width: 100%">
      <el-table-column prop="roleId" label="roleId" min-width="100px" align="center"/>
      <el-table-column prop="roleName" label="roleName" min-width="100px" align="center"/>
      <el-table-column prop="roleCreateTime" label="roleCreateTime" min-width="100px" align="center"/>
      <el-table-column prop="roleStatusFlag" label="roleStatusFlag" min-width="100px" align="center"/>
      <el-table-column prop="roleRemark" label="roleRemark" min-width="100px" align="center"/>
      <el-table-column label="操作" min-width="200px" align="center">
        <template v-slot="scope">
          <el-button type="primary" @click="handleEditRole(scope.row)">编辑</el-button>
          <el-button type="danger" @click="handleDeleteRole(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <el-dialog v-model="dialogFormVisibleRole" @close="restRole">
    <h2>{{ title }}角色</h2>
    <el-form :model="formRole" label-width="100px">
      <el-form-item label="roleName">
        <el-input v-model="formRole.roleName"/>
      </el-form-item>
      <el-form-item label="roleCreateTime">
        <el-date-picker
            v-model="formRole.roleCreateTime"
            type="date"
            placeholder="Pick a day"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item label="roleStatusFlag">
        <el-input v-model="formRole.roleStatusFlag"/>
      </el-form-item>
      <el-form-item label="roleRemark">
        <el-input v-model="formRole.roleRemark"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisibleRole = false;restRole()">取消</el-button>
        <el-button type="primary" @click="saveRole">
          保存
        </el-button>
      </span>
    </template>
  </el-dialog>
  <el-dialog v-model="baobiaoStatus">
    <h2>报表</h2>
<!--    文本域-->
    <el-input v-model="baobiao" type="textarea" placeholder="placeholder"></el-input>
  </el-dialog>
</template>

<style scoped lang="scss">

</style>