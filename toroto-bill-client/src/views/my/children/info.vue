<template>
  <div class="page">
    <van-nav-bar
      title="完善信息"
      left-text="返回"
      left-arrow
      @click-left="back"
    ></van-nav-bar>

    <div class="bg-fff" style="height:1.25rem">
      <!--个人信息-->
      <p style="font-size: .5rem; padding: .2rem; text-align: center;">
        完善个人信息，更好帮您精准匹配资源
      </p>
      <p
        style="font-size: .1rem; color: #F1C593; padding: .2rem .5rem .2rem .5rem; background-color: white;"
      >
        *为了保障您的信息安全，系统已进行加密处理，您可以在我的-隐私设置中选择是否公开
      </p>
      <van-icon name="manager-o" style="margin: .2rem;">个人信息</van-icon>
      <van-field v-model="user.name" placeholder="真实姓名"></van-field>
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
      <van-field v-model="user.wechat" placeholder="微信号"></van-field>
      <van-field
        v-model="user.wechatGroup"
        placeholder="所在线下微信群"
      ></van-field>
      <van-field v-model="user.email" placeholder="邮箱"></van-field>
      <!--公司信息-->
      <van-icon name="friends-o" style="margin: .2rem;">公司信息</van-icon>
      <van-field
        v-model="user.companyName"
        placeholder="所在公司名称"
      ></van-field>
      <van-field
        v-model="user.companyShortName"
        placeholder="所在公司简称"
      ></van-field>
      <van-field
        v-model="user.companyProvince"
        placeholder="所在公司省份"
      ></van-field>
      <van-field
        v-model="user.companyAddress"
        placeholder="所在公司地址"
      ></van-field>
      <van-field
        v-model="user.companyType"
        clickable
        @click="showCompanyTypePicker = true"
        readonly
        placeholder="企业性质"
      ></van-field>
      <van-field
        v-model="user.companyScale"
        clickable
        readonly
        @click="showCompanyScalePicker = true"
        placeholder="企业规模"
      ></van-field>
      <van-field v-model="user.post" placeholder="公司职位或部门"></van-field>

      <van-button
        class="next-step f16 flex fcc bg-fff fixed w100pc"
        @click="save"
        >完成</van-button
      >
      <p
        style="font-size: .1rem; color: #A6A19C; padding: .2rem; text-align: center;"
      >
        如需变更，可在我的-个人信息中修改
      </p>
    </div>

    <van-popup v-model="showCompanyTypePicker" round position="bottom">
      <van-picker
        title="企业性质"
        show-toolbar
        :columns="companyTypeOpt"
        @cancel="showCompanyTypePicker = false"
        @confirm="changeCompanyType"
      />
    </van-popup>

    <van-popup v-model="showCompanyScalePicker" round position="bottom">
      <van-picker
        title="企业规模"
        show-toolbar
        :columns="companyScaleOpt"
        @cancel="showCompanyScalePicker = false"
        @confirm="changeCompanyScale"
      />
    </van-popup>
  </div>
</template>

<script>
import { Toast, Picker } from 'vant';
import { mapGetters } from 'vuex';

export default {
  components: {
    vanPicker: Picker,
  },
  data() {
    this.companyTypeOpt = [
      '国企',
      '外商独资',
      '代表处',
      '合资',
      '民营',
      '股份制企业',
      '上市公司',
      '银行',
      '其他',
    ];
    this.companyScaleOpt = ['0-49人', '50-99人', '100-499人', '500人以上'];
    return {
      showCompanyTypePicker: false,
      showCompanyScalePicker: false,
      user: {
        id: 0,
        name: '',
        phoneNumber: '',
        email: '',
        wechat: '',
        wechatGroup: '',
        companyName: '',
        companyShortName: '',
        companyProvince: '',
        companyCity: '',
        companyAddress: '',
        companyType: '',
        companyScale: '',
        post: '',
      },
      msgCode: '',
      verifyPass: false
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
      this.$axios.post('userLogin/user', this.user).then((res) => {
        if (res.code == '000') {
          Toast.success({
            message: '保存成功',
            duration: 500,
            onClose: () => {
              this.$store.commit('updateUser', this.user);
              this.$router.push('/my/center');
            },
          });
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
      // todo
      this.$axios.get(`/msg/code/${this.user.phoneNumber}`).then(res => {
        if (res.code == '000') {
          Toast.success("发送成功")
        }
      })
    },
    changeCompanyType(val, index) {
      this.user.companyType = val;
      this.showCompanyTypePicker = false;
    },
    changeCompanyScale(val, index) {
      this.user.companyScale = val;
      this.showCompanyScalePicker = false;
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
