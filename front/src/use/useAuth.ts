import { computed } from 'vue'
import { useUserStore } from '@/stores/user'
import { auth } from '@/api/user'
import type { ILoginInfo } from '@/types'

export function useAuth() {
  const store = useUserStore()
  const user = computed(() => store.getUserInfo)
  const login = async (data: ILoginInfo) => {
    const res = await auth(data)
    if (res){
      const {token,userInfo} = res
      store.setInfo(
          {
            token,
            userInfo
          })
    }
  }

  const logout = () => {
    store.removeInfo()
  }
  return {
    user,
    login,
    logout,
  }
}
