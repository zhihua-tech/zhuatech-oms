/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
import { defineStore } from 'pinia'
import { api } from '../api/oms'

export const useAuthStore = defineStore('auth', {
  state: () => ({ user: JSON.parse(localStorage.getItem('zhuatech_oms_user') || 'null') }),
  actions: {
    async login(form) {
      const result = await api.login(form)
      localStorage.setItem('zhuatech_oms_token', result.token)
      localStorage.setItem('zhuatech_oms_user', JSON.stringify(result.user))
      this.user = result.user
    },
    logout() {
      localStorage.removeItem('zhuatech_oms_token')
      localStorage.removeItem('zhuatech_oms_user')
      this.user = null
    }
  }
})
