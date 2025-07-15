import axios from 'axios';
import {useMemberStore} from "@/stores/memberStore.js";

const axiosApi = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
});

axiosApi.interceptors.request.use(config => {
  const memberStore = useMemberStore();
  if (memberStore.token) {
    config.headers.Authorization = `Bearer ${memberStore.token}`;
  }
  return config;
}, error => {
  return Promise.reject(error);
});

export default axiosApi;
