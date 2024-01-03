import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import type { IUserInfo } from '@/types'
import { useLocalStorage } from '@/use/useLocalStorage'

export interface IUserState {
  userInfo: IUserInfo
  token: string
}

const getDefaultUserInfo: () => IUserInfo = () => ({
  id: 0,
  username: '',
  isAdmin: false
})

export const useUserStore = defineStore('user', () => {
  const {
    value: $userInfo,
    setValue: $setUserInfoValue,
    removeItem: $removeUserInfoItem,
  } = useLocalStorage('userInfo', getDefaultUserInfo())
  const { setValue: $setTokenValue, removeItem: $removeTokenItem } = useLocalStorage('token', '')
  const state = ref({
    userInfo: getDefaultUserInfo(),
    token: '',
  })
  const getUserInfo = computed(() => {
    // 为什么不直接读 localStorage 的值呢？
    // 因为读取 localStorage 是比较耗时的操作，所以这里先读 store
    if (!state.value.userInfo || !state.value.userInfo.username) {
      state.value.userInfo = $userInfo.value
    }
    return state.value.userInfo
  })
  const setInfo = ({ token, userInfo }: IUserState) => {
    state.value.userInfo = userInfo
    state.value.token = token
    $setUserInfoValue(userInfo)
    $setTokenValue(token)
  }
  const removeInfo = () => {
    state.value.userInfo = getDefaultUserInfo()
    state.value.token = ''
    $removeUserInfoItem()
    $removeTokenItem()
  }
  return {
    state,
    getUserInfo,
    setInfo,
    removeInfo,
  }
})
