<template>
  <div class="page">
    <van-nav-bar
      title="设置"
      left-text="返回"
      left-arrow
      @click-left="back"
    ></van-nav-bar>
    <div class="bg-fff" style="height:1.25rem">
      <div style="height: .2rem;"></div>
      <!--个人信息-->
      <van-field v-model="user.phoneNumber" placeholder="手机号"></van-field>
      <div>
        <div style="position: absolute;width: 70%;">
          <van-field v-model="msgCode" placeholder="验证码" @input="inputMsgCode"></van-field>
        </div>
        <div style="width: 100%;margin-left: 70%;">
          <van-button
            :disabled="!user.phoneNumber || user.phoneNumber.length != 11"
            style="width: 30%;"
            @click="getMsgCode"
            >立即获取</van-button
          >
        </div>
      </div>
      <van-field
        v-model="user.password"
        type="password"
        placeholder="密码"
      ></van-field>
      <van-field
        v-model="confirmPassword"
        type="password"
        placeholder="确认密码"
      ></van-field>
      <van-button
        class="next-step f16 flex fcc bg-fff fixed w100pc"
        @click="save"
        >提交</van-button
      >
    </div>
  </div>
</template>

<script>
import { Toast } from 'vant';
import { mapGetters } from 'vuex';

export default {
  data() {
    return {
      user: {
        id: 0,
        name: '',
        phoneNumber: '',
        password: '',
      },
      msgCode: '',
      confirmPassword: '',
      verifyPass: false
    };
  },

  computed: {
    ...mapGetters(['isLogin']),
  },

  mounted() {
    if (this.isLogin) {
      this.$router.push('/my/center');
      return;
    }
  },

  methods: {
    back() {
      history.back();
    },
    save() {
      if (!this.verifyPass) {
        Toast.fail("请先输入验证码验证手机号")
        return
      }
      if (!this.user.phoneNumber) {
        Toast.fail('手机号未填写');
        return;
      }
      if (this.user.phoneNumber.length != 11) {
        Toast.fail('手机号必须为11位');
        return;
      }
      if (!this.user.password) {
        Toast.fail("密码必填")
        return
      }
      if (this.user.password != this.confirmPassword) {
        Toast.fail('两次填写的密码不一致，请重新填写');
        return;
      }
      this.$axios.post('userLogin/forgetPassword', this.user).then((res) => {
        if (res.code == '000') {
          Toast.success('修改成功');
          this.$router.push({
            path: '/success',
            query: { phoneNumber: this.user.phoneNumber, from: 'changePassword' },
          });
        } else {
          Toast.fail(res.msg);
        }
      });
    },
    getMsgCode() {
      if (!this.user.phoneNumber) {
        Toast('手机号未填写');
        return;
      }
      if (this.user.phoneNumber.length != 11) {
        Toast('手机号必须为11位');
        return;
      }
      this.$axios.get(`/msg/code/${this.user.phoneNumber}`).then(res => {
        if (res.code == '000') {
          Toast.success("发送成功")
        }
      })
    },
    inputMsgCode(val) {
      if (val.length == 6) {
        this.$axios.get(`/msg/verify/${this.user.phoneNumber}/${this.msgCode}`).then(res => {
          if (res.code == '000') {
            Toast.success("验证成功")
            this.verifyPass = true
          } else {
            Toast.fail(res.msg)
          }
        })
      }
    }
  }
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
