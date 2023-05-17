<template>
    <!-- 我的评论 -->
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
    name:'mycomment',
    data() {
      return {
          tableData:[],
          pageNum:1,
          pageSize:10,
          total:0,
          bname:"",
          form:{},
          mut:{},
          dialogFormVisible:false,
      }
  },
  created(){
        this.load()
  },
  methods:{
      load(){
        let username=localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")).username:''
          this.request.get("/comment/pages",{params:{
            PageNum:this.pageNum,
            PageSize:this.pageSize,
            username:username
          }}).then(res=>{
              console.log(res.data)
              this.tableData=res.data.records
              this.total=res.data.total
          })
     
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
    handleSelectionChange(val){
        this.mut=val

    },
    add(){},
    bel(){
        const ids=this.mut.map(v=>v.id)
        console.log(ids)
        this.request.post("/comment/del/bath",ids).then(res=>{
            if(res.code=="200"){
            this.$message.success("删除成功")
            this.load()
          }else{
            this.$message.error("删除失败")
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
