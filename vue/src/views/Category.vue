<template>
    <div class="about">
  <div>
      <el-input placeholder="请输入内容"
  suffix-icon="el-icon-date"
  v-model="nickname" style="width:150px">
</el-input>
<template>
  <el-button type="success" @click="load">查询</el-button></template>
    </div>
    <div>
      <el-button type="primary" @click="add">新增</el-button>
       <el-button type="primary" @click="bel">批量删除</el-button>
    </div>
        <el-table :data="tableData" @selection-change="handleSelectionChange">
          <el-table-column
                  type="selection"
                  width="55">
          </el-table-column>
          <el-table-column prop="id" label="id" >
          </el-table-column>
          <el-table-column prop="name" label="名称" >
          </el-table-column>
        
          <el-table-column prop="path" label="路径" >
          </el-table-column>
          <el-table-column prop="description" label="简介" >
          </el-table-column>
        
          <el-table-column prop="pagepath" label="页面路径" >
          </el-table-column>
       
          <el-table-column label="操作" >
              <template slot-scope="scope">
                <el-button type='warning' @click="update(scope.row)" style="margin-right:10px">编辑</el-button>
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
        <div>
        <el-dialog title="用户添加" :visible.sync="dialogFormVisible">
      <el-form :model="form" label-width="200px" >
  
      <el-form-item label="分类名称" >
        <el-input v-model="form.name" autocomplete="off" style="width: 300px;" ></el-input>
      </el-form-item>
      <el-form-item label="路径" >
        <el-input v-model="form.path" autocomplete="off"  style="width: 300px;"></el-input>
      </el-form-item>
       <el-form-item label="页面路径" >
        <el-input v-model="form.pagepath" autocomplete="off" style="width: 300px;" ></el-input>
      </el-form-item>
      <el-form-item label="简介" >
        <el-input v-model="form.description" autocomplete="off" style="width: 300px;" ></el-input>
      </el-form-item>
     
      </el-form>
<div slot="footer" class="dialog-footer">
  <el-button @click="dialogFormVisible = false">取 消</el-button>
  <el-button type="primary" @click="insert" >确 定</el-button>
</div>
</el-dialog>
        </div>
</div>
</template>
<script>
export default {
  name:"category",
  data() {
      return {
          tableData:[],
          pageNum:1,
          pageSize:10,
          total:0,
          bname:"",
          nickname:'',
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
        this.request.get("/category/page",{params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize
        }}).then(res=>{
          console.log(res.data)
          this.tableData=res.data.records
          this.total=res.data.total
        })
      },
      handleSizeChange(pageSize){
        this.pageSize=pageSize
      },
      add(){
        this.form={}
        this.dialogFormVisible=true
      },
      bel(){

      },
      update(row){
        this.form=row
        this.dialogFormVisible=true
      },
      handleCurrentChange(pageNum){
        this.pageNum=pageNum
      },
      insert(){
        this.request.post("/category/insert",this.form).then(res =>{
        if(res.code=="200"){
          this.$message.success("操作成功")
          this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.error("操作失败")
        }
      })
      },
      deletec(id){
        this.request.delete("/category/"+id).then(res=>{
            if(res.code=="200"){
            this.$message.success("删除成功")
            this.load()
          }else{
            this.$message.error("删除失败")
          }
          })
      },
      handleSelectionChange(val){
        this.mut=val
      }
  },
}
</script>