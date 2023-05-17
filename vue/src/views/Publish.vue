<template>
    <!-- 发布小说 -->
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>卡片名称</span>
            </div>
        <el-form :model="novel" label-width="200px"  style="margin-left: 30px;">
          <el-form-item  label="书面" style="width:220px">
                <el-upload
                    class="avatar-uploader"
                    :action="'http://localhost:9090/file/upload'"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    >
                    <el-image v-if="novel.bookUrl" :src="novel.bookUrl" class="avatar" />
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        </el-form-item>
        
         <el-form-item label="书名" >
          <el-input v-model="novel.bname" autocomplete="off" style="width:300px"></el-input>
        </el-form-item>
    
           <el-form-item label="简介" >
           <el-input v-model="novel.brief"
            autocomplete="off" 
            maxlength="150"
            type="textarea"
            show-word-limit
            style="width:300px"></el-input>
        </el-form-item>
        <el-form-item label="类型" >
         <el-select v-model="novel.btype" placeholder="请选择"  style="width: 300px;">
        <el-option
        v-for="item in classfinca"
        :key="item"
        :label="item"
        :value="item"
        >
        </el-option>
  </el-select>
        </el-form-item>
        </el-form>
    <div class="text">
            <el-button type="primary" @click="add">发布小说</el-button>
        </div>
</el-card>

        
    </div>
</template>
<script>
export default {
    name:'publish',
    data(){
        return{
            novel:[],
            con:'',
            classfinca:[],
            user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):[]
        }
    },
    created(){
        this.getds()
         this.novel={}
    },
    methods:{
        handleAvatarSuccess(res){
            this.novel.bookUrl=res
        },
        add(){
            this.novel.author=this.user.nickname
                this.request.post("/novel/release/"+this.user.username,this.novel).then(res=>{
            if(res.code=='200'){
                this.novel={}
                this.$message.success("发布成功")
            }else{
                this.$message.error("该小说已存在")
            }
          })
            
          
        },
        getds(){
            this.request.get("/novel/classification").then(res=>{
                this.classfinca=res.data
                console.log(this.classfinca)
            })
        }
    },
}
</script>
<style>
.box-card{
    margin: 80px;
    
}
.clearfix{
    display:flex;
    justify-content: center;
}
.text{
    display:flex;
    justify-content: center;
    margin-top: 15px;
}

</style>