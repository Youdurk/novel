<template>
    <div class="about">
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
          <el-table-column prop="name" label="角色" >
          </el-table-column>
          <el-table-column prop="description" label="简介" >
          </el-table-column>
          <el-table-column label="操作" width="300" >
              <template slot-scope="scope">
                <el-button type='warning' @click="distribute(scope.row)" style="margin-right:10px">分配权限</el-button>
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
        <el-dialog title="角色管理" :visible.sync="dialogFormVisible">
      <el-form :model="form" label-width="200px" >
      <el-form-item label="角色">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
       <el-form-item label="简介" >
        <el-input v-model="form.description" autocomplete="off"  ></el-input>
      </el-form-item>
      </el-form>
        <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="insert" >确 定</el-button>
        </div>
</el-dialog>
        </div>
        <div>
            <el-dialog title="分配权限" :visible.sync="limit">
                <el-tree
                :prop="props"
                :data="muendata"
                show-checkbox
                node-key="id"
                ref="tree"
                :default-expanded-keys="cx"
                :default-checked-keys="ex"
                @check-change="handleCheckChange">
                <span class="custom-tree-node" slot-scope="{data}">
                        <span><i :class="data.icon"></i>{{data.name}}</span>
                    </span>
                </el-tree>
                <div slot="footer" class="dialog-footer">
                 
        <el-button @click="limit = false">取 消</el-button>
        <el-button type="primary" @click="saverolemuen" >确 定</el-button>
        </div>
            </el-dialog>
        </div>
</div>
</template>
<script>
export default {
    name: "role",
    data() {
        return {
            tableData:[],
            pageNum:1,
            pageSize:10,
            total:0,
            form:{},
            dialogFormVisible:false,
            mut:[],
            props:{
              label:'name'
            },
            muendata:[],
            ex:[],
            cx:[],
            limit:false,
            roleid:0,
            roleflag:'',
        }
    },
    created(){
        this.load()
    },
    methods:{
        load(){
        this.request.get("/role/page",{params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
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
    handleCheckChange(data,checked,indeterminate){
      console.log(data, checked, indeterminate);
    },
    deletec(id){
        this.request.delete("/role/"+id).then(res =>{
          if(res.code=="200"){
            this.$message.success("删除成功")
            this.load()
          }else{
            this.$message.error("删除失败")
          }
        })
    },
    distribute(role){
      this.roleid=role.id
      this.roleflag=role.flag
      this.request.get("/muen").then(res=>{
        this.muendata=res.data
        this.ex=this.muendata.map(m=>m.id)
      })
      this.request.get("/muen/muen/"+this.roleid).then(res=>{
          this.cx=res.data
         this.limit=true
         this.request.get("/muen/ids").then(r=>{
          const ids=r.data
          ids.forEach(id => {
            if(!this.cx.includes(id)){
              this.$nextTick(()=>{
                this.$refs.tree.setChecked(id,false)
              })
            }})
      })
    })
    },
    saverolemuen(){
      this.request.post("/muen/role/"+this.roleid,this.$refs.tree.getCheckedKeys()).then(res=>{
        if (res.code=="200"){
          this.$message.success("绑定成功")
          this.limit=false
          if (this.roleflag=='ROLE_ADMIN'){
              this.$store.commit("logout")
          }
      }else {
          this.$message.error(res.msg)
      }
    })
    },
    bel(){
      let ids=this.mut.map(v=>v.id)
      this.request.post("/role/del/ids",ids).then(res=>{
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
    },
    update(row){
      this.dialogFormVisible=true
      this.form=row
    },
    insert(){
        this.request.post("/role/insert",this.form).then(res =>{
          if(res.code=='200'){
            this.dialogFormVisible=false
            this.$message.success("成功")
            this.load()
          }else{
            this.$message.error("失败")
          }
        })
    },
    add(){
      this.dialogFormVisible=true
      this.form={}
      this.load()
    },
    dd(){},
    }
}
</script>