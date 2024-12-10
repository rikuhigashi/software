<template>

  <el-descriptions :title="title + user.name" direction="vertical" :column="2" :size="size" border>
    <el-descriptions-item label="账号">{{ user.name }}</el-descriptions-item>
    <el-descriptions-item label="电话">{{ user.phone }}</el-descriptions-item>
    <el-descriptions-item label="性别">
      <!-- {{ user.sex }} -->
      <el-tag :type="sex === 0 ? 'success' : 'primary'">
        {{ sex === 0 ? '男' : '女' }}
      </el-tag>
    </el-descriptions-item>

    <el-descriptions-item label="角色">
      <el-tag :type="roleId === 0 ? 'danger' : (roleId === 1 ? 'primary' : 'success')">
        {{ roleId === 0 ? '超级管理员' : (roleId === 1 ? '管理员' : '普通用户') }}
      </el-tag>
    </el-descriptions-item>
  </el-descriptions>


  <div style="margin-top: 20px;font-size: 35px;text-align: center;">
    当前时间是： {{ currentTime }} {{ currentWeekday }}
  </div>

</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref } from 'vue'
import { type ComponentSize } from 'element-plus'

const size = ref<ComponentSize>('default')

const userString = sessionStorage.getItem('CurUser')
const user = ref();
if (userString) {
  user.value = JSON.parse(userString)
}

const title = ref("欢迎你!")
const roleId = ref(user.value.roleId)
const sex = ref(user.value.sex)


const currentTime = ref<string>('');
const currentWeekday = ref<string>('');

// const test = ref<string>('');
// const test2 = ref<string>('');

const updateTime = () => {
  const now = new Date();
  currentTime.value = now.toLocaleString();  // 使用 toLocaleString() 简化日期时间格式化

  const weekdays = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
  currentWeekday.value = weekdays[now.getDay()];

  // test.value = now.toLocaleTimeString();  // 单独获取当前时间
  // test2.value = now.toLocaleDateString();  // 单独获取当前日期
};

onMounted(() => {
  updateTime();   // 添加这个是为了让刷新页面，或者进入页面时直接出现，否则需要等待一会才会出现。
  const interval = setInterval(updateTime, 1000);
  onUnmounted(() => clearInterval(interval));  // 清除定时器
});
</script>

<style scoped>
.el-descriptions {
  margin-top: 20px;
}

.el-descriptions .title {
  display: flex;
  justify-content: center;
  /* 水平居中 */
  align-items: center;
  /* 垂直居中 */
}
</style>
