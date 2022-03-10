<template>
  <div class="page">
    <van-nav-bar
      title="设置"
      left-text="返回"
      left-arrow
      @click-left="back"
    ></van-nav-bar>

    <div class="bg-fff" style="height:1.25rem">
      <van-field
        v-model="password"
        type="password"
        placeholder="请输入原密码进行验证"
      ></van-field>
    </div>
    <van-field
      v-model="newPassword"
      type="password"
      placeholder="密码"
    ></van-field>
    <van-field
      v-model="confirmPassword"
      type="password"
      placeholder="确认密码"
    ></van-field>
    <van-button style="width: 100%;" @click="modify">提交</van-button>
  </div>
</template>

<script>
import { Toast } from 'vant';

export default {
  data() {
    return {
      password: '',
      newPassword: '',
      confirmPassword: '',
    };
  },
  methods: {
    back() {
      history.back();
    },
    modify() {
      if (!this.password) {
        Toast.fail('原密码必填');
        return;
      }
      if (!this.newPassword) {
        Toast.fail('密码必填');
        return;
      }
      if (this.newPassword != this.confirmPassword) {
        Toast.fail('两次填写的密码不一致，请重新填写');
        return;
      }
      this.$axios
        .post('/userLogin/change/password', {
          password: this.password,
          newPassword: this.newPassword,
        })
        .then((res) => {
          if (res.code == '000') {
            Toast.success('修改成功');
            this.$axios.get('userLogin/logout').then((res) => {
              if (res.code == '000') {
                this.$store.commit('updateLogin', false);
                this.$router.push("/login")
              }
            });
          } else {
            Toast.fail(res.msg);
          }
        });
    },
  },
};
</script>

<style lang="less" scoped>
.next-step {
  top: 2.5rem;
  height: 1.25rem;
}
</style>
