// import axios from "axios";
import store from "../store";
import router from "../router";
// 创建axios实例
const service = axios.create({
  baseURL: process.env.VUE_APP_URL, // api 的 VUE_APP_URL
  timeout: 50000 // 请求超时时间(因为需要调试后台,所以设置得比较大)
});

// request拦截器,在请求之前做一些处理
service.interceptors.request.use(
  config => {
    if (store.state.token) {
      // 给请求头添加laohu-token
      config.headers["Authorization"] = store.state.token;
    }
    return config;
  },
  error => {
    console.log(error); // for debug
    Promise.reject(error);
  }
);

// response 拦截器,数据返回后进行一些处理
service.interceptors.response.use(
  response => {
    /**
     * code为非20000是抛错 可结合自己业务进行修改
     */
    const res = response.data;
    return res;
  },
  error => {
    Promise.reject("网络异常");
  }
);
export default service;
