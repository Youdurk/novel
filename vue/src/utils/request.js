import axios from "axios";
import ElementUI from "element-ui";

const request=axios.create({
    baseURL:"http://localhost:9090/",
    timeout:5000
})
// 添加请求拦截器
// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config=> {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    
    let user=localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):null
    if(user){
        config.headers["token"]=user.token;
    }
    // config.headers['token'] = user.token;  // 设置请求头
    return config
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });

// 添加响应拦截器
// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
  response => {
    let res = response.data;
    // 如果是返回的文件
    if(res.code=='401'){
      localStorage.removeItem("user")
      localStorage.removeItem("muen")
      router.push("/login")
    }
    if (response.config.responseType === 'blob') {
        return res
    }
    // 兼容服务端返回的字符串数据
    if (typeof res === 'string') {
        res = res ? JSON.parse(res) : res
    }
    //当验证不通过时给出提示
    if (res.code==='401'){
        ElementUI.Message({
            message:res.msg,
            type:'error'
        })
    }
    return res;
},
error => {
    console.log('err' + error) // for debug
    return Promise.reject(error)
}
  );

  export default request