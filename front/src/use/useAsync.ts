import type { UnwrapRef } from 'vue'
import { ref } from 'vue'

export function useAsync<T>(asyncFn: () => Promise<T>, initValue: T, immediate = true) {
  const pending = ref(false)
  const data = ref(initValue)
  const error = ref(null)
  const execute = function () {
    // 设置请求状态为请求中
    pending.value = true
    error.value = null
    return asyncFn()
      .then((res) => {
        // data.value 能接受的值是 UnwrapRef
        data.value = res as UnwrapRef<T>
      })
      .catch((err) => {
        error.value = err
      })
      .finally(() => {
        pending.value = false
      })
  }
  if (immediate) {
    execute()
  }
  return {
    pending,
    data,
    error,
    execute,
  }
}
