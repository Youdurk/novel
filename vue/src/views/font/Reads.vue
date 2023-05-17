<template>
    <div class="day0">
        <div class="day2">    
 </div>
        <div class="day1">  
            <el-card class="box-card">
         <div slot="header" class="clearfix">
                    <div><span >{{read.chapters}}</span></div>
                    <div>
                        <el-button type="primary" @click="add" class="el-icon-d-arrow-left"></el-button>
                           {{this.sds}}
                         <el-button type="primary" @click="reduce" class="el-icon-d-arrow-right"></el-button>
                         <span v-for="index in niu" :key="index.id" style="margin-left: 3px;">
                            <el-button type="primary" @click="cl1(index.id)" >{{index.name}}</el-button>
                        </span>
                         <!-- <el-button type="primary" @click="cl1" class="el-icon-d-arrow-right">卡其布</el-button>
                         <el-button type="primary" @click="cl2" class="el-icon-d-arrow-right">暗淡的灰色#696969</el-button>
                         <el-button type="primary" @click="cl3" class="el-icon-d-arrow-right">亮灰色#DCDCDC</el-button>
                         <el-button type="primary" @click="cl4" class="el-icon-d-arrow-right">天蓝色#87CEEB</el-button> -->
                        </div>
                    
                    </div>
               <div  :style="'background-color: #'+this.clor+';'">
                <div :style="'font-size:'+this.sds+'px;'"  >
                   
                          {{read.content}}
                  
                </div>
                <div style="text-align:center;">
                    <template > 
                    <el-button type="" @click="Lastread">上一章</el-button>
                    <el-button type="" @click="$router.push('/catalogue?id='+id)">目录</el-button>
                    <el-button type="" @click="$router.push('/homepage')">返回首页</el-button>
                    <el-button type="" @click="nextread">下一章</el-button>
                    </template>
                </div>
                </div>
                </el-card>
                </div>
              
                    </div>
</template>
<script>
import router from '@/router'
import { color } from 'echarts'
export default {
    name:"reads",
    data() {
        return {
            id:this.$route.query.id,
            read:{},
            niu:[
                {
                    id:1,
                    name:"卡其布"},
                {
                    id:2,name:"暗淡的灰色"},
                {
                    id:3,
                    name:"亮灰色"},
                {
                    id:4,
                    name:"天蓝色"},
            ],
            sequence:1,
            sds:20,
            clor:'F0E68C',
        }
    },
    created(){
        this.add()
        this.reduce()
            this.load()
            console.log(this.sequence)
    },
    methods: {
        load(){
            this.request.get("/chapters/reads",{params:{
                bid:this.$route.query.id,
                sequence:this.$route.query.sequence
            }}).then(res=>{
                if(res.data){
                    this.read=res.data
                console.log(this.read)
                }else{
                    this.$message.error("系统错误,请联系客服");
                }
            })
        },
        nextread(){
            let e=this.read.sequence+1
           this.request.get("/chapters/reads/",{params:{
            bid:this.id,
            sequence:e
           }}).then(res=>{
            if(res.data!=null){
                this.read=res.data
                console.log(this.read)
                window.scrollTo(0,0)
            }else{
                    this.$message.error("没有后续章节了");
            }
           })
        },
        Lastread(){
            let e=this.read.sequence-1
            if(e!=0){
                this.request.get("/chapters/reads/",{params:{
                    bid:this.id,
            sequence:e
           }}).then(res=>{
            if(res.data){
                this.read=res.data
                console.log(this.read)
                window.scrollTo(0,0)
            }else{
                router.push("/reads?id="+this.id)
               this.$message.error("系统错误,请联系客服");
            }
           })
            }else{
                this.$message.error("这是第一章");
            }
            
        },
        add(){
            if(this.sds>10){
                  this.sds--;
            }else{
                this.$message.error("已到最小")
            }
          
                },
        reduce(){
            if(this.sds<30){
                this.sds++;
            }else{
                this.$message.error("已到最大")
            }
            
        },
        cl1(val){
           if(val==2){
            this.clor='696969'
           }else if(val==3){
            this.clor='DCDCDC'
           }else if(val==4){
            this.clor='87CEEB'
           }else if(val==1){
            this.clor='F0E68C'
        }
        }

    },
}
</script>
<style>
.day0{
    background-color: #AFEEEE;
}
.day1{
    display:flex;
    justify-content: center;
   
}
.box-card{
    width:850px;
 
}
.clearfix{
    text-align: center;
}
.day2{
    padding: 5px;
    margin-left: 135px;
}
</style>