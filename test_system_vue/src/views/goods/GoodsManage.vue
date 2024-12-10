<template>
    <div>
        <!-- 按钮、搜索框 -->
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
            <el-button type="primary" @click="add" v-if="user.roleId != 2">新增</el-button>
            <el-button type="primary" @click="inGoods" v-if="user.roleId != 2">入库</el-button>
            <el-button type="primary" @click="outGoods" v-if="user.roleId != 2">出库</el-button>
        </div>

        <!-- 主体 -->
        <el-scrollbar>
            <el-table :data="tableData" :header-cell-style="{ background: '#9b999b', color: '#ffffff' }" border
                highlight-current-row @current-change="selectCurrentChange">
                <el-table-column prop="id" label="ID" width="60" />

                <el-table-column prop="name" label="物品名" width="180" />
                <el-table-column prop="storage" label="仓库" width="180" :formatter="formatterStorage" />
                <el-table-column prop="goodstype" label="分类" width="180" :formatter="formatterGoodstypeData" />
                <el-table-column prop="count" label="数量" width="180" />

                <el-table-column prop="remark" label="备注" />


                <el-table-column prop="operate" label="操作" v-if="user.roleId != 2">
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

        <!-- 分页 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[5, 10, 20, 30]"
            :size="size" :disabled="disabled" layout="total, sizes, prev, pager, next, jumper" :total="total"
            @size-change="handleSizeChange" @current-change="handleCurrentChange">
        </el-pagination>

        <!-- 点击新增弹出框 -->
        <el-dialog v-model="centerDialogVisible" title="提示" width="500" align-center :before-close="handleClose" center>
            <el-form :model="form" ref="formRef" label-width="80px" :rules="rules" style="max-width: 600px;" center>
                <el-form-item label="物品名" prop="name">
                    <el-col :span="20"><el-input v-model="form.name" /></el-col>
                </el-form-item>
                <el-form-item label="仓库" prop="storage">
                    <el-col :span="20">
                        <el-select v-model="form.storage" placeholder="请选择仓库" style="width: 240px;margin-left: 10px;">
                            <el-option v-for="item in storageData" :key="item.id" :label="item.name" :value="item.id" />
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="分类" prop="goodstype">
                    <el-col :span="20">
                        <el-select v-model="form.goodstype" placeholder="请选择分类" style="width: 240px;margin-left: 10px;">
                            <el-option v-for="item in goodstypeData" :key="item.id" :label="item.name"
                                :value="item.id" />
                        </el-select>

                    </el-col>
                </el-form-item>
                <el-form-item label="数量" prop="count">
                    <el-col :span="20"><el-input v-model="form.count" /></el-col>
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

        <!-- 点击入库弹出框 -->
        <el-dialog v-model="inDialogVisible" title="出入库" width="500" align-center :before-close="handleClose" center>

            <el-dialog v-model="innerVisible" width="70%" title="用户选择" append-to-body align-center center>

                <SelectUser @doSelectUser="doSelectUser"></SelectUser>
                <template #footer>
                    <div class="dialog-footer">
                        <el-button @click="innerVisible = false">取消</el-button>
                        <el-button type="primary" @click="confirmUser">
                            确认
                        </el-button>
                    </div>
                </template>
            </el-dialog>


            <el-form :model="form1" ref="formRef1" label-width="80px" :rules="rules1" style="max-width: 600px;" center>
                <el-form-item label="物品名">
                    <el-col :span="20"><el-input v-model="form1.goodsname" readonly /></el-col>
                </el-form-item>
                <el-form-item label="申请人">
                    <el-col :span="20"><el-input v-model="form1.username" readonly @click="selectUser" /></el-col>
                </el-form-item>

                <el-form-item label="数量" prop="count">
                    <el-col :span="20"><el-input v-model="form1.count" /></el-col>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-col :span="20"><el-input v-model="form1.remark" /></el-col>
                </el-form-item>
            </el-form>

            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="inDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="doInGoods">
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
import SelectUser from '../user/SelectUser.vue';

const tableData = ref()

let pageNum = ref(1)  //默认显示
let pageSize = ref(10)  //设置分页大小显示大小
let storageData = ref()
let goodstypeData = ref()
let storage = ref('')
let goodstype = ref('')
let currentRow = ref({ id: '', name: '' })
let tempUser = ref()

const innerVisible = ref(false)

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
        form.storage = row.storage
        form.goodstype = row.goodstype
        form.count = row.count
        form.remark = row.remark
    })
}

