<template>
    <div>
        <el-card style="width: 60%;">
        <el-steps :active="active" finish-status="success">
        <el-step title="步骤 1">
        </el-step>
        <el-step title="步骤 2"></el-step>
        <el-step title="步骤 3"></el-step>
        </el-steps>
        <template>
           
        <div v-if="active==1">
        <el-form :model="user"  label-width="100px">
            <el-form-item label="原密码" prop="password">
                <el-input v-model="user.password"  placeholder="请输入原密码" style="width: 300px;"></el-input>
            </el-form-item>
            </el-form> 
            <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
            </div>
            <div v-if="active==2">
                <el-form :model="usdto" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="新密码" prop="newpassword">
                    <el-input v-model="usdto.newpassword" placeholder="请输入新密码" style="width: 300px;"></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="neassword">
                    <el-input v-model="usdto.neassword" placeholder="再一次输入新密码" style="width: 300px;"></el-input>
                </el-form-item>
            </el-form> 
                 <el-button style="margin-top: 12px;" @click="prev">返回</el-button>
        <el-button style="margin-top: 12px;" @click="nexttwo">下一步</el-button>
            </div>
            <div v-if="active==3">
              <div>修改成功</div>
        <el-button style="margin-top: 12px;" @click="nextfree">回到登录</el-button>
            </div>
            
        </template>
        </el-card>
   
    </div>

</template>
<script>


export default {
    name:'modify',
    data(){
        return{
            active:1,
            user:{},
            usdto:{},
            rules:{
                newpassword: [
            { required: true, message: '请输入活动名称', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
          ],
          neassword: [
            { required: true, message: '请输入活动名称', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
            },
        }
    },
    created(){

    },
    methods:{
        next() {
            if(this.user.password){
                this.user.username=localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")).username:''
            this.request.get("/user/users",{params:{
                password:this.user.password,
                username:this.user.username
            }}).then(res=>{
                if(res.data){ 
                    if (this.active++ > 2) this.active = 1;
                }else{
                    this.$message.error("密码错误")
                }
        })
 
            }else{
                this.$message.error("输入密码")
            }
      },
      nexttwo(){
            if(this.usdto.newpassword==this.usdto.neassword){
                this.usdto.password=this.user.password
                this.usdto.username=localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")).username:''
              
                this.request.post("/user/upassword",this.usdto).then(res=>{
                    if(res.code==200){
                        if (this.active++ > 2) this.active = 1;
                    }else{
                        this.$message.error("密码修改错误")
                    }
                })
            }else{
                this.$message.error("两次输入密码不同")
            }
      },
      prev(){
        if (this.active > 1) this.active--;
      },
      nextfree(){
        this.$store.commit("logout")
      this.$message.success("退出成功")
      }
    }
}
</script>