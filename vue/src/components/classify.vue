<template>
   <el-menu
  class="el-menu-demo"
  mode="horizontal"
  background-color="#545c64"
  text-color="#fff"
  active-text-color="#ffd04b"
  router  style="text-align: center;width: 100%;" >
  <div v-for="item in category" :key="item.id" style="width: 75px;float: left;">
    <el-menu-item :index="item.path">{{item.name}}</el-menu-item>
  </div>


</el-menu>
</template>
<script>
  export default{
    name: 'classify',
    data() {
      return {
        category:[]
      }
    },
    created(){
   
        this.load()
    },
    methods:{
      load(){
          this.request.get("/category").then(res=>{
          this.category=res.data
            console.log(this.category)
        })
 
          
      }
    }
}
</script>