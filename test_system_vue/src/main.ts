import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import axios from 'axios'


const app = createApp(App);

//设置全局url
axios.defaults.baseURL = 'http://localhost:8090';

//设置全局axios
app.config.globalProperties.$axios = axios;

const menuList = JSON.parse(sessionStorage.getItem('menu') || '[]');
if (menuList.length > 0) {
    store.commit('setMenu', menuList);
}

router.afterEach((to) => {
    store.commit('setActiveMenu', to.path);
});

app.use(store)
    .use(router)
    .use(ElementPlus, {
        locale: zhCn
    })
    // .use(ElementPlus,{size:'small'})  //设置全局size
    .use(store)
    .mount('#app')




