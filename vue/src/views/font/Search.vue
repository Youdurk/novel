<template>
    <div>
        <el-card :body-style="{margin: '10px'}">
           <h5 style="text-align: center;">搜索结果：仅显示前30条，请输入更详细的搜索条件，缩写搜索范围</h5>
           <el-row>
            <div v-if="this.bname !='' && this.bname !=null">
                <el-col :span="50" v-for="o in novel" :key="o.id" >
    <div  style="width:1500px;height: 200px;cursor: pointer;">
      <img :src="o.bookUrl" class="image" style="height: 100%;width: 20%;" />
      <div style="font-size: 25px;width: 80%;float: right;">
      <dl >
        <dt>
            <a >{{o.bname}}</a>
            <span style="font-size:25px;margin-left: 200px;">{{o.author}}</span>  
      </dt>
      <dd><a style="font-size:15px">{{o.brief}}</a></dd>
      </dl>
      </div>
    </div>
    
  </el-col>
            </div>
            <div v-else>
                <h3 id="ds" style="text-align: center;">没有查询到该相关书籍</h3>
            </div>
           </el-row>
        </el-card>
    </div>
</template>
<script>
export default {
    name:'search',
    data() {
        return {
            bname:this.$route.query.bname,
            novel:[],
            pageNum:1,
            pageSize:30
        }
    },
    created(){
       this.load()
    },
    methods:{
        load(){        
             this.request.get("/novel/page",{params:{
             pageNum:this.pageNum,
            pageSize:this.pageSize,
            bname:this.bname
          }}).then(res =>{
           if(res.data.records){
             this.novel=res.data.records
           }else{
            document.getElementById("ds")
           }
               
          }) 
 }, 
    }
}
</script>