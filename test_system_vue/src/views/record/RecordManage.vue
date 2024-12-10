<template>
    <div>

        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="请输入物品名" :suffix-icon="Search" style="width: 200px;"
                @keyup.enter="logoPost" />
            <el-select v-model="storage" placeholder="请选择仓库" style="width: 240px;margin-left: 10px;">
                <el-option v-for="item in storageData" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
            <el-select v-model="goodstype" placeholder="请选择分类" style="width: 240px;margin-left: 10px;">
                <el-option v-for="item in goodstypeData" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>

            <el-button type="primary" style="margin-left: 5px;" @click="logoPost">查询</el-button>
            <el-button type="success" @click="resetParam">重置</el-button>

        </div>

        <el-scrollbar>
            <el-table :data="tableData" :header-cell-style="{ background: '#9b999b', color: '#ffffff' }" border>
                <el-table-column prop="id" label="ID" width="60" />

                <el-table-column prop="goodsname" label="物品名" width="180" />
                <el-table-column prop="storagename" label="仓库" width="180" />
                <el-table-column prop="goodstypename" label="分类" width="180" />
                <el-table-column prop="adminname" label="操作人" width="180" />
                <el-table-column prop="username" label="申请人" width="180" />
                <el-table-column prop="count" label="数量" width="180" />
                <el-table-column prop="createtime" label="操作时间" width="180" />
                <el-table-column prop="remark" label="备注" />

            </el-table>
        </el-scrollbar>

        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[5, 10, 20, 30]"
            :size="size" :disabled="disabled" layout="total, sizes, prev, pager, next, jumper" :total="total"
            @size-change="handleSizeChange" @current-change="handleCurrentChange">
        </el-pagination>

    </div>
</template>

<script lang="ts" setup>
import { nextTick, onBeforeMount, reactive, ref } from 'vue'
import axios from 'axios';
import { ComponentSize, ElMessage, } from 'element-plus';
import { Search } from '@element-plus/icons-vue'



const tableData = ref()

//axios获取到后端数据  跨域请求
//  axios.get('http://localhost:8090/user/list').then(res=>{   获取全部数据
//   axios.get('http://localhost:8090/user/list').then(res=>res.data).then(res=>{  //只获取data中的数据

let pageNum = ref(1)  //默认显示
let pageSize = ref(10)  //设置分页大小显示大小
let storageData = ref()
let goodstypeData = ref()
let storage = ref('')
let goodstype = ref('')

//获取sessionStorage的userid
const userString = sessionStorage.getItem('CurUser')
const user = ref();
if (userString) {
    user.value = JSON.parse(userString)
}

function resetParam() {
    name.value = ''  //设置为空
    storage.value = ''
    goodstype.value = ''
    logoPost()  //点击时，回到第一页
}


function logoPost() {
    axios.post('/record/listPage', {
        pageSize: pageSize.value,   // 把前端设置的页面大小 传给后端
        pageNum: pageNum.value,
        param: {
            name: name.value,
            goodstype: goodstype.value + '',
            storage: storage.value + '',
            roleId: user.value.roleId + '',
            userId: user.value.id + ''
        }

    }).then(res => res.data).then(res => {
        console.log(res)

        if (res.code === 200) {
            tableData.value = res.data
            total.value = res.total
        } else {
            ElMessage({
                message: '获取数据失败',
                type: 'error',
                plain: true,
            })
        }
    })
}

function loadStorage() {
    axios.get('/storage/list').then(res => res.data).then(res => {
        // console.log(res)

        if (res.code === 200) {
            storageData.value = res.data
        } else {
            ElMessage({
                message: '获取数据失败',
                type: 'error',
                plain: true,
            })
        }
    })
}

function loadGoodstype() {
    axios.get('/goodstype/list').then(res => res.data).then(res => {
        // console.log(res)

        if (res.code === 200) {
            goodstypeData.value = res.data
        } else {
            ElMessage({
                message: '获取数据失败',
                type: 'error',
                plain: true,
            })
        }
    })
}

onBeforeMount(() => {
    loadStorage()
    loadGoodstype()
    logoPost();
})


//分页
const total = ref(0)
const size = ref<ComponentSize>('default')
const disabled = ref(false)

const handleSizeChange = (val: number) => {
    // console.log(`${val} items per page`)
    pageNum.value = 1  // 设置时，默认跳转到第一页
    pageSize.value = val //把val给切换显示大小
    logoPost();
}
const handleCurrentChange = (val: number) => {
    // console.log(`current page: ${val}`)

    logoPost();
}

//输入框
let name = ref('')

//对话框
let centerDialogVisible = ref(false)

//表单

let form = reactive({
    id: '',
    name: '',
    storage: '',
    goodstype: '',
    count: '',
    remark: '',
})


</script>

<style>
.layout-container-demo .el-main {
    padding: 5px;
    height: 100%;
    width: 100%;
}
</style>