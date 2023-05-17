<template>
    <div>
        <el-row >
            <div v-for="item in novel" :key="item.id">
             <el-col :span="6" >
                <div  class="buj">
                <span type="selection"></span>
                <el-image :src="item.bookUrl" style="widows: 100%;height: 150px;" @click="$router.push('/briefbook?id='+item.id)"></el-image>
                <span style="text-align: center;display:block;" @click="$router.push('/briefbook?id='+item.id)">{{item.bname}}</span>
                <el-button type="danger"  style="float: left;" @click="dele(item.id)">删除</el-button>
                </div>
            </el-col>
            </div>
        </el-row>
    </div>
</template>
<script>
export default {
    name:"bookshelf",
    data() {
        return {
            novel:[],
            user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):[]
        }
    },
    created(){
        this.load()
    },
    methods:{
        load(){
            this.request.get("/novel/bookshlf/"+this.user.username).then(res=>{
                this.novel=res.data
                console.log(this.novel)
            })
        },
        dele(id){
                this.request.delete("/novel/bookshlf/"+this.user.username+"/"+id).then(res=>{
                    if(res.code=='200'){
                        this.$message.success("yes")
                        this.load()
                    }
                })
        }
    }
}
</script>
<style>
.buj{
    width: 280px;
    height:1900px;
    margin: 30px;
}
</style>