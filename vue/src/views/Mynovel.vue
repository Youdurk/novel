<template>
    <!-- 作者：我的小说  -->
    <div>
        <el-main>
      <el-table :data="tableData" @selection-change="handleSelectionChange">
        <el-table-column
      type="selection"
      width="55">
    </el-table-column>
        <el-table-column prop="bname" label="书名">
        </el-table-column>
        <el-table-column prop="bookUrl" label="界面" >
            <template slot-scope="scope"> 
            <img :src="scope.row.bookUrl" alt="" style="width: 140px;height: 100%;"></template>
        </el-table-column>
        <el-table-column prop="brief" label="简介">
        </el-table-column>
        <el-table-column  label="操作" >
            <template slot-scope="scope">
              <el-popconfirm
                            class="ml-5"
                            confirm-button-text='确定'
                            cancel-button-text='我再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="您确定删除吗？"
                            @confirm="novelde(scope.row.id)"
                    >
                        <span type="danger" slot="reference" >删除<i class="el-icon-delete"></i></span>
                    </el-popconfirm>
                <a>
                <span @click="upnovel(scope.row)" style="margin: 5px;">编辑</span>
                <span @click="chars(scope.row.chapters)" style="margin: 5px;">查看章节</span>
                <!-- <router-link :to="{path:'/publishchart',query:{id:scope.row.id}}">发布新章节</router-link> -->
              
              </a>
            <span> <router-link :to="'/publishchart?tablename='+scope.row.bname" style="margin: 5px;">发布新章节</router-link></span>
            </template>
        </el-table-column>
      </el-table>
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
           <el-input v-model="form.brief" autocomplete="off" style="width:200px"></el-input>
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
  <div>
    <el-dialog title="收货地址" :visible.sync="chapterslimit">
  <el-table :data="chapters">
    <el-table-column property="chapters" label="章节" width="150"></el-table-column>
    <el-table-column property="sequence" label="顺序"></el-table-column>
    <el-dialog
      title="内层 Dialog"
      :visible.sync="innerVisible"
      append-to-body>
        <el-form :model="chapter" label-width="200px" >
         <el-form-item label="章节" >
          <el-input v-model="chapter.chapters" autocomplete="off" style="width:200px"></el-input>
        </el-form-item>
           <el-form-item label="顺序" >
           <el-input v-model="chapter.sequence" autocomplete="off" style="width:200px"></el-input>
        </el-form-item>
        <el-form-item label="内容" >
          <div id="edi"></div>
        </el-form-item>
        </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="innerVisible = false">取 消</el-button>
    <el-button type="primary" @click="cinsert" >确 定</el-button>
  </div>

    </el-dialog>
    <el-table-column  label="操作">
            <template slot-scope="scope">
                <el-button @click="deletec(scope.row.id)">删除</el-button>
                <el-button @click="upadte(scope.row)">编辑</el-button>
            </template>
    </el-table-column>
  </el-table>
</el-dialog>
  </div>
    </el-main>
    </div>
</template>
<script>
import E from 'wangeditor'
let editor;
export default {
    name:'mynovel',
    data(){
        return{
            tableData:[],
            user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):[],
            dd:[],
            form:[],
            chapters:[],
            chapter:[],
            dialogFormVisible:false,
            chapterslimit:false,
            innerVisible:false
        }
    },
    created(){
        this.load()
    },

    methods:{
        load(){
            this.request.get("/novel/getUN/"+this.user.username).then(res=>{
                    this.tableData=res.data
                    console.log(this.tableData);
            })
        },
        handleSelectionChange(val){
            this.dd=val
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
        },
        cinsert(){
          this.chapter.content=editor.txt.text()
          this.request.post("/chapters/updatas",this.chapter).then(res =>{
          if(res.code=="200"){
            this.innerVisible=false
            this.$message.success("操作成功")
            this.load()
          }else{
            this.$message.error("操作失败")
          }
         })
        },
        novelde(id){
          this.request.delete("/chapters/del/"+id).then(res=>{
            if(res.code=="200"){
            this.$message.success("删除成功")
            this.load()
          }else{
            this.$message.error("删除失败")
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
        upadte(row){
                this.innerVisible=true
                this.chapter=row
                this.$nextTick(()=>{
                  if(!editor){
                     editor=new E('#edi');
                     editor.create();
                  }
                 editor.txt.html(row.content)
                })
                
                 
        },
        handleAvatarSuccess(res){
            this.form.bookUrl=res
        },
        chars(val){
            this.chapters=val
            this.chapterslimit=true
        },
        upnovel(row){
            this.form=row
            this.dialogFormVisible=true
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