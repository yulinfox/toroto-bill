<template>
  <div class="page">
    <van-nav-bar title="设置" left-text="返回" left-arrow @click-left="back"></van-nav-bar>

      <div class="bg-fff">
        <van-cell title="修改密码" isLink to="/login/changePassword"></van-cell>
        <van-cell title="更换手机号" isLink></van-cell>
      </div>

    <!-- 退出登录 -->
    <div class="logout f16 flex fcc bg-fff fixed w100pc" @click="logout">
      <p class="blue">退出登录</p>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    back() {
      history.back();
    },
    async logout() {
      let url = "/logout";
      let res = await this.$axios.post(url);
      // 修改登陆状态
      this.$store.commit("updateLogin", false);
      // 把用户名置空
      this.$store.commit("updateUsername", "");
      // 清除token
      this.$store.commit("updateToken", "");
      this.$router.push("/my");
    }
  }
};
</script>

<style lang="less" scoped>
.logout {
  bottom: 0;
  height: 50px;
  color: #ff5f16;
}
</style>

