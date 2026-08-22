/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
import axios from 'axios'
import { showFailToast } from 'vant'

const http = axios.create({ baseURL: import.meta.env.VITE_API_BASE || '/api', timeout: 10000 })
http.interceptors.request.use(config => {
  const token = localStorage.getItem('zhuatech_oms_token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})
http.interceptors.response.use(response => response.data.data, error => {
  showFailToast(error.response?.data?.message || '网络连接异常')
  if (error.response?.status === 401) {
    localStorage.removeItem('zhuatech_oms_token')
    location.href = '/login'
  }
  return Promise.reject(error)
})

export default http

