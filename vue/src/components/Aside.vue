<template>
  
         <el-menu :default-openeds="['1', '3']"
          style="min-height: 100%" 
          background-color="#696969"
          router>
          <div style="width:200px;height: 85px;background-color: aliceblue;">
          <img src="@/images/novelogo.jpg" style="width:65px;height:70px;float:left;margin: 6px;" />
          <p style="text-align:center;padding: 25px;">小说阅读后台</p>
          </div>
          <div v-for="item in muens" :key="item.id">
            <div v-if="item.path" >
              <el-menu-item :index="item.path">
                  <template slot="title">
                    <i class="el-icon-house"></i>
                    <span slot="title" style="color:#FFFF;">{{item.name}}</span>
                  </template>
                </el-menu-item>
            </div>
            <div v-else>
                <el-submenu :index="item.id + ''">
                  <template slot="title">
                    <i class="el-icon-menu"></i>
                    <span slot="title" style="color:#FFFF;">{{item.name}}</span>
                  </template>
                  <div v-for="chil in item.children" :key="chil.id">
                  <el-menu-item :index="chil.path">
                    <i class="el-icon-user"></i>
                    <span slot="title" style="color:#FFFF;">{{chil.name}}</span>
                  </el-menu-item>
                  </div>
                </el-submenu>
            </div>
          </div>
        </el-menu>
</template>
<script>


export default {
    name: "Aside",
    data() {
      return {
        muens:localStorage.getItem("muen")?JSON.parse(localStorage.getItem("muen")):[],
      }
    },
    created(){
        this.get()
    },
    methods:{
      exc(){
        this.$store.commit("logout")
        this.$message.success("退出成功")
      },
      get(){
        console.log(this.muens)
      }
    }

}
</script>
<style>

</style>