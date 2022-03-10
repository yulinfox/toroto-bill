<template>
  <div class="page">
    <van-nav-bar
      title="隐私设置"
      left-text="返回"
      left-arrow
      @click-left="back"
    ></van-nav-bar>
    <div class="bg-fff" style="height:1.25rem">
      <div style="height: .2rem;"></div>
      <p
        style="font-size: .1rem; color: #F1C593; padding: .2rem .5rem .1rem .5rem; background-color: white;"
      >
        *开启后，其他会员将可以申请查看
      </p>
      <p
        style="font-size: .1rem; color: #F1C593; padding: .1rem .5rem .2rem .5rem; background-color: white;"
      >
        *为了保障您的信息安全，系统已经进行加密处理，没有您的允许，绝不对外公开
      </p>
      <van-cell title="手机号">
        <van-switch
          size=".5rem"
          v-model="showPhone"
          @change="save"
        ></van-switch>
      </van-cell>
      <van-cell title="微信">
        <van-switch
          size=".5rem"
          v-model="showWechat"
          @change="save"
        ></van-switch>
      </van-cell>
      <van-cell title="邮箱">
        <van-switch
          size=".5rem"
          v-model="showEmail"
          @change="save"
        ></van-switch>
      </van-cell>
    </div>
  </div>
</template>

<script>
import { Toast, Switch } from 'vant';
import { mapGetters } from 'vuex';

export default {
  components: {
    vanSwitch: Switch,
  },

  data() {
    return {
      user: {
        id: 0,
        showWechat: 0,
        showPhone: 0,
        showEmail: 0,
      },
      msgCode: '',
      confirmPassword: '',
      showPhone: false,
      showEmail: false,
      showWechat: false,
    };
  },

  computed: {
    ...mapGetters(['loginUser', 'isLogin']),
  },

  mounted() {
    if (!this.isLogin) {
      this.$router.push('/login');
      return;
    }
    if (this.loginUser) {
      this.user = JSON.parse(JSON.stringify(this.loginUser));
      this.showPhone =
        this.user.showPhone && this.user.showPhone == 1 ? true : false;
      this.showEmail =
        this.user.showEmail && this.user.showEmail == 1 ? true : false;
      this.showWechat =
        this.user.showWechat && this.user.showWechat == 1 ? true : false;
    }
  },

  methods: {
    back() {
      history.back();
    },
    save() {
      let updateUser = {
        id: this.user.id,
        showPhone: this.showPhone ? 1 : 0,
        showEmail: this.showEmail ? 1 : 0,
        showWechat: this.showWechat ? 1 : 0,
      };
      this.$axios.post('/userLogin/user', updateUser).then((res) => {
        if (res.code != '000') {
          Toast.fail('处理失败，请重试');
          return;
        }
      });
    },
    onShowPhoneChange(event) {
      alert(event.detail);
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
