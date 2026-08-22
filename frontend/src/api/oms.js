/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
import http from './http'
import { mockApi } from './mock'

const realApi = {
  login: data => http.post('/auth/login', data),
  me: () => http.get('/auth/me'),
  dashboard: () => http.get('/oms/dashboard'),
  orders: () => http.get('/oms/orders'),
  shipments: () => http.get('/oms/shipments'),
  afterSales: () => http.get('/oms/after-sales'),
  channels: () => http.get('/oms/channels'),
  events: () => http.get('/oms/events')
}

export const api = import.meta.env.VITE_DEMO_MODE === 'true' ? mockApi : realApi
