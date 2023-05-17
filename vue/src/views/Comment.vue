<template>
    <div class="about">
  <div>
      <el-input placeholder="请输入内容"
  suffix-icon="el-icon-date"
  v-model="bname" style="width:150px">
</el-input>
<template>
  <el-button type="success" @click="load">查询</el-button></template>
    </div>
    <div>

       <el-button type="primary" @click="bel">批量删除</el-button>
    </div>
        <el-table :data="tableData" @selection-change="handleSelectionChange">
          <el-table-column
                  type="selection"
                  width="55">
          </el-table-column>
          <el-table-column prop="id" label="id" width="50">
          </el-table-column>
          <el-table-column prop="content" label="评论"></el-table-column>
          <el-table-column prop="bname" label="书名" >
          </el-table-column>
          <el-table-column prop="nickname" label="评论人" >
          </el-table-column>
          <el-table-column label="操作" width="200" >
              <template slot-scope="scope">
               <el-popconfirm
                          class="ml-5"
                          confirm-button-text='确定'
                          cancel-button-text='我再想想'
                          icon="el-icon-info"
                          icon-color="red"
                          title="您确定删除吗？"
                          @confirm="deletec(scope.row.id)"
                  >
                      <el-button type="danger" slot="reference" >删除<i class="el-icon-delete"></i></el-button>
                  </el-popconfirm>
              </template>
          </el-table-column>
        </el-table>
         <div>
            <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[10, 15, 20, 25]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
          </div>
       
</div>
</template>
<script>
export default {
  name:"comment",
  data() {
      return {
          tableData:[],
          pageNum:1,
          pageSize:10,
          total:0,
          bname:"",
          form:{},
          mu:[],
          dialogFormVisible:false,
      }
  },
  created(){
        this.load()
  },
  methods:{
      load(){
          this.request.get("/comment/page",{params:{
            PageNum:this.pageNum,
            PageSize:this.pageSize,
            bname:this.bname
          }}).then(res=>{
              console.log(res)
              this.tableData=res.data
              this.total=res.total
          })
      },
      handleSelectionChange(val){
          this.mu=val
          console.log(this.mu)
      },
      handleSizeChange(pageSize){
                console.log(pageSize)
                this.pageSize=pageSize
                this.load()
   },
    handleCurrentChange(pageNum){
        console.log(pageNum)
        this.pageNum=pageNum
        this.load()
    },
      bel(){
        let ids=this.mu.map(v=>v.id)
          this.request.post("/comment/del/bath",ids).then(res =>{
        if (res.data) {
                    this.$message.success("批量删除成功")
                    this.load()
                } else {
                    this.$message.error("批量删除失败")
                } 
      })
      },
      deletec(id){
          this.request.delete("/comment/"+id).then(res=>{
            if(res.code=='200'){
                this.$message.success("删除成功")
                this.load()
            }else{
                this.$message.error("删除成功")
            }
          })
      }

  },
}
</script>