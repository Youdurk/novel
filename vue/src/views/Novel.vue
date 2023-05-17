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
        <el-button type="primary" @click="add">新增</el-button>
         <el-button type="primary" @click="bel">批量删除</el-button>
      </div>
          <el-table :data="tableData" @selection-change="handleSelectionChange">
            <el-table-column
                    type="selection"
                    width="55">
            </el-table-column>
            <el-table-column prop="id" label="id" width="50">
            </el-table-column>
             <el-table-column prop="bookUrl" label="书面" width="140">
              <template slot-scope="scope">
                <el-image
                style="width: 100px; height: 100px"
              
                :src="scope.row.bookUrl"
                />
                 </template>
            </el-table-column>
            <el-table-column prop="bname" label="书名" width="140">
            </el-table-column>
           
            <el-table-column prop="heat" label="热度" width="120">
            </el-table-column>
            <el-table-column prop="author" label="作者">
            </el-table-column>
            <el-table-column prop="brief" label="简介"  :show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column prop="btype" label="类型">
            </el-table-column>
            <el-table-column label="操作" width="300" >
                <template slot-scope="scope">
                  <el-button type='info' @click="adds(scope.row.id)" style="margin-right:10px">新增章节</el-button>
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
          <el-form-item  label="书面" style="width:150px">
                <el-upload
                    class="avatar-uploader"
                    :action="'http://localhost:9090/file/upload'"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    >
                    <el-image v-if="form.bookUrl" :src="form.bookUrl" class="avatar" />
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
         
        </el-form-item>
        
         <el-form-item label="书名" >
          <el-input v-model="form.bname" autocomplete="off" style="width:200px"></el-input>
        </el-form-item>
         <el-form-item label="作者" >
          <el-input v-model="form.author" autocomplete="off" style="width:200px"></el-input>
        </el-form-item>
           <el-form-item label="简介" >
           <el-input v-model="form.brief" autocomplete="off" 
       
            maxlength="150"
            type="textarea"
            show-word-limit
           style="width:400px;height: 100%;"></el-input>
        </el-form-item>
        <el-form-item label="类型" >
          <el-input v-model="form.btype" autocomplete="off" style="width:200px"></el-input>
        </el-form-item>
        
        </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="insert" >确 定</el-button>
  </div>
</el-dialog>
          </div>
          <el-dialog title="小说添加" :visible.sync="chapterst">
        <el-form :model="chaptersa" label-width="200px" >
         <el-form-item label="章节" >
          <el-input v-model="chaptersa.chapters" autocomplete="off" style="width:200px"></el-input>
        </el-form-item>
           <el-form-item label="顺序" >
           <el-input v-model="chaptersa.sequence" autocomplete="off" style="width:200px"></el-input>
        </el-form-item>
        <el-form-item label="内容" >
          <div id="edi"></div>
        </el-form-item>
        
        </el-form>
  <div slot="footer" class="dialog-footer">
    
    <el-button @click="chapterst = false">取 消</el-button>
    <el-button type="primary" @click="adda" >确 定</el-button>

  </div>
</el-dialog>
  </div>
</template>
<script>
import E from 'wangeditor'
let editor;
export default {
    name: "novels",
    data() {
        return {
            tableData:[],
            pageNum:1,
            pageSize:10,
            total:0,
            bname:"",
            multipleSelection:[],
            form:{},
            cd:"",
            chaptersa:{},
            dialogFormVisible:false,
            chapterst:false,
            novel:localStorage.getItem("novel")?Jsop.parse(localStorage.getItem("novel")):{}
        };
    },
    created(){
      this.load()
    },
    methods: {
      load(){
          this.request.get("/novel/page",{params:{
             pageNum:this.pageNum,
            pageSize:this.pageSize,
            bname:this.bname
          }}).then(res =>{
            console.log(res.data);
            this.tableData=res.data.records
              this.total=res.data.total
          })
      },
      handleSizeChange(pageSize){
        this.pageSize=pageSize
        this.load
      },
      handleCurrentChange(pageNum){
        this.pageNum=pageNum
        this.load()
      },
      handleSelectionChange(val){
       this.multipleSelection=val
      },
      handleAvatarSuccess(res){
        this.form.bookUrl=res
    },
   
    add(){
            this.dialogFormVisible=true
            this.form={}
        },
        
        adds(id){
          this.chapterst=true
          this.chaptersa={}
          this.cd=id
          this.$nextTick(()=>{
                  if(!editor){
                     editor=new E('#edi');
                     editor.create();
                  }
                 editor.txt.html('')
                })
                 
        },
        adda(){
          this.chaptersa.content=editor.txt.text()
          this.request.post("/chapters/insert/"+this.cd,this.chaptersa).then(res =>{
            if(res.code=="200"){
              this.chapterst=false
              editor.txt.html("")
              this.$message.success("添加成功")
              this.load()
            }else{
              this.$message.error("添加成功")
            }
          })
        },
        deletec(id){
          this.request.delete("/chapters/del/"+id).then(res=>{
            if(res.code=="200"){
            this.$message.success("删除成功")
            this.load()
          }else{
            this.$message.error("删除失败")
          }
          })
        },
        bel(){
          let ds=this.multipleSelection.map(v=>v.id)
          console.log(ds)
          this.request.post("novel/del/bath",ds).then(res=>{
            if(res.code=="200"){
            this.$message.success("删除成功")
            this.load()
          }else{
            this.$message.error("删除失败")
          }
          })
        },
        update(val){
          this.dialogFormVisible=true
          this.form=val
        },
        insert(){
         this.request.post("/novel/insert",this.form).then(res =>{
          if(res.code=="200"){
            this.dialogFormVisible=false
            this.$message.success("操作成功")
            this.load()
          }else{
            this.$message.error("操作失败")
          }
         })
        }
    },
}
</script>
<style>
.avatar {
        width: 142px;
        height: 156px;
        display: block;
    }
</style>