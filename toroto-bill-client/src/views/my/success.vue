<template>
  <div class="page">
    <van-nav-bar
      title="操作成功"
      left-text="返回"
      left-arrow
      @click-left="back"
    ></van-nav-bar>

    <div v-if="showText" style="padding: 30% 20%;font-size: .42rem; background-color: white;">
      <p>加入金享汇，享受更多优质服务</p>
    </div>

    <div class="bg-fff">
      <p style="text-align: center; font-size: .4rem;">{{tipInfo}}</p>
      <p style="text-align: center; font-size: .2rem; color: #5AACF5; margin-top: .5rem; padding-bottom: .5rem;">{{ time }}秒后跳转到登陆页</p>
    </div>
  </div>
</template>

<script>
import { Toast } from 'vant';
import { mapGetters } from 'vuex';

export default {
  data() {
    return {
      time: 5,
      showText: this.$route.query.from == "changePassword" ? false : true,
      tipInfo: this.$route.query.from == "changePassword" ? "密码修改成功，请重新登陆！" : "恭喜您，注册成功！"
    };
  },

  computed: {
    ...mapGetters(['loginUser', 'isLogin']),
  },

  mounted() {
    if (this.loginUser) {
      this.user = JSON.parse(JSON.stringify(this.loginUser));
    }
    this.time = 5;
    let clock = window.setInterval(() => {
      this.time--;
      if (this.time < 0) {
        //当倒计时小于0时清除定时器
        window.clearInterval(clock);
        this.$router.push({
            path: "/login",
            query: this.query
        })
      }
    }, 1000);
  },

  methods: {
    back() {
      history.back();
    },
  },
};
</script>

<style lang="less" scoped>
.next-step {
  height: 1.2rem;
  margin-left: 0.1rem;
  margin-right: 0.1rem;
}
.van-field {
  margin-left: 0.1rem;
  margin-right: 0.1rem;
}
</style>
