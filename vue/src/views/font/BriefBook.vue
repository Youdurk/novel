<template >
    <div style="background-color:#87CEFA">
   <div style="display:flex;justify-content: center;" >
        <el-card :body-style="{margin: '10px'}">
            <div style="display:flex;width: 750px;" > 
                <div style="width: 280px;">
                 <img :src="novel.bookUrl" class="image" style="width:100%" />
                </div>
   
               <div style="flex: 1;padding-left: 50px;">
                    <div class="b1" style="text-align:center"><h1>{{novel.bname}}</h1></div>
                    <div class="b1">作者:{{novel.author}}</div>
                    <div class="b1">类型:{{novel.btype}}</div>
                    <div class="b1"><a>简介:{{novel.brief}}</a></div>
                    <div class="b1">
                        <el-button type="danger" @click="$router.push('/reads?id='+novel.id+'&sequence=1')">立即阅读</el-button>
                        <el-button type="primary" @click="joins">加入书架</el-button>
                        <el-button type="warning" @click="$router.push('/catalogue?id='+novel.id)">查看目录</el-button>
                    </div>
                </div>
                </div>
                <div><el-tabs >
                    <el-tab-pane label="评价管理">
                        <div v-for="item in comment" :key="item.id">
                        <div>
                            <div style="">
                                <el-avatar :size="50" :src="item.avatarurl" />
                               <span style="margin-left: 20px;position: absolute;"> {{item.nickname}}</span>
                               </div>
                            <div style="margin-left: 50px;">{{item.content}}</div>
                            <el-divider></el-divider>
                        </div>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="发布评论">
                        <div>
                            <el-input v-model="publish.content" placeholder="请输入内容"
                            maxlength="150"
                             show-word-limit></el-input>
                            <div>
                                <el-button type="danger" @click="ss">发布</el-button>
                            </div>
                        </div>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </el-card>
   </div>
   </div>
</template>
<script>
import store from '@/store'
export default{
    name:'briefbook',
    data() {
        return {
            id:this.$route.query.id,
            heat:this.$route.query.heat,
            novel:{},
            publish:{},
            user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):[],
          de:"sss",
          input:"",
          comment:[],
        }
    },
    created(){
        this.load()
        if(this.heat!=null){
        this.request.post("/novel/heats/"+this.heat+"/"+this.id).then(res =>{
         })
        }
       
    },
    methods:{
        load(){
            this.request.get("/novel/selectid/"+this.id).then(res =>{
                this.novel=res.data
                console.log(this.novel)
            })
            this.request.get("/comment/bcomm/"+this.id).then(res=>{
                this.comment=res.data
                console.log(this.comment)
            })
            
        },
        joins(){
            if(this.user.token !=null){
               this.request.post("/novel/addbook/"+this.user.username+"/"+this.novel.id).then(res=>{
                if(res.data=='-1'){
                   this.$message.error("该书籍已存在")
                }else{
                     this.$message.success("添加成功")
                    this.load()
                    console.log(res.data)
                }
               })
            }else{
                this.$router.push("/login")
            }
        },
        ss(){
           if(this.user !=""){
            if(this.publish.content!="" && this.publish.content!=null){
                this.publish.tableid=this.id
            console.log(this.publish)
           this.request.post("/comment/pucomm/"+this.user.username,this.publish).then(res=>{
            if(res.code=='200'){
                this.$message.success("发布成功")
                this.load()
                this.publish={}
            }else{
                this.$message.error("发布失败")
            }
           })
            }else{
                this.$message.error("请输入评论")
            }
            
           }else{
            this.$message.error("请登录")
           }
        }
    }
}
</script>
<style>
.b1 {
    padding: 5px;
}
</style>