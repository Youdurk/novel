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
            <el-table-column prop="id" label="id" width="50">
            </el-table-column>
            <el-table-column prop="avatarurl" label="头像" width="140">
              <template slot-scope="scope">
                <el-image
      style="width: 100px; height: 100px"
        alt=""
      :src="scope.row.avatarurl"
      ></el-image>
                 </template>
            </el-table-column>
            <el-table-column prop="username" label="用户名" width="140">
            </el-table-column>
            <el-table-column prop="nickname" label="昵称" width="120">
            </el-table-column>
            <el-table-column prop="age" label="年龄">
            </el-table-column>
            <el-table-column prop="address" label="地址">
            </el-table-column>
            <el-table-column prop="mobile" label="手机">
            </el-table-column>
            <el-table-column prop="email" label="邮箱">
            </el-table-column>
            <el-table-column prop="sate" label="状态">
              <template slot-scope="scope">
                <el-tag  type="danger" v-if="scope.row.sate==0">冻结</el-tag>
                <el-tag type="success" v-if="scope.row.sate==1">使用中</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="200" >
                <template slot-scope="scope">
                  <el-button type='warning' @click="update(scope.row)" style="margin-right:10px">查看</el-button>
                 <el-popconfirm
                            class="ml-5"
                            confirm-button-text='确定'
                            cancel-button-text='我再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="您确定删除吗？"
                            @confirm="deletec(scope.row.id)"
                    >
                        <el-button type="danger" slot="reference" >冻结<i class="el-icon-delete"></i></el-button>
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
          <el-form-item  label="头像">
                <el-upload
                    class="avatar-uploader"
                    :action="'http://localhost:9090/file/upload'"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    >
                    <img v-if="form.avatarurl" :src="form.avatarurl" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        </el-form-item>
         <el-form-item label="用户名" >
          <el-input v-model="form.username" disabled autocomplete="off"  ></el-input>
        </el-form-item>
         <el-form-item label="密码" >
          <el-input v-model="form.password" disabled show-password autocomplete="off"></el-input>
        </el-form-item>
           <el-form-item label="昵称" >
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄" >
          <el-input v-model="form.age" autocomplete="off"></el-input>
        </el-form-item>
         <el-form-item label="地址" >
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
         <el-form-item label="手机" >
          <el-input v-model="form.mobile" autocomplete="off"></el-input>
        </el-form-item>
         <el-form-item label="邮箱">
         <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
         <el-form-item label="角色" prop="role">
          <el-select v-model="form.role" placeholder="请选择角色">
            <el-option label="读者" value="读者"></el-option>
            <el-option label="作者" value="作者"></el-option>
          </el-select>
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
  name:'User',
  data() {
        return {
            tableData:[],
            pageNum:1,
            pageSize:10,
            total:0,
            nickname:"",
            multipleSelection:[],
            form:[],
            dialogFormVisible:false,
        };
    },
    created(){
      this.load()
    },
    methods:{
     load() {
      this.request.get("/user/page",{params:{
                pageNum:this.pageNum,
                pageSize:this.pageSize,
                nickname:this.nickname
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
     handleSelectionChange(val) {
        console.log(val)
        this.multipleSelection = val
    },
    add(){
      this.dialogFormVisible=true
      this.form={}
    },
    update(val){
      this.dialogFormVisible=true
      this.form=val
    },
    insert(){
      this.request.post("/user/insert",this.form).then(res =>{
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
      this.request.delete("/user/"+id).then(res =>{
          if(res.code=="200"){
              this.$message.success("删除成功")
              this.load()
          }else{
                this.$message.error("删除失败")
          }
      })
    },
    handleAvatarSuccess(res){
        this.form.avatarurl=res
    },
    bel(){
      let ds=this.multipleSelection.map(v=>v.id)
      this.request.post("/user/del/bath",ds).then(res =>{
        if (res.data) {
                    this.$message.success("批量删除成功")
                    this.load()
                } else {
                    this.$message.error("批量删除失败")
                } 
      })
    }
    }
}
</script>
<style>

</style>
