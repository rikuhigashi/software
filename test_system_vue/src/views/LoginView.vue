<template>

    <div class="loginBody">
        <div class="loginDiv">
            <div class="login-content">
                <h1 class="login-title">用户登录</h1>
                <el-form :model="loginForm" ref="loginForms" :rules="rules" label-width="auto"
                    style="max-width: 600px;">
                    <el-form-item label="账号：">
                        <el-input v-model="loginForm.no" autocomplete="off" />
                    </el-form-item>
                    <el-form-item label="密码：" prop="password">
                        <el-input v-model="loginForm.password" type="password" autocomplete="off"
                            @keyup.enter="confirm" />

                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="confirm" :disabled="confirm_disabled">
                            确定
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>

</template>

<script lang="ts" setup>
import router from '@/router';
import store from '@/store';
import axios from 'axios';
import { ElMessage, FormInstance } from 'element-plus';
import { reactive, ref } from 'vue';

let confirm_disabled = ref(false)
const loginForms = ref<FormInstance | null>(null);
let loginForm = reactive({
    no: '',
    password: ''

})

const rules = {
    no: [
        { required: true, message: '请输入账号', trigger: 'blur' }

    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' }
    ]
}

function confirm() {
    confirm_disabled.value = true
    if (!loginForms.value) return
    loginForms.value.validate((valid) => {
        if (valid) {
            axios.post('/user/login', loginForm).then(res => res.data).then(res => {
                // console.log(res);
                if (res.code === 200) {
                    ElMessage({
                        message: '登录成功',
                        type: 'success',
                    })

                    sessionStorage.setItem("CurUser", JSON.stringify(res.data.user))

                    // console.log(res.data.menu);
                    store.commit("setMenu",res.data.menu)

                    router.replace('/index')
                    // router.push('/HomeView')  // 会留下历史记录 可返回
                    router.replace('/HomeView')  // 不会留下历史记录 不可返回

                } else {
                    ElMessage({
                        message: '账号或密码错误！',
                        type: 'warning',
                    })

                    confirm_disabled.value = false
                    return false
                }
            })
        } else {
            confirm_disabled.value = false
            console.log('校验失败');


        }
    })

}



</script>

<style scoped>
.loginBody {
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: #B3c0D1;
}

.loginDiv {
    position: absolute;
    top: 50%;
    left: 50%;
    margin-top: -200px;
    margin-left: -250px;
    width: 450px;
    height: 330px;
    background: #fff;
    border-radius: 5%;
}

.login-content {
    width: 400px;
    height: 250px;
    position: absolute;
    top: 25px;
    left: 25px;
}

.login-title {
    margin: 20px 0;
    text-align: center;
}
</style>