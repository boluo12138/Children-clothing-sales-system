import router from "./router";
import store from "./store";
import { Message } from "element-ui";
import NProgress from "nprogress"; // progress bar
import "nprogress/nprogress.css"; // progress bar style
import { getToken } from "@/utils/auth"; // get token from cookie
import getPageTitle from "@/utils/get-page-title";

import Layout from "@/layout";

NProgress.configure({ showSpinner: false }); // NProgress Configuration
//白名单
const whiteList = ["/","/retrieve","/login", "/register","/search","/detail"]; // no redirect whitelist

router.beforeEach(async (to, from, next) => {
  // start progress bar
  NProgress.start();

  // set page title
  document.title = getPageTitle(to.meta.title);

  // determine whether the user has logged in
  const hasToken = getToken();

  if (hasToken) {
    if(to.name === 'login'){
    // if (to.path === "/login") {
      // if is logged in, redirect to the home page
      // next({ path: "/" });
      next({ name: "Dashboard" });
      NProgress.done();
    } else {
      const hasGetUserInfo = store.getters.name;
      if (hasGetUserInfo) {
        next();
      } else {
        try {
          // get user info
          const res = await store.dispatch("user/getInfo");
          // 将user权限设置到本地缓存中

          localStorage.setItem("user",JSON.stringify(res))
          // 路由转换
          let myRoutes = myFilterAsyncRoutes(store.getters.menuList);
          // console.log("myRoutes:")
          // console.log(myRoutes)
          // 404
          myRoutes.push({
            path: "*",
            redirect: "/404",
            hidden: true,
          });
          // 动态添加路由
          router.addRoutes(myRoutes);
          // 存至全局变量
          global.myRoutes = myRoutes;

          next({ ...to, replace: true }); // 防止刷新后页面空白
          //next()
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch("user/resetToken");
          Message.error(error || "Has Error");
          // next(`/login?redirect=${to.path}`);
          next(`/login?redirect=${to.name}`);
          NProgress.done();
        }
      }
    }
  } else {
    /* has no token*/

    // if (whiteList.indexOf(to.name) !== -1) {
    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next();
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      // next(`/login?redirect=${to.path}`);
      next(`/login?redirect=${to.name}`);
      NProgress.done();
    }
  }
});

router.afterEach(() => {
  // finish progress bar
  NProgress.done();
});

function myFilterAsyncRoutes(menuList) {
  menuList.filter((menu) => {
    // menu.name =store.getters.username + "_" + menu.name;
    menu.id = store.getters.id
    if (menu.component === "Layout") {
      // console.log("username："+store.getters.username)

      menu.component = Layout;
      console.log(menu.component);
    } else {
      menu.component = require(`@/views/${menu.component}.vue`).default;
    }
    // 递归处理子菜单
    if (menu.children && menu.children.length) {
      menu.children = myFilterAsyncRoutes(menu.children);
    }
    return true;
  });
  // console.log("menuList: ");
  // console.log(menuList);
  return menuList;
}