//点击删除
function del(id: any) {
    // console.log(id);
    axios.get('/goods/del?id=' + id).then(res => res.data).then(res => {
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

//点击入库
function inGoods() {
    if (!currentRow.value || !currentRow.value.id) {
        alert('请选择一个记录');
        return;
    }
    inDialogVisible.value = true
    form1.goodsname = currentRow.value.name
    form1.goods = currentRow.value.id
    form1.adminid = user.value.id
    form1.action = '1'



    nextTick(() => {
        resetinForm()
    })
}
//点击出库
function outGoods() {
    if (!currentRow.value || !currentRow.value.id) {
        alert('请选择一个记录');
        return;
    }
    inDialogVisible.value = true
    form1.goodsname = currentRow.value.name
    form1.goods = currentRow.value.id
    form1.adminid = user.value.id
    form1.action = '2'


    nextTick(() => {
        resetinForm()
    })
}

//选择申请人

function selectUser() {
    innerVisible.value = true
}
function confirmUser() {
    form1.username = tempUser.value.name
    form1.userid = tempUser.value.id
    innerVisible.value = false
}
function doSelectUser(val: any) {
    // console.log(val);
    tempUser.value = val
}

function logoPost() {
    axios.post('/goods/listPage', {
        pageSize: pageSize.value,   // 把前端设置的页面大小 传给后端
        pageNum: pageNum.value,
        param: {
            name: name.value,
            goodstype: goodstype.value + '',
            storage: storage.value + ''
        }
    }).then(res => res.data).then(res => {
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


//获取storage数据
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

//获取loadGood数据
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

const selectCurrentChange = (val: any) => {
    currentRow.value = val
}

//输入框
let name = ref('')

//对话框
let centerDialogVisible = ref(false)
let inDialogVisible = ref(false)

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
    storage: '',
    goodstype: '',
    count: '',
    remark: '',
})
let form1 = reactive({
    id: '',
    goods: '',
    goodsname: '',
    count: '',
    adminid: '',
    username: '',
    userid: '',
    remark: '',
    action: '1',
})

function doSave() {
    axios.post('/goods/save', form).then(res => res.data).then(res => {
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

function doInGoods() {
    axios.post('/record/save', form1).then(res => res.data).then(res => {
        // console.log(res)
        if (res.code === 200) {
            ElMessage({
                message: '数据添加成功',
                type: 'success',
            })
            inDialogVisible.value = false   // 新增数据完毕后 关闭提示框
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
    axios.post('/goods/update', form).then(res => res.data).then(res => {
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
        id: '',
        name: '',
        storage: '',
        goodstype: '',
        count: '',
        remark: '',
    });
};

const resetinForm = () => {
    Object.assign(form1, {
        id: '',
        // goods: '',
        count: '',
        // adminid: '',
        // userid: '',
        remark: '',
        username: ''
    });
};



const checkCount = (rule: any, value: any, callback: any) => {
    if (value > 9999) {
        callback(new Error('数量输入过大'))
    } else {
        callback()
    }
}

//输入规则
const rules = reactive<FormRules<typeof form>>({
    name: [
        { required: true, message: '请输入物品名!', trigger: 'blur' },
    ],
    storage: [
        { required: true, message: '请选择仓库!', trigger: 'blur' },
    ],
    goodstype: [
        { required: true, message: '请选择分类!', trigger: 'blur' },
    ],
    count: [
        { required: true, message: '请输入数量!', trigger: 'blur' },
        { pattern: /^([1-9][0-9]*){1,4}$/, message: '必须为正整数字', trigger: 'blur' },
        { validator: checkCount, trigger: 'blur' }
    ]
})

const rules1 = reactive<FormRules<typeof form>>({
    name: [
        { required: true, message: '请输入物品名!', trigger: 'blur' },
    ],
    storage: [
        { required: true, message: '请选择仓库!', trigger: 'blur' },
    ],
    goodstype: [
        { required: true, message: '请选择分类!', trigger: 'blur' },
    ],
    count: [
        { required: true, message: '请输入数量!', trigger: 'blur' },
        { pattern: /^([1-9][0-9]*){1,4}$/, message: '必须为正整数字', trigger: 'blur' },
        { validator: checkCount, trigger: 'blur' }
    ]
})



function formatterStorage(row: any) {
    let temp = storageData.value.find((item: { id: any; }) => {
        return item.id == row.storage
    })
    return temp && temp.name
}
function formatterGoodstypeData(row: any) {
    let temp = goodstypeData.value.find((item: { id: any; }) => {
        return item.id == row.goodstype
    })
    return temp && temp.name
}





</script>

<style>
.layout-container-demo .el-main {
    padding: 5px;
    height: 100%;
    width: 100%;
}
</style>