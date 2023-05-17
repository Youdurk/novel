<template>
  
      <div class="about">
       
    <div>
        <el-input placeholder="请输入内容"
    suffix-icon="el-icon-date"
    v-model="scopes" style="width:150px">
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
            <el-table-column prop="imges" label="图片" >
              <template slot-scope="scope">
                <el-image
              style="width: 100px; height: 100px"
              alt=""
              :src="scope.row.imges"
      ></el-image>
                 </template>
            </el-table-column>
            <el-table-column prop="scope" label="顺序" >
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
                        <el-button type="danger" slot="reference">删除<i class="el-icon-delete"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
          </el-table>
           <div>
              <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="[6, 12, 18, 25]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
            </el-pagination>
            </div>
          <div>
          <el-dialog title="用户添加" :visible.sync="dialogFormVisible">
        <el-form :model="form" label-width="200px" >
          <el-form-item  label="图片">
                <el-upload
                    class="avatar-uploader"
                    :action="'http://localhost:9090/file/upload'"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    >
                    <img v-if="form.imges" :src="form.imges" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        </el-form-item>
         <el-form-item label="顺序" >
          <el-input v-model="form.scope" autocomplete="off"  ></el-input>
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
    name:"carousel",
    data() {
        return {
            tableData:[],
            pageNum:1,
            pageSize:10,
            total:0,
            scopes:"",
            form:{},
            dialogFormVisible:false,
            mut:[],
        }
    },
    created(){
        this.load()
    },
    methods:{
      load(){
        this.request.get("/carousel/page",{params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          scopes:this.scopes
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
    handleAvatarSuccess(res){
      this.form.imges=res
    },
    insert(){
      this.request.post("/carousel/insert",this.form).then(res=>{
        if(res.code=="200"){
            this.dialogFormVisible=false
            this.$message.success("操作成功")
            this.load()
          }else{
            this.$message.error("操作失败")
          }
        })
    },
    update(row){
      this.dialogFormVisible=true
      this.form=row
    },
    deletec(id){
        this.request.delete("/carousel/"+id).then(res =>{
          if(res.code=="200"){
            this.$message.success("删除成功")
            this.load()
          }else{
            this.$message.error("删除失败")
          }
        })
    },
    handleSelectionChange(al){
      console.log(al)
      this.mut=al
    },
    bel(){
        let ids=this.mut.map(m=>m.id)
        this.request.post("/carousel/del/ids",ids).then(res =>{
          if(res.data){
            this.$message.success("删除成功")
            this.load()
          }else{
            this.$message.error("删除失败")
          }
        })
    },
    add(){
      this.dialogFormVisible=true
      this.form={}
    }
    },
}
</script>