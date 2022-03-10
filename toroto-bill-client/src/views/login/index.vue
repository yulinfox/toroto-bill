<template>
  <header class="login">
    <div style="padding: 30% 20%;font-size: .6rem;">
      <p>登录</p>
    </div>
    <div class>
      <!-- 手机号码 -->
      <div class="handset ml-20 mr-20 flex jc-sb mt-20 pb-5">
        <van-field
          v-model="username"
          class="f14"
          type="text"
          placeholder="请输入用户名/手机号/邮箱"
        />
      </div>
      <!-- 验证码 -->
      <div class="handset ml-20 mr-20 flex jc-sb mt-20 pb-5">
        <van-field
          type="password"
          v-model="password"
          class="f14"
          placeholder="请输入密码"
        />
      </div>

      <!-- 登录 -->
      <button @click="login" class="butt ml-20 mr-20 fff">登录</button>

      <div style="margin-top: .2rem; color: #5AACF5;">
        <p
          @click="signUp"
          isLink
          style="font-size: .1rem; margin-left: 25%; position: absolute;"
        >
          去注册
        </p>
        <p
          @click="forgetPassword"
          isLink
          style="font-size: .1rem; margin-left: 40%;"
        >
          忘记密码？
        </p>
      </div>
    </div>
  </header>
</template>

<script>
import { constants } from 'crypto';
import { Toast } from 'vant';

export default {
  data() {
    return {
      username: this.$route.query.phoneNumber
        ? this.$route.query.phoneNumber
        : '',
      password: '',
    };
  },
  methods: {
    login() {
      // 发送请求,用post方式
      let url = '/userLogin/login';
      // 请求参数
      let data = {
        username: this.username,
        password: this.password,
      };
      this.$axios
        .post(url, data)
        .then((res) => {
          if (res.code != '000') {
            Toast.fail(res.msg);
            return;
          }
          // 修改登陆状态
          this.$store.commit('updateLogin', true);
          // 把token存入store
          this.$store.commit('updateToken', res.token);
          // 把用户名存入store
          this.$store.commit('updateUser', res.data);
          this.$router.push('/my');
        })
        .catch((err) => {
          console.log('登陆失败');
        });
    },
    signUp() {
      this.$router.push('/signUp');
    },
    forgetPassword() {
      this.$router.push('/forgetPassword');
    },
  },
};
</script>

<style lang="less" scoped>
@import './login.less';
.logo {
  font-size: 100px !important;
  margin-bottom: 150px;
}
</style>
