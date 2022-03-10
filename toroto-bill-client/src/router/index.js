import Vue from "vue";
import Router from "vue-router";
import store from "../store/index";

Vue.use(Router);

const router = new Router({
  routes: [
    {
      path: "/",
      redirect: "/index"
    },
    {
      path: "/index",
      component: () => import("@/views/index/index"),
      meta: {
        title: "金享汇",
        // 缓存首页
        keepAlive: true
      }
    },
    {
      path: "/cart",
      meta: {
        title: "购物车"
      },
      component: () => import("@/views/cart/index")
    },
    {
      path: "/my",
      component: () => import("@/views/my/index"),
      redirect: "/my/center",
      children: [
        {
          path: "center",
          meta: {
            title: "个人中心"
          },
          component: () => import("@/views/my/children/center")
        },
        {
          path: "set",
          meta: {
            title: "设置"
          },
          component: () => import("@/views/my/children/set")
        }
      ]
    },
    {
      path: "/login",
      component: () => import("@/views/login/index"),
      meta: {
        title: "登陆"
      }
    },
    {
      path: "/login/changePassword",
      component: () => import("@/views/my/children/changePassword"),
      meta: {
        title: "修改密码"
      }
    },
    {
      path: "/login/info",
      component: () => import("@/views/my/children/info"),
      meta: {
        title: "个人信息"
      }
    },
    {
      path: "/signUp",
      component: () => import("@/views/my/SignUp"),
      meta: {
        title: "注册"
      }
    },
    {
      path: "/success",
      component: () => import("@/views/my/success"),
      meta: {
        title: "成功"
      }
    },
    {
      path: "/forgetPassword",
      component: () => import("@/views/my/ForgetPassword"),
      meta: {
        title: "忘记密码"
      }
    },
    {
      path: "/secrets",
      component: () => import("@/views/my/children/secrets"),
      meta: {
        title: "隐私设置"
      }
    },
    {
      path: "*",
      component: () => import("@/components/NotFound")
    }
  ]
});

router.beforeEach((to, from, next) => {
  let { title, needLogin } = to.meta;
  let { isLogin } = store.state;
  document.title = title;

  if (needLogin && !isLogin) {
    next({
      path: "/login"
    });
  } else {
    next();
  }
});

export default router;
