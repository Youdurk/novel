<template>
<div>
    <div style="display:flex;justify-content: center;" >
       <el-carousel indicator-position="outside" :interval="4000" type="card" height="350px" style="width:80%;">
    <el-carousel-item v-for="item in carousel" :key="item.id">
      <img :src="item.imges"   style="width: 100%;height: 100%;"/>
    </el-carousel-item>
  </el-carousel>
    </div>
   <div style="box-shadow: 0 4px 5px rgba(0, 0, 0, .12), 0 0 7px rgba(0, 0, 0, .04);margin: 10px;">
    <el-row :gutter="10">
  <el-col :span="8" v-for="item in Novel" :key="item.id" >
    <div style="width: 350px;height: 156px;cursor: pointer;">
      <b @click="$router.push('/briefbook?id='+item.id+'&heat='+(item.heat+1))"><img :src="item.bookUrl" class="image"  /></b>
      <div style="font-size: 18px;">
      <dl >
        <dt>
            <a @click="$router.push('/briefbook?id='+item.id+'&heat='+(item.heat+1))" >{{item.bname}}</a>&nbsp;&nbsp;&nbsp;
            <span style="font-size:10px">{{item.author}}</span>  
      </dt>
      <dd><a style="font-size:8px" @click="$router.push('/briefbook?id='+item.id+'&heat='+(item.heat+1))">{{item.brief | cdf}}</a></dd>
      </dl>
      </div>
    </div>
  </el-col>
</el-row>
</div>
<div style="height:35px"></div>
<div style="border: 3px solid #ccc;">
    <div style="height: 34px;background-color: aqua;">小说列表 <i class="el-icon-d-arrow-right"></i></div>
    <el-table :data="tableData" @row-click="oppen">
        <el-table-column prop="btype" label="类型"  >
        </el-table-column>
        <el-table-column prop="bname" label="书名"  >
        </el-table-column>
        <el-table-column prop="author" label="作者">
        </el-table-column>
      </el-table>
      <div class="block">
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-size="pageSize"
      layout="prev, pager, next, jumper"
      :total="total"
      >
    </el-pagination>
  </div>
</div>
  </div>
    
</template>
<script>

 export default{
    name:"Homepage",
    data() {
      return {
        Novel:{},
        tableData:[],
        carousel:{},
        pageNum:1,
        pageSize:10,
        total:0
      }
    },
    filters: {
      cdf(value){
          if(!value){return ""};
          if(value.length >15){
            return value.slice(0,45)+'....';
          } 
      }
    },

    created(){
      this.load()
    },
    methods:{
      load(){
        this.request.get("/novel/popular").then(res =>{
          console.log(res.data)
          this.Novel=res.data
        })
        this.request.get("/novel",{params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize
        }}).then(res =>{
          console.log(res.data)
          this.tableData=res.data.records
          this.total=res.data.total            
        })
        this.request.get("/carousel/list").then(res=>{
          console.log(res.data)
          this.carousel=res.data
        })
      },
      handleSizeChange(pageSize){
          this.pageSize=pageSize
          this.load()
      },
      handleCurrentChange(pageNum){
        this.pageNum=pageNum
        this.load()
      },
      oppen(val){
        this.$router.push({path:'/briefbook?id='+val.id+'&heat='+(val.heat+1)})
      }
    
    }

}
</script>

<style>
   .time {
    font-size: 13px;
    color: #999;
  }
  
  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .image {
    float: left;
    width: 175px;
    height: 200px;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  
  .clearfix:after {
      clear: both
  }
</style>