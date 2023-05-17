<template>
  <div>
    <el-container  style="min-height: 100vh">
      <el-aside width="200px" style="">
       <Aside />
      </el-aside>

      <el-container>
        
        <el-header style="text-align: right; font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px">
        <Header />
        </el-header>
 
        <el-main>
              <router-view />
        </el-main>
         <el-footer  style="background-color: rgb(238, 241, 246);text-align: center;">
          <Foonter />
      </el-footer>
      </el-container>
     
    </el-container>
    
  </div>
  
</template>

<script>
import Aside from '@/components/Aside';
import Header from '@/components/Header';
import Foonter from '@/components/Foonter'
export default {
    name: "Manage",
    data() {
      return {
        
      }
    },
    methods: {
     
    },
    components: { 
      Aside,
      Header,
      Foonter
     }
}
</script>
<style >
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }
  
  .el-aside {
    color: #333;
  }
</style>
