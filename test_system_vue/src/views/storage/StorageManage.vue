<template>
    <div>

        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="请输入仓库名" :suffix-icon="Search" style="width: 200px;"
                @keyup.enter="logoPost" />

            <el-button type="primary" style="margin-left: 5px;" @click="logoPost">查询</el-button>
            <el-button type="success" @click="resetParam">重置</el-button>
            <el-button type="primary" @click="add">新增</el-button>
        </div>

        <el-scrollbar>
            <el-table :data="tableData" :header-cell-style="{ background: '#9b999b', color: '#ffffff' }" border>
                <el-table-column prop="id" label="ID" width="60" />

                <el-table-column prop="name" label="仓库名" width="180" />

                <el-table-column prop="remark" label="备注" />


                <el-table-column prop="operate" label="操作">
                    <template #default="scope">

                        <el-button type="primary" size="small" @click="mod(scope.row)">
                            编辑
                        </el-button>

                        <el-popconfirm title="确定删除吗?" @confirm="del(scope.row.id)">
                            <template #reference>
                                <el-button type="danger" size="small">
                                    删除
                                </el-button>
                            </template>
                        </el-popconfirm>

                    </template>
                </el-table-column>

            </el-table>
        </el-scrollbar>

        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[5, 10, 20, 30]"
            :size="size" :disabled="disabled" layout="total, sizes, prev, pager, next, jumper" :total="total"
            @size-change="handleSizeChange" @current-change="handleCurrentChange">
        </el-pagination>

        <el-dialog v-model="centerDialogVisible" title="提示" width="500" align-center :before-close="handleClose" center>
            <el-form :model="form" ref="formRef" label-width="80px" :rules="rules" style="max-width: 600px;" center>


                <el-form-item label="仓库名" prop="name">
                    <el-col :span="20"><el-input v-model="form.name" /></el-col>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-col :span="20"><el-input v-model="form.remark" /></el-col>
                </el-form-item>


            </el-form>

            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="centerDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="save">
                        确认
                    </el-button>
                </div>
            </template>
        </el-dialog>


    </div>
</template>

<script lang="ts" setup>
import { nextTick, onBeforeMount, reactive, ref } from 'vue'
import axios from 'axios';
import { ComponentSize, ElMessage, ElMessageBox, FormInstance, FormRules } from 'element-plus';
import { Search } from '@element-plus/icons-vue'



const tableData = ref()

//axios获取到后端数据  跨域请求
//  axios.get('http://localhost:8090/user/list').then(res=>{   获取全部数据
//   axios.get('http://localhost:8090/user/list').then(res=>res.data).then(res=>{  //只获取data中的数据

let pageNum = ref(1)  //默认显示
let pageSize = ref(10)  //设置分页大小显示大小


function resetParam() {
    name.value = ''  //设置为空
    logoPost()  //点击时，回到第一页
}

//点击 新增 按钮弹出提示框
function add() {

    centerDialogVisible.value = true

    nextTick(() => {
        resetForm()  //点击新增按钮  清除原有的表单内容
    })

}

//点击编辑(修改)
function mod(row: any) {
    centerDialogVisible.value = true // 使其弹出对话框
    // console.log(row)
    nextTick(() => {
        form.id = row.id
        form.name = row.name
        form.remark = row.remark

    })
}

//点击删除
function del(id: any) {
    // console.log(id);
    axios.get('/storage/del?id=' + id).then(res => res.data).then(res => {
        // console.log(res)
        if (res.code === 200) {
            ElMessage({
                message: '数据删除成功',
                type: 'success',
            })
            centerDialogVisible.value = false   // 新增数据完毕后 关闭提示框
            logoPost()  // 添加完毕后直接刷新数据
        } else {
            ElMessage({
                message: '数据删除失败',
                type: 'warning',
            })
        }
    })
}


function logoPost() {
    axios.post('/storage/listPage', {
        pageSize: pageSize.value,   // 把前端设置的页面大小 传给后端
        pageNum: pageNum.value,
        param: {
            name: name.value,

        }

    }).then(res => res.data).then(res => {
        // console.log("post方式获取数据")
        // console.log(res)

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

onBeforeMount(() => {

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

const handleClose = (done: () => void) => {
    ElMessageBox.confirm('你确定要退出吗?')
        .then(() => {
            done()
        })
        .catch(() => {
            // catch error
        })
}


//表单
const formRef = ref<FormInstance | null>(null);
let form = reactive({
    id: '',
    name: '',
    remark: '',

})

function doSave() {
    axios.post('/storage/save', form).then(res => res.data).then(res => {
        // console.log(res)
        if (res.code === 200) {
            ElMessage({
                message: '数据添加成功',
                type: 'success',
            })
            centerDialogVisible.value = false   // 新增数据完毕后 关闭提示框
            logoPost()  // 添加完毕后直接刷新数据
        } else {
            ElMessage({
                message: '数据添加失败',
                type: 'warning',
            })
        }
    })
}

function doMod() {
    axios.post('/storage/update', form).then(res => res.data).then(res => {
        // console.log(res)
        if (res.code === 200) {
            ElMessage({
                message: '数据修改成功',
                type: 'success',
            })
            centerDialogVisible.value = false   // 新增数据完毕后 关闭提示框
            logoPost()  // 添加完毕后直接刷新数据
        } else {
            ElMessage({
                message: '数据修改失败',
                type: 'warning',
            })
        }
    })
}


function save() {
    if (!formRef.value) return;
    formRef.value.validate((valid) => {
        if (valid) {

            if (form.id) {
                doMod()
            } else {
                doSave()
            }

        } else {
            ElMessage({
                message: '数据填写错误！请重新填写',
                type: 'error',
                plain: true,
            })
        }
    })
}

//重置表单
const resetForm = () => {
    Object.assign(form, {
        no: '',
        password: '',
        name: '',
        age: '',
        sex: '1',
        phone: '',
    });
};

//输入规则
const rules = reactive<FormRules<typeof form>>({
    name: [
        { required: true, message: '请输入仓库名!', trigger: 'blur' },
    ],
})

</script>

<style>
.layout-container-demo .el-main {
    padding: 5px;
    height: 100%;
    width: 100%;
}
</style>