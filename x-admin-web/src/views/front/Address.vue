<template>
  <div>
    <Header></Header>
    <div class="main-content">
      <div
        style="
          width: 70%;
          background-color: white;
          margin: 30px auto;
          border-radius: 20px;
        "
      >
        <div style="padding-bottom: 10px">
          <div
            style="
              display: flex;
              font-size: 18px;
              color: #000000ff;
              line-height: 80px;
              border-bottom: #cccccc 1px solid;
            "
          >
            <div style="flex: 3; margin-left: 20px">我的地址</div>
            <div style="flex: 1; text-align: right; padding-right: 20px">
              <el-button type="warning" round @click="addAddress"
                >添加收货地址</el-button
              >
            </div>
          </div>
          <div style="margin: 20px 0; padding: 0 50px">
            <div class="table">
              <el-table :data="addressData" strip>
                <el-table-column
                  prop="username"
                  label="收货人"
                  width="350px"
                ></el-table-column>
                <el-table-column
                  prop="useraddress"
                  label="收货地址"
                ></el-table-column>
                <el-table-column
                  prop="phone"
                  label="联系电话"
                ></el-table-column>
                <el-table-column label="操作" align="center" width="180">
                  <template v-slot="scope">
                    <el-button
                      size="mini"
                      type="primary"
                      plain
                      @click="editAddress(scope.row)"
                      >编辑</el-button
                    >
                    <el-button
                      size="mini"
                      type="danger"
                      plain
                      @click="del(scope.row.id)"
                      >删除</el-button
                    >
                  </template>
                </el-table-column>
              </el-table>

              <div class="pagination" style="margin-top: 20px">
                <el-pagination
                  background
                  @current-change="handleCurrentChange"
                  :current-page="selectForm.pageNum"
                  :page-sizes="[5, 10, 20]"
                  :page-size="selectForm.pageSize"
                  layout="total, prev, pager, next"
                  :total="total"
                >
                </el-pagination>
              </div>
            </div>
          </div>
        </div>
      </div>
      <el-dialog
        title="地址信息"
        :visible.sync="formVisible"
        width="40%"
        :close-on-click-modal="false"
        destroy-on-close
      >
        <el-form
          label-width="100px"
          style="padding-right: 50px"
          :model="form"
          :rules="rules"
          ref="formRef"
        >
          <el-form-item prop="username" label="收货人">
            <el-input v-model="form.username" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="useraddress" label="收货地址">
            <el-input v-model="form.useraddress" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="phone" label="联系电话">
            <el-input v-model="form.phone" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="formVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import utilsApi from "@/api/utilsApi";
export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem("user") || "{}"),
      addressData: [],
      total: 0,
      formVisible: false,
      // 表单数据
      selectForm: {
        pageNum: 1, // 当前的页码
        pageSize: 10, // 每页显示的个数
        userId: JSON.parse(localStorage.getItem("user") || "{}").id, // 用户id
      },
      form: {},
      rules: {
        username: [
          { required: true, message: "请输入收货人", trigger: "blur" },
        ],
        useraddress: [
          { required: true, message: "请输入收货地址", trigger: "blur" },
        ],
        phone: [{ required: true, message: "请输入联系电话", trigger: "blur" }],
      },
    };
  },
  mounted() {
    this.loadAddress();
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    addAddress() {
      this.form = {};
      this.formVisible = true;
    },
    editAddress(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.formVisible = true;
    },
    save() {
      // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate(async (valid) => {
        if (valid) {
          this.form.userId = this.user.id;
          const res = await utilsApi.addUpdate(
            this.form.id ? "/address/update" : "/address/add",
            this.form.id ? "PUT" : "POST",
            this.form
          );
          if (res.code === 20000) {
            // 表示成功保存
            this.$message.success(res.message);
            this.formVisible = false;
            this.loadAddress();
          } else {
            this.$message.error(res.message); // 弹出的信息
          }
        }
      });
    },
    async loadAddress() {
      const res = await utilsApi.selectPage(
        "/address/selectPage",
        "get",
        this.selectForm
      );
      if (res.code === 20000) {
        this.addressData = res.data?.records;
        this.total = res.data?.total;
        this.$message.success(res.message);
      } else {
        this.$message.error(res.message);
      }
    },
    navTo(url) {
      location.href = url;
    },
    del(id) {
      this.$confirm(`您确认删除该地址吗?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const res = await utilsApi.deleteById("/address/delete/" + id);
          if (res.code === 20000) {
            this.$message.success(res.message);
            this.loadAddress();
          } else {
            this.$message.error(res.message);
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    handleCurrentChange(pageNum) {
      this.loadAddress(pageNum);
    },
  },
};
</script>

<style scoped></style>
