<template>
    <el-card style="width: 60%;margin-left: 20%;">
        <el-form label-width="80px" style="margin: 50px;width: 250px;height: 100%">
            <el-upload
                    class="avatar-uploader"
                    :action="'http://localhost:9090/file/upload'"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    >
                <img v-if="form.avatarurl" :src="form.avatarurl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        <el-form-item label="用户名"  >
            <el-input v-model="form.username" disabled autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称" >
            <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄" >
            <el-select v-model="form.age" placeholder="请选择">
                <el-option
                v-for="item in 100"
                :key="item"
                :label="item"
                :value="item"
               >
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="手机号" >
            <el-input v-model="form.mobile" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" >
            <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址" >
            <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="save" >确 定</el-button>
        </el-form-item>
    </el-form>
    </el-card>
</template>
<script>
export default {
    name:'Personal',
    data() {
        return {
            form:[],
        }
    },
    created(){
       this.load()
    },
    methods:{
        load(){
            let username=localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")).username:''
        this.request.get("/user/"+username).then(res=>{
             if(res.data){
                console.log(res.data)
                this.form=res.data
             }
            })
        },
        handleAvatarSuccess(val){
            this.form.avatarurl=val
        },
        save(){
        this.request.post("/user/insert",this.form).then(res =>{
        if(res.code=="200"){
          this.$message.success("修改成功")
          this.load()
        }else{
          this.$message.error("修改失败")
        }
      })
        },

    }
}
</script>
<style>
.avatar {
        width: 142px;
        height: 156px;
        display: block;
    }
</style>