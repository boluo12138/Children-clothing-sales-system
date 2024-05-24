import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

/* Layout */
import Layout from "@/layout";

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: "/",
    component: () => import("@/views/front/Home.vue"),
    hidden: true,
    // children:[
    //   { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('@/views/front/Home') },
    //   { path: 'cart', name: 'Cart', meta: { name: '我的购物车' }, component: () => import('@/views/front/Cart')}
    // ]
  },
  {
    path: "/manager",
    name:"manager",
    component: Layout,
    redirect: "/dashboard",
    children: [
      {
        path: "/dashboard",
        name: "Dashboard",
        component: () => import("@/views/manage/Dashboard"),
        meta: { title: "首页", icon: "dashboard", affix: true },
      },
      // {
      //   path: "/changeuserinfo",
      //   name: "changeuserinfo",
      //   meta:{title : "修改个人信息"},
      //   component: () => import("@/views/info/changeuserinfo"),
      // },
      // {
      //   path: "/changepassword",
      //   name: "changepassword",
      //   meta:{title : "修改密码"},
      //   component: () => import("@/views/info/changepassword"),
      // },

    ],
  },
  { path: '/cart', name: 'cart', meta: { name: '我的购物车' }, component: () => import('@/views/front/Cart') ,hidden: true},
  { path: '/detail', name: 'detail', meta: { name: '商品详情' }, component: () => import('@/views/front/Detail') ,hidden: true},
  { path: '/business', name: 'Business', meta: { name: '商家店铺' }, component: () => import('@/views/front/Business'),hidden: true },
  { path: '/search', name: 'Search', meta: { name: '搜索页面' }, component: () => import('../views/front/Search') ,hidden: true},
  { path: '/person', name: 'Person', meta: { name: '个人信息' }, component: () => import('../views/front/Person') ,hidden: true},
  { path: '/address', name: 'Address', meta: { name: '我的地址' }, component: () => import('../views/front/Address'),hidden: true},
  { path: '/orders', name: 'Orders', meta: { name: '我的订单' }, component: () => import('../views/front/Orders'),hidden: true},
  { path: '/collect', name: 'Collect', meta: { name: '我的收藏' }, component: () => import('../views/front/Collect'),hidden: true},
  { path: '/type', name: 'Type', meta: { name: '分类商品' }, component: () => import('../views/front/Type') ,hidden: true},
  { path: '/pay', name: 'Pay', meta: { name: '支付页面' }, component: () => import('../views/front/Pay') ,hidden: true},
  { path: "/login",name: "login",component: () => import("@/views/front/Login"),hidden: true,},
  { path: "/register",name:"register",component: () => import("@/views/front/Register"),hidden: true,},
  { path: "/retrieve",name:"Retrieve",component: () => import("@/views/front/Retrieve"),hidden: true,},

  {path: "/404",name: "404",component: () => import("@/views/404"),hidden: true,},

];


const createRouter = () =>
  new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes,

  });

const router = createRouter();

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher; // reset router
}

export default router;
