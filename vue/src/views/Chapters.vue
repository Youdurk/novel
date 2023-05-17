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
            <el-table-column prop="bname" label="书名" width="140">
            </el-table-column>
           
            <el-table-column prop="chapters" label="章节" width="120">
            </el-table-column>
            <el-table-column prop="sequence" label="顺序">
            </el-table-column>
            <el-table-column label="操作" width="200" >
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
        <el-dialog title="小说添加" :visible.sync="dialogFormVisible">
        <el-form :model="form" label-width="200px" >
         <el-form-item label="书名" >
          <el-input v-model="form.bname" autocomplete="off" style="width:200px"></el-input>
        </el-form-item>
         <el-form-item label="章节" >
          <el-input v-model="form.chapters" autocomplete="off" style="width:200px"></el-input>
        </el-form-item>
           <el-form-item label="顺序" >
           <el-input v-model="form.sequence" autocomplete="off" style="width:200px"></el-input>
        </el-form-item>
        <el-form-item label="内容" >
          <div id="edi"></div>
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
import E from 'wangeditor'
let editor;
export default{
    name:"chapters",
    data() {
        return {
            tableData:[],
            pageNum:1,
            pageSize:10,
            total:0,
            bname:"",
            form:[],
            dialogFormVisible:false,
            multipleSelection:[],
        }
    },
    created()
    {this.load()
    },
    methods:
    {
        load(){
            this.request.get("/chapters/page",{params:{
            pageNum:this.pageNum,
            pageSize:this.pageSize,
            bname:this.bname
            }}).then(res=>{
                console.log(res.data)
              this.tableData=res.data.records
              this.total=res.data.total
            })
        },
        handleSizeChange(pageSize){
            Console.log(pageSize)
            this.pageSize=pageSize
            this.load()
        },
        update(val){
          this.dialogFormVisible=true
          this.form=val
          this.$nextTick(()=>{
                  if(!editor){
                     editor=new E('#edi');
                     editor.create();
                  }
                 editor.txt.html(val.content)
                })
                
        },
        handleSelectionChange(val){
          this.multipleSelection=val
        },
        bel(){
          let ids=this.multipleSelection.map(v=>v.id)
          this.request.post("/chapters/del/bath",ids).then(res =>{
        if (res.data) {
                    this.$message.success("批量删除成功")
                    this.load()
                } else {
                    this.$message.error("批量删除失败")
                } 
      })
        },
        insert(){
          this.form.content=editor.txt.text()
          this.request.post("/chapters/updatas",this.form).then(res=>{
            if(res.code=='200'){
              this.dialogFormVisible=false
              this.$message.success("修改成功")
              this.load()
            }else{
              this.$message.error("修改失败")
            }
          })
        },
        deletec(id){
          this.request.delete("/chapters/"+id).then(res => {
            if(res.code=="200"){
              this.$message.success("删除成功")
              this.load()
            }else{
              this.$message.error("删除失败")
            }
          })
        },
        handleCurrentChange(pageNum){
            this.pageNum=pageNum
            this.load()
        }
    },
    
    
}
</script>