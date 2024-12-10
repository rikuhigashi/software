import router from '@/router'
import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate';

function addNewRoute(menuList: any) {
  menuList.forEach((menu: { menuclick: any; menuname: any; menucomponent: string }) => {
    const route = {
      path: '/' + menu.menuclick,
      name: menu.menuname,
      meta: {
        title: menu.menuname
      },
      component: () => import(`../views/${menu.menucomponent}.vue`),

    }
    router.addRoute("index", route)
  })

}



export default createStore({
  state: {
    menu: [],
     activeMenu: ''
  },
  getters: {
    getMenu(state) {
      return state.menu
    },
    getActiveMenu(state) {
      return state.activeMenu;
    }
  },
  mutations: {
    setMenu(state, menuList) {
      state.menu = menuList
      sessionStorage.setItem('menu', JSON.stringify(menuList));
      //添加路由
      addNewRoute(menuList)
    },
    setActiveMenu(state, path) {
      state.activeMenu = path;
    }
  },
  actions: {
  },
  modules: {

  },

  // plugins: [createPersistedState()]
  plugins: [createPersistedState({
    storage: window.sessionStorage,
  })]
})
