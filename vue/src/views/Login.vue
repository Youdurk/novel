<template>
  <div class="webss">
      <div class="webs1">
            <div style="font-size: 20px;text-align: center;margin: 20px">树懒小说</div>
            <el-form :model="user" :rules="rules" ref="userForm"
                     label-width="100px" style="margin: 20px"
                     :hide-required-asterisk=true
            >
                <el-form-item label="用户名" prop="username" style="color: #FFFF;">
                    <el-input v-model="user.username" ></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input show-password v-model="user.password"></el-input>
                </el-form-item>
            </el-form>
            <div style="margin: 50px 0;text-align: center">
                <el-button  round type="warning" autocomplete="off" @click="los">登录</el-button>
                <el-button round type="primary" autocomplete="off" @click="dialogFormVisible = true">注册</el-button>
            </div>
            <div>
              <el-dialog title="用户注册" :visible.sync="dialogFormVisible">
            <el-form label-width="120px"
                     :model="user" :rules="rules" ref="userForm"
                     :hide-required-asterisk=true>
                <el-form-item label="昵称" prop="nickname">
                    <el-input v-model="user.nickname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="user.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="user.password" show-password autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="conpassword">
                    <el-input v-model="user.conpassword" show-password autocomplete="off"></el-input>
                </el-form-item>
                 <el-form-item label="角色" prop="role">
                  <el-select v-model="user.role" placeholder="请选择角色">
                    <el-option label="读者" value="读者"></el-option>
                    <el-option label="作者" value="作者"></el-option>
                  </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="Register" >确 定</el-button>
            </div>
        </el-dialog>
            </div>
        </div>

  </div>
</template>
<script>
import { setrouter } from '@/router';
import request from '../utils/request'
export default {
  name:'Login',
  data(){
    return{
      user:{},
      dialogFormVisible:false,
       rules: {
         nickname:[
                {required: true, message: '请输入昵称', trigger: 'blur'},
                {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
            ],
            username: [
                {required: true, message: '请输入用户名', trigger: 'blur'},
                {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
            ],
            password: [
                {required: true, message: '请输入密码', trigger: 'blur'},
                {min: 1, max: 16, message: '长度在 1 到 16 个字符', trigger: 'blur'}
            ],
             conpassword: [
                {required: true, message: '请再一次输入密码', trigger: 'blur'},
                {min: 1, max: 16, message: '长度在 1 到 16 个字符', trigger: 'blur'}
            ],
            role: [
                {required: true, message: '请选择角色', trigger: 'blur'},
            ],
        }
    }
  },
  methods:{
    los(){
      this.$refs["userForm"].validate((valid)=>{
        if(valid){
      request.post("/user/login",this.user).then(res=>{
          if(res.data){
            localStorage.setItem("user",JSON.stringify(res.data))//保存用户信息
            localStorage.setItem("muen",JSON.stringify(res.data.muen))//保存用户信息
            //动态设置当前用户的路由
              if(res.data.role=="管理员"){
              setrouter()
              this.$router.push("/home")
              this.$message.success("登录成功")
              console.log(res.msg);
            }else{
              setrouter()
              this.$router.push("/bookshelf")
              this.$message.success("登录成功")
              console.log(res.msg);
            }
              
          }else if(res.code=="600"){
            this.$message.error("该账号已被冻结")
          }else{
            this.$message.error("用户名或者密码错误")
          }
         }) 
      }else{
          console.log('error submit!!');
          return false;
      }
        });
    },
    Register(){
        if(this.user.password == this.user.conpassword){
        request.post("/user/register",this.user).then(res =>{
          if(res.data){
             console.log(res.msg)
            this.$message.success("注册成功")
             this.dialogFormVisible = false
          }else{
             this.$message.error("注册失败")
          }
        })
        }else{
              this.$message.success("两次密码不同请从新输入")
                return false;
        }
       
     
    }
    }
  
}
</script>
<style>
.webss{
  width: 100%;
    height: 100vh;
    background-image:url("@/images/beijing.jpeg");
    overflow:hidden;
    background-size: 100% 100%;
}
.webs1{
    margin: 200px auto;
    background-color: rgba(255, 255, 255, 0.1);
    width: 350px;
    height: 300px;
    padding: 20px;
    border-radius: 10px;
}
</style>
