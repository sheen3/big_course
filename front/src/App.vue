<template>

  <router-view />
</template>
<script setup lang="ts">
import {useRouter} from "vue-router";

const router = useRouter()
</script>
<style>
body{
  margin: 0;
  padding: 0;
}
</style>
