<template>
  <div>
    产品 {{ route.params.productId }}
    {{showRes}}
  </div>
</template>

<script setup lang="ts">
import {useRoute, useRouter} from "vue-router";
import axios from "axios";
import {ref} from "vue";
interface Ajax {
  code: number
  msg: string
  data: any
}
const route = useRoute()
// 请求
// http://localhost:8080/#/product/11
// https://kokodayou.top/back/api/v1/course/getCourseList/计科2013
const instance = axios.create({
  baseURL: 'https://kokodayou.top/back/api/v1/course/getCourseList',
})
// http://localhost:8080/#/product/计科2013
const showRes = ref({} as any)
instance.get<Ajax,Ajax>('/'+route.params.productId).then((res)=>{
  showRes.value = res
})
</script>