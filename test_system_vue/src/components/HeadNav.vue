<template>
    <el-header style="display: flex;line-height: 60px;border-bottom: rgb(168, 168, 168,0.3) 1px solid;">

        <el-icon :size="25" style="line-height: 60px;display: inline;" @click="collapse">
            <component :is="iconComponent" style="font-size: 25px;cursor: pointer;" />
        </el-icon>

        <span style="font-size: 32px;flex: 1;text-align: center;user-select: none;">欢迎来到仓库管理系统</span>

        <el-dropdown>

            <span style="display: inline;line-height: 60px;height: 100%;margin-left: 8px;margin-top: 1px;">
                {{ user.name }}
                <el-icon style="display: inline;line-height: 60px;height: 100%;margin-left: 8px;margin-top: 1px;">
                    <ArrowDown />
                </el-icon>
            </span>

            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item @click="toUser">个人中心</el-dropdown-item>
                    <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>

    </el-header>


    <el-dialog v-model="centerDialogVisible" title="Warning" width="500" center>
        <span>
            你确定要退出吗?
        </span>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="logOutCancel">取消</el-button>
                <el-button type="primary" @click="logOutConfirm">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>

</template>

<script lang="ts" setup>
import router from '@/router';
import { ArrowDown, Fold, Expand } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus';
import { computed, ref } from 'vue';

//点击个人中心时
const toUser = () => {
    // console.log("to_User")

    router.push("/HomeView")
}

const userString = sessionStorage.getItem('CurUser')
const user = ref();
if (userString) {
    user.value = JSON.parse(userString)
}

let centerDialogVisible = ref(false)
//点击退出登录时
const logout = () => {
    // console.log("logout")
    centerDialogVisible.value = true
}

function logOutConfirm() {
    router.push("/")
    sessionStorage.clear()
    ElMessage({
        message: '已退出登录',
        type: 'success',
    })
}

function logOutCancel() {
    centerDialogVisible.value = false
}

const emit = defineEmits(['doCollapse'])

const collapse = () => {
    emit('doCollapse')
}

const props = defineProps({
    icon: String
});

const iconComponent = computed(() => {
    if (props.icon === 'Fold') {
        return Fold;
    } else {
        return Expand;
    }
});
</script>
