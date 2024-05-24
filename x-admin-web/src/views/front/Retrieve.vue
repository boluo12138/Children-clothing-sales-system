<template>
  <div class="resetPassword">
    <div class="container">
      <el-steps
        :active="active"
        :space="200"
        finish-status="success"
        align-center
      >
        <el-step title="验证用户名和邮箱" icon="el-icon-edit"></el-step>
        <el-step title="输入验证码" icon="el-icon-s-promotion"></el-step>
        <el-step title="设置新密码" icon="el-icon-key"></el-step>
      </el-steps>
      <div v-if="active === 0" class="common_div">
        <el-form
          :model="Form"
          class="user-container"
          label-position="left"
          label-width="60px"
          size="medium"
        >
          <el-form-item style="float: right; width: 80%" label="用户名">
            <el-input
              type="text"
              v-model="Form.username"
              autofocus
              ref="username"
              auto-complete="off"
              placeholder="请输入要找回密码的用户名"
              prefix-icon="el-icon-user-solid"
              spellcheck="false"
            >
            </el-input>
          </el-form-item>
          <el-form-item style="float: right; width: 80%" label="邮箱号">
            <el-input
              type="text"
              v-model="Form.email"
              autofocus
              ref="email"
              auto-complete="off"
              placeholder="请输入用来找回密码的邮箱"
              prefix-icon="el-icon-message"
              spellcheck="false"
              :disabled="!isUsername"
            >
            </el-input>
          </el-form-item>
        </el-form>
      </div>
      <div v-if="active === 1" class="common_div">
        <el-form
          :model="codeForm"
          class="user-container"
          label-position="left"
          label-width="60px"
          size="medium"
        >
          <el-form-item style="float: right; width: 80%" label="验证码">
            <el-input
              type="text"
              v-model="codeForm.code"
              autofocus
              ref="code"
              auto-complete="off"
              placeholder="请输入邮箱验证码"
              prefix-icon="el-icon-s-promotion"
              spellcheck="false"
            >
            </el-input>
          </el-form-item>
        </el-form>
      </div>
      <div v-if="active === 2" class="common_div">
        <el-form
          :model="passwordForm"
          class="user-container"
          label-position="left"
          label-width="60px"
          size="medium"
        >
          <el-form-item style="float: right; width: 80%" label="新密码">
            <el-input
              type="password"
              v-model="passwordForm.password"
              autofocus
              ref="password"
              auto-complete="off"
              placeholder="请输入新密码"
              prefix-icon="el-icon-key"
            >
            </el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="common_div">
        <el-button @click="next" class="action_button">下一步</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import user from "@/api/user";
export default {
  name: "ResetPassword",

  data() {
    return {
      active: 0,
      Form: {
        username: "",
        email: "",
      },
      codeForm: {
        code: "",
        email: "",
      },
      passwordForm: {
        username: "",
        password: "",
      },
      disabled: false,
      isUsername: false,
    };
  },
  created() {
    let regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
    if (regEmail.test(this.$route.query.username)) {
      console.log("传来了邮箱");
      this.Form.email = this.$route.query.username;
      this.isUsername = false;
    } else {
      // 传来的不是邮箱，那就是用户名
      console.log("传来了用户名");
      this.Form.username = this.$route.query.username;
      this.isUsername = true;
    }
  },
  methods: {
    isEmail() {
      let regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
      if (!regEmail.test(this.Form.email)) {
        this.$message({
          message: "邮箱格式不正确",
          type: "error",
        });
        return false;
      }
      return true;
    },
    beforePost() {
      if (this.Form.username === "" || this.Form.email === "") {
        // 重置密码时，如果用户名或者邮箱未输入，那么提醒用户输入
        if (this.Form.username === "") {
          this.$message({
            message: "警告, 用户名未输入哦",
            type: "warning",
          });
          this.$refs.username.focus();
        } else {
          this.$message({
            message: "警告, 邮箱未输入呀",
            type: "warning",
          });
          this.$refs.email.focus();
        }
        return false;
      } else {
        //进行邮箱格式的检测
        return this.isEmail();
      }
    },
    async next() {
      // 当面板为0时，先判断用户名和邮箱是否输入，进行相关的验证
      if (this.active === 0) {
        let isFinished = this.beforePost();
        if (isFinished) {
          //数据输入正确后，将按钮禁掉，并提示相关信息，然后数据发送到后台
          this.disabled = true;
          this.$message.success("正在发送验证码，请稍后...");
          this.post();
        }
      }
      // 当面板为1时，则到了用户输入验证码的时候, 将验证码传入后台
      if (this.active === 1) {
        // 如果验证码未输入，提示用户
        if (this.codeForm.code === "") {
          this.$message.error("警告, 验证码未输入，请去您邮箱中查看!");
          this.$refs.code.focus();
        } else {
          console.log(this.codeForm);
          const res = await user.resetPassword(this.codeForm);
          console.log(res);
          if (res.code === 20000) {
            //验证码输入正确，
            this.active++;
            this.$message.success("验证码匹配正确！");
          } else {
            //验证码匹配错误返回对应信息
            this.$message.error(successResponse.data.message);
          }
        }
      }
      // 当面板为2时，则到了用户输入密码的时候, 将密码传入后台
      if (this.active === 2) {
        //在发送密码之前，先校验一下是否输入了，不能让用户不小心输入了空密码
        if (this.passwordForm.password === "") {
          this.$message.error("警告, 新密码未输入，请重新输入!");
          this.$refs.password.focus();
        } else {
          const res = await user.resetPassword(this.passwordForm);
          if(res.code === 20000){
             //密码修改成功
             this.$message.success("密码修改成功！");
             this.$router.replace({
               path: "/login",})
          }else{
            this.$message.error(res.message);
          }
        }
      }
    },
    async post() {
      console.log(this.Form.username);
      console.log(this.Form.email);
      const res = await user.resetPassword(this.Form);
      console.log(res);
      if (res.code === 20000) {
        // 如果返回的结果正确，那么需要发送邮件到对应的用户邮箱中，用户自己登录邮箱后找到对应的链接后才可以输入新密码
        this.$message.success(
          "已向" +
            this.Form.email +
            "发送验证码，请在5分钟之内修改密码，否则验证码失效"
        );
        //跳转到下一个面板，并且将按钮恢复正常
        this.active++;
        this.disabled = false;
        //将用户username保存下来
        this.codeForm.email = this.Form.email;
        this.passwordForm.username = this.Form.username;
      } else {
        this.$message.error(res.message);
      }
    },
  },
};
</script>

<style scoped>
.resetPassword {
  /* background-image: url("../../assets/bg1.jpg"); */
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}
.container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 10% auto;
  width: 40%;
  padding: 25px 30px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
  opacity: 0.7;
}
.common_div {
  margin-top: 5%;
}
.user-container {
  width: 80%;
  background: #fff;
}
.action_button {
  width: 20%;
  margin-top: 3%;
  text-align: center;
}
</style>
