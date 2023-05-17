<template>
   <div>

          <el-dropdown>
            <div>
              <img :src="user.avatarurl" alt=""
             style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
              <span>{{user.nickname}}</span>
              <i class="el-icon-setting" style="margin-right: 15px"></i>
            </div>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item >个人信息</el-dropdown-item>
              <el-dropdown-item  >
               <span @click="rou" >进入前台</span> 
              </el-dropdown-item>
              <el-dropdown-item >
                  <span @click="exs">退出</span>
                </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          
     
   </div>
</template>
<script>
export default {
  name:'Herder',
data() {
  return {
    user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
  }
},
  methods:{
    exs(){
      this.$store.commit("logout")
      this.$message.success("退出成功")
    },
    rou(){
      this.$nextTick(()=>{
          this.request.get("/category").then(res=>{
          this.category=res.data
        localStorage.setItem("category",JSON.stringify(res.data))
        })})
      this.$router.push("/")
    }
  }
}
</script>
<style>

</style>