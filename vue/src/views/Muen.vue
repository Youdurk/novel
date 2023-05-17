<template>
 <div class="about">
      <div>
        <el-button type="primary" @click="add">新增</el-button>
         <el-button type="primary" @click="bel">批量删除</el-button>
      </div>
          <el-table :data="tableData"  
          :header-cell-class-name="headerBg"
          row-key="id"
          default-expand-all
          :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
          @selection-change="handleSelectionChange">
            <el-table-column
                    type="selection"
                    width="55">
            </el-table-column>
            <el-table-column prop="id" label="id" >
            </el-table-column>
             <el-table-column prop="name" label="名称" width="140">
            </el-table-column>
            <el-table-column prop="path" label="路径" width="140">
            </el-table-column>
            <el-table-column prop="icon" label="图标" width="140">
            </el-table-column>
            <el-table-column prop="description" label="简介" width="120">
            </el-table-column>
            <el-table-column prop="pagepath" label="页面路径">
            </el-table-column>
            <el-table-column label="操作" width="300" >
                <template slot-scope="scope">
                  
                  <el-button type='success' @click="update(scope.row)" style="margin-right:10px">编辑</el-button>
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
                   <el-button type='warning' @click="adda(scope.row.id)" style="margin-right:10px;margin: 5px;" v-if="scope.row.pid==null">新增子权限</el-button> 
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
        <el-dialog title="小说添加" :visible.sync="dialogFormVisible">
        <el-form :model="form" label-width="200px" >
         <el-form-item label="书名" >
          <el-input v-model="form.name" autocomplete="off" style="width:200px"></el-input>
        </el-form-item>
         <el-form-item label="路径" >
          <el-input v-model="form.path" autocomplete="off" style="width:200px"></el-input>
        </el-form-item>
           <el-form-item label="图标" >
           <el-input v-model="form.icon" autocomplete="off" style="width:200px"></el-input>
        </el-form-item>
        <el-form-item label="简介" >
          <el-input v-model="form.description" autocomplete="off" style="width:200px"></el-input>
        </el-form-item>
        <el-form-item label="页面路径" >
          <el-input v-model="form.pagepath" autocomplete="off" style="width:200px"></el-input>
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
 name: "muen",
 data() {
  return {
    tableData:[],
    form:{},
    pageNum:1,
    pageSize:5,
    total:0,
    mul:[],
    dialogFormVisible:false,
    headerBg:'headerBg',
  }
 }, 
 created(){
  this.load()
 },
 methods: {
  load(){
    this.request.get("/muen",{params:{
      pageNum:this.pageNum,
      pageSize:this.pageSize
    }}).then(res=>{
      console.log(res.data)
      this.tableData=res.data
      this.total=res.data.total
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
  deletec(id){
    this.request.delete("/muen/d/"+id).then(res=>{
      if(res.code=='200'){
        this.$message.success("成功")
        this.load()
      }
    })
  },
  add(){
    this.dialogFormVisible=true
    this.form={}
  },
  bel(){
    let ids=this.mul.map(m=>m.id)
    this.request.post("/muen/del/bath",ids).then(res=>{
      if(res.code=='200'){
        this.$message.success("成功")
        this.load()
      }else{
        this.$message.error("请重试")
      }
    })
  },
  update(val){
    this.dialogFormVisible=true
    this.form=val
  },
  handleSelectionChange(val){
    this.mul=val
  },
  adda(id){
    this.dialogFormVisible=true
    this.form={}
    if(id){
      this.form.pid=id
      this.load()
    }
  },
  insert(){
    this.request.post("/muen/ins",this.form).then(res =>{
      if(res.code=='200'){
        this.$message.success("成功")
        this.dialogFormVisible=false
        this.load()
      }else{
        this.$message.error("请重试")
      }
    })
  },
  },
}
</script>

<style>

</style>