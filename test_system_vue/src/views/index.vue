<template>
    <el-container class="layout-container-demo">
        <el-aside :width="aside_width">
            <LeftNav :isCollapse="isCollapse"></LeftNav>
        </el-aside>

        <el-container>

            <el-header>
                <HeadNav @doCollapse="doCollapse" :icon="icon"></HeadNav>
            </el-header>

            <el-main >
                <!-- <MainNav ></MainNav> -->
                <router-view></router-view>
            </el-main>
        </el-container>


    </el-container>
</template>


<script lang="ts" setup>
import LeftNav from '@/components/LeftNav.vue';
import HeadNav from '@/components/HeadNav.vue';
// import MainNav from '@/views/MainView.vue';
import { ref } from 'vue';

//收缩
let isCollapse = ref(Boolean(false))
const aside_width = ref('200px')

//切换样式
let icon = ref('Fold')


function doCollapse() {
    isCollapse.value = !isCollapse.value

    if (!isCollapse.value) {
        aside_width.value = '200px'
        icon.value = 'Fold'
    } else {
        aside_width.value = '64px'
        icon.value = 'Expand'
    }
}
</script>


<style>

.el-aside {
  height: 100vh;
}


</style>