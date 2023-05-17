<template>
    <!-- 章节发布 -->
    <div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>卡片名称</span>
            </div>
            <div style="margin-left: 178px;">
                <span>书名</span>&nbsp;
        <el-select v-model="tablename" placeholder="请选择"  style="width: 300px;">
        <el-option
        v-for="item in novel"
        :key="item.banme"
        :label="item.bname"
        :value="item.bname"
        >
        </el-option>
        </el-select>
            </div>
        <el-form :model="chapters" label-width="200px"  style="margin-left: 30px;margin: 20px;">
          <el-form-item  label="章节" >
            <el-input v-model="chapters.chapters"  autocomplete="off" style="width:300px" placeholder="请输入"></el-input>
        </el-form-item>
        
         <el-form-item label="顺序" >
          <el-input v-model="chapters.sequence" autocomplete="off" style="width:300px"></el-input>
          <!-- <el-upload :action="'http://localhost:9090/file/uword'"
                    :limit="3" style="display:inline-block;margin-left: 5px"
                    accept="xlsx" :on-success="Impsucceeded">
            <el-button type="primary">导入<i class=""></i></el-button>
            </el-upload> -->
            <el-upload :action="'http://localhost:9090/file/uword'" :http-request="uploadSectionFile">
                <el-button type="primary">导入<i class=""></i></el-button>
            </el-upload>
        </el-form-item>
    
           <el-form-item label="内容" >
            <div id="div1"> </div>
        </el-form-item>
       
        </el-form>
    <div class="text">
            <el-button type="primary" @click="add">发布小说</el-button>
        </div>
</el-card>

        
    </div>
</template>
<script>
import E from "wangeditor";
let editor;
export default {
    name:'publishchart',
    data(){
        return{
            chapters:{},
            classfinca:[],
            tablename:this.$route.query.tablename,
           user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):[],
           novel:[],
        }
    },
    created(){
        if(this.tablename==null){
            this.request.get("/novel/getUN/"+this.user.username).then(res=>{
                    this.novel=res.data
            })
        }
        this.load();
        this.chapters={}
    },
    methods:{
        load(){
            this.$nextTick(res=>{
                editor=new E('#div1');
       editor.create();
            })   
        },
        add(){
             this.chapters.content=editor.txt.text()
           this.request.post("/chapters/authoradc/"+this.tablename,this.chapters).then(res=>{
                if(res.code=='200'){
                    this.$message.success("编辑成功")
                    this.chapters={}
                    editor.txt.clear()
                }
           })
           
        },
        Impsucceeded(){

        },
        uploadSectionFile(param){
            var fileObj = param.file;
            var form = new FormData();
            form.append("file", fileObj);
            this.request.post("/file/uword",form).then(res=>{
               editor.txt.html(res.data)
           })
        },
    }

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