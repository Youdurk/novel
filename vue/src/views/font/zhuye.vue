<template>
    <div style="">
       <el-container style="min-height: 100vh;" >
        <div style="text-align: center;height: 100px;">
       <img src="@/images/novelogo.jpg" style="float:left;width: 200px;height: 100%;"/>
        <el-input placeholder="请输入书名"
    suffix-icon="el-icon-date"
    v-model="bname"
     style="width:350px;margin: 20px;" ></el-input>
    <el-button  type="primary" @click="searc">搜索</el-button>
      <span style="float:right;margin: 25px;" @click="panduan">{{exc}}</span>
        </div>
         <el-header style="height:100%;border-bottom: 1px solid #ccc;">
            <classify />
         </el-header>
         <el-main style="background-color:#87CEFA">
                <router-view  />
         </el-main>
        <el-footer>
            <foonter />
        </el-footer>
       </el-container>
    </div>
   
</template>
<script>
import classify from '@/components/classify.vue'
import foonter from '@/components/Foonter.vue'
export default {
    name:'Zhuye',
    components:{
       classify,
       foonter,
    },
    data() {
        return {
            src:"@/images/novelogo.jpg",
            user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):[],
            exc:"登录",
            bname:"",
        }
    },
    created(){
        if(this.user.nickname !=null){
            this.exc=this.user.nickname
        }
        
    },
    methods:{
        searc(){
            if(this.bname!=null && this.bname!="" ){
                this.$router.push("/search?bname="+this.bname)
                this.$router.go(0)
                this.bname=''
            }else{
                this.$message.error("请输入书名")
            }
            
         
        },
        panduan(){
            if(this.user.token !=null){
                if(this.user.role=='管理员'){
                    this.$router.push("/user")
                }else{
                    this.$router.push("/bookshelf")
                }
            }else{ 
                this.$router.push("/login")
            }
        }
    }

}
</script>