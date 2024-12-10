<template>
    <el-aside style="height: 100%;">
        <el-menu active-text-color="#ffd04b" background-color="#545c64" text-color="#fff" :default-active="activeMenu"
            style="height: 100%;" :collapse="isCollapse" :collapse-transition="false" router>


            <el-menu-item index="HomeView">
                <el-icon>
                    <HomeFilled />
                </el-icon>
                <template #title>首页</template>
            </el-menu-item>

            <el-menu-item :index="'/' + item.menuclick" v-for="(item, i) in menu" :key="i"> 
                <el-icon>
                    <component :is=iconMap[item.menuicon] />
                </el-icon>
                <template #title>{{ item.menuname }}</template>
            </el-menu-item>

        </el-menu>
    </el-aside>
</template>

<style scoped></style>

<script lang="ts" setup>
import { HomeFilled, Avatar, UserFilled, OfficeBuilding, Menu, Management, Tickets } from '@element-plus/icons-vue'
import { computed, ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import { useStore } from 'vuex';

const route = useRoute();
const currentRoute = ref(route.path);  //使用 useRoute 钩子 来获取当前路由信息

const store = useStore();
const menu = computed(() => store.state.menu);
const activeMenu = computed(() => store.state.activeMenu);

const iconMap: Record<string, any> = {
    HomeFilled,
    Avatar,
    UserFilled,
    OfficeBuilding,
    Menu,
    Management,
    Tickets
};

defineProps({
    isCollapse: Boolean
})

// 监听路由变化并更新当前激活的菜单项
watch(route, (newRoute) => {
    currentRoute.value = newRoute.path;
    store.commit('setActiveMenu', newRoute.path);
});
</script>
