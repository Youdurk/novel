<template>
     <div class="day0">
        <div class="day1">
            <el-card class="box-card">
         <div slot="header" class="clearfix">
                    <span>{{novel.bname}}</span>
                    </div>
               
                <div >
                      <el-row>
                        <el-col :span="6" v-for="item in catalogue" :key="item.id" style="margin: 5px;">
                            <div style="cursor: pointer;">
                                <a @click="$router.push('/reads?id='+novel.id+'&sequence='+item.sequence)">{{item.chapters}}</a></div>
                        </el-col>
                      </el-row> 
                </div>
                </el-card>
                </div>
                    </div>
</template>
<script>
export default {
    name:"catalogue",
    data() {
        return {
            id:this.$route.query.id,
            novel:[],
            catalogue:[],
            
        }
    },
    created(){
       this.load()
        
    },
    methods:{
        load(){
            this.request.get("/novel/selectid/"+this.id).then(res =>{
                this.novel=res.data
                console.log(this.novel)
            })
            this.request.get("/chapters/catalogue/"+this.id).then(res =>{
                this.catalogue=res.data
                console.log(this.catalogue)
            })
        },

    },
}
</script>
<style>
.day0{
    background-color: #AFEEEE;
}
.day1{
    padding:5px;
    width: 800px;
    margin-left: 230px;
}
.clearfix{
    text-align: center;
}
</style>