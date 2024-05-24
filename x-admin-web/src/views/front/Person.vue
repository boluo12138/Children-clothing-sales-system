<template>
  <div>
    <!-- 头部         导航栏菜单 -->
    <Header> </Header>
    <!-- 主体内容 -->
    <div class="main-content">
    <el-card style="width: 50%; margin: 30px auto">
      <div style="text-align: right; margin-bottom: 20px">
        <el-button type="primary" @click="updatePassword">修改密码</el-button>
      </div>
      <el-form :model="user" label-width="80px" style="padding-right: 20px">
        <div style="margin: 15px; text-align: center">
          <el-upload
            class="avatar-uploader"
            :action="$baseUrl + '/aliyun/upload;'"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
          >
            <img v-if="user.avatar" :src="user.avatar" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="user.username"
            placeholder="用户名"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="name">
          <el-input v-model="user.name" placeholder="昵称"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="user.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="user.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="update">保 存</el-button>
        </div>
      </el-form>
    </el-card>
    <el-dialog
      title="修改密码"
      :visible.sync="dialogVisible"
      width="30%"
      :close-on-click-modal="false"
      destroy-on-close
    >
      <el-form
        :model="user"
        label-width="80px"
        style="padding-right: 20px"
        :rules="rules"
        ref="formRef"
      >
        <el-form-item label="原始密码" prop="password">
          <el-input
            show-password
            v-model="user.password"
            placeholder="原始密码"
          ></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input
            show-password
            v-model="user.newPassword"
            placeholder="新密码"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            show-password
            v-model="user.confirmPassword"
            placeholder="确认密码"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
  </div>
</template>

<script>
import store from "../../store";
import userApi from "@/api/userManage";
export default {
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请确认密码"));
      } else if (value !== this.user.newPassword) {
        callback(new Error("确认密码错误"));
      } else {
        callback();
      }
    };
    return {
      user: JSON.parse(localStorage.getItem("user") || "{}"),
      dialogVisible: false,

      rules: {
        password: [
          { required: true, message: "请输入原始密码", trigger: "blur" },
        ],
        newPassword: [
          { required: true, message: "请输入新密码", trigger: "blur" },
        ],
        confirmPassword: [
          { validator: validatePassword, required: true, trigger: "blur" },
        ],
      },
    };
  },
  created() {},
  methods: {
    async update() {
      // 保存当前的用户信息到数据库
      const res = await userApi.updateUser(this.user);
      console.log(res);
      if (res.code === 20000) {
        // 成功更新
        this.$message.success(res.message);
        // 更新浏览器缓存里的用户信息
        localStorage.setItem("user", JSON.stringify(res.data));
        // 触发父级的数据更新
        this.$emit("update:user");
      } else {
        this.$message.error(res.msg);
      }
      this.$router.go(0); // 刷新页面
    },
    handleAvatarSuccess(response, file, fileList) {
      // 把user的头像属性换成上传的图片的链接
      console.log(response)
      this.$set(this.user, "avatar", response.data[0].url);
      // this.form.img = response.data;
    },
    // 修改密码
    updatePassword() {
      this.dialogVisible = true;
    },
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          userApi
            .updatePassword(
              store.state.user.token,
              store.state.user.username,
              this.user.password,
              this.user.confirmPassword
            )
            .then((res) => {
              console.log("update password success: ", res.data);
              this.$router.go(0); // 刷新页面
            });
        }
      });
    },
    cancel() {
      this.dialogVisible = false;
    },
  },
};
</script>

<style scoped>
/deep/.el-form-item__label {
  font-weight: bold;
}
/deep/.el-upload {
  border-radius: 50%;
}
/deep/.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
}
/deep/.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
  border-radius: 50%;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%;
}
</style>
