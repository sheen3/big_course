import { useLocalStorage } from '@/use/useLocalStorage'
import axios from 'axios'
import {ElDialog, useDialog} from "element-plus";

const instance = axios.create({
  baseURL: 'http://localhost:11100/api',
})

instance.interceptors.request.use((config) => {
  const { value: token } = useLocalStorage('token', '')
  if (config.headers && token.value) {
    config.headers['token'] = token.value
  }
  return config
})

instance.interceptors.response.use(
  (response) => {
    const { data: _data } = response
    const { data, code, msg } = _data
    if (code !== 200) {
        alert("error: "+msg)
        return
    }
    return data
  },
  (err) => {
    if (err.response && err.response.status === 401) {
    }
  },
)

export default instance
