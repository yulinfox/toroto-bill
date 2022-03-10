<template>
  <div class="center">
    <!-- 头部 -->
    <header>
      <div class="beijin"></div>
      <div class="flex aic users pixed" style="width:50%;">
        <img :src="require('../../../assets/img/personal/tx.jpg')" class="imege" />
        <router-link v-if="!isLogin" to="/login" class="f16 fff ml-20">立即登录</router-link>
        <span v-else class="f16 fff ml-20">欢迎 {{loginUser.name ? loginUser.name : loginUser.username}}</span>
      </div>
    </header>

    <!-- 主体 -->
    <main class="mt-10">
      <div class="bg-fff">
        <van-cell title="个人信息" isLink to="/login/info"></van-cell>
        <van-cell title="我的意向" isLink></van-cell>
        <van-cell title="我发布的信息" isLink></van-cell>
        <van-cell title="隐私设置" isLink to="/secrets"></van-cell>
        <van-cell title="设置" isLink to="/my/set"></van-cell>
      </div>
    </main>

    <!-- 退出登录 -->
    <div v-if="isLogin" class="logout f16 flex fcc bg-fff fixed w100pc" style="margin-top:10px; height:1.5rem;color:#ff5f16" @click="logout">
      <p class="blue">退出登录</p>
    </div>

    <Footer page="my" />
  </div>
</template>

<script>
import Footer from "@/components/Footer";
import { mapGetters } from "vuex";
export default {
  components: {
    Footer
  },

  computed: {
    ...mapGetters(["loginUser", "isLogin"])
  },

  mounted() {
    this.$axios.get("userLogin/loginStatus").then(res => {
      if (res.code == "-1") {
        this.$store.commit("updateLogin", false);
      }
    })
  },

  methods: {
    logout() {
      this.$axios.get("userLogin/logout").then(res => {
        if (res.code == "000") {
          this.$store.commit("updateLogin", false);
        }
      })
    },
  },
};
</script>

<style lang="less" scoped>
@import "./center.less";
</style>