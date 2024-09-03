import axios from "axios";
import { useBaseStore } from "@/store/index.js";

// Tạo một instance của axios với các default config
const instance = axios.create({
  baseURL: "http://localhost:8080/api",

  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
});

//Tạo một interceptor để thêm token vào header của request
instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error)
)

// Tạo một interceptor để xử lý các lỗi trả về từ server
instance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalConfig = error.config;
    if (originalConfig.url !== "/auth/token" && error.response) {
      // Access Token was expired
      if (error.response.status === 401 && !originalConfig._retry) {
        originalConfig._retry = true;

        try {
          const token = localStorage.getItem("token");
          localStorage.removeItem("token");
          const rs = await base.post("/auth/refresh", token);

          const accessToken = rs.result.token;

          const store = useBaseStore();

          store.refreshToken(accessToken);

          return instance(originalConfig);
        } catch (_error) {
          return Promise.reject(_error);
        }
      }
    }
    return Promise.reject(error)
  }
)

export const base = {
  async get(url, params = {}) {
    try {
      const response = await instance.get(url, { params });
      return response.data;
    } catch (error) {
      return Promise.reject(error);
    }
  },

  async post(url, data) {
    try {
      const response = await instance.post(url, data);
      return response.data;
    } catch (error) {
      return Promise.reject(error);
    }
  },

  async postFile(url, data) {
    try {
      console.log(data);
      const response = await instance.post(url, data,{
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      return response.data;
    } catch (error) {
      return Promise.reject(error);
    }
  },

  async put(url, data) {
    try {
      const response = await instance.put(url, data);
      return response.data;
    } catch (error) {
      return Promise.reject(error);
    }
  },

  async delete(url, params = {}) {
    try {
      const response = await instance.delete(url, { params });
      return response.data;
    } catch (error) {
      return Promise.reject(error);
    }
  },
};
