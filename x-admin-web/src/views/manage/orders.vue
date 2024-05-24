<template>
  <div>
    <div class="search">
      <el-input
        placeholder="请输入订单编号"
        style="width: 200px"
        v-model="selectForm.orderId"
      ></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load"
        >查询</el-button
      >
      <el-button type="warning" plain style="margin-left: 10px" @click="reset"
        >重置</el-button
      >
    </div>

    <div class="operation">
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table
        :data="tableData"
        stripe
        @selection-change="handleSelectionChange"
      >
        <el-table-column
          type="selection"
          width="55"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="id"
          label="序号"
          width="80"
          align="center"
          sortable
        ></el-table-column>
        <el-table-column label="商品图片">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image
                style="width: 40px; height: 40px"
                v-if="scope.row.goodsImg"
                :src="scope.row.goodsImg"
                :preview-src-list="[scope.row.goodsImg]"
              ></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="orderId" label="订单编号"></el-table-column>
        <el-table-column
          prop="goodsName"
          label="商品名称"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="goodsPrice"
          label="商品单价"
          show-overflow-tooltip
        >
          <template v-slot="scope">
            {{ scope.row.goodsPrice }} / {{ scope.row.goodsUnit }}
          </template>
        </el-table-column>
        <el-table-column
          prop="num"
          label="商品数量"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="price"
          label="订单总价"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="businessName"
          label="所属店铺"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="username"
          label="收货人"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="useraddress"
          label="收货地址"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="phone"
          label="联系电话"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="status"
          label="订单状态"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button
              plain
              type="primary"
              size="mini"
              v-if="scope.row.status === '待发货'"
              @click="updateStatus(scope.row, '待收货')"
              >发货</el-button
            >
            <el-button
              plain
              type="danger"
              size="mini"
              @click="del(scope.row.id)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
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
</template>

<script>
import utilsApi from "@/api/utilsApi";
export default {
  name: "Orders",
  data() {
    return {
      tableData: [],  // 所有的数据
      selectForm: {
        orderId: "",
        pageNum: 1, // 当前的页码
        pageSize: 10, // 每页显示的个数
        businessId:JSON.parse(localStorage.getItem("user") || "{}").id
      },
      total: 0,
      orderId: null,
      form: {},
      user: JSON.parse(localStorage.getItem("user") || "{}"),
      ids: [],
    };
  },
  created() {
    this.load();
  },
  methods: {
    async save() {
      // 保存按钮触发的逻辑  它会触发新增或者更新
      const res = await utilsApi.addUpdate(
      this.form.id ? "/orders/update" : "/orders/add",
      this.form.id ? "put" : "post",
      this.form
    );
    console.log(res)
      if (res.code === 20000) {
        // 表示操作成功
        this.$message.success("保存成功");
        this.load();
        this.fromVisible = false;
      } else {
        this.$message.error(res.message); // 弹出错误的信息
      }
    },
    del(id) {
      // 单个删除
      this.$confirm("您确定删除吗？", "确认删除", { type: "warning" })
        .then(async () => {
          const res = await utilsApi.deleteById("/orders/delete/"+id)
          if(res.code === 20000){
              // 表示操作成功
              this.$message.success("操作成功");
              this.load();
          }else{
            this.$message.error(res.msg); // 弹出错误的信息
          }
        })
        .catch(() => {});
    },
    handleSelectionChange(rows) {
      // 当前选中的所有的行数据
      this.ids = rows.map((v) => v.id); //  [1,2]
    },
    delBatch() {
      // 批量删除
      if (!this.ids.length) {
        this.$message.warning("请选择数据");
        return;
      }
      this.$confirm("您确定批量删除这些数据吗？", "确认删除", {
        type: "warning",
      })
        .then(async () => {
          const res = await utilsApi.deleteByList("/orders/delete/batch",this.ids)
          if(res.code === 20000){
                // 表示操作成功
                this.$message.success("操作成功");
                this.load();
          }else{
            this.$message.error(res.msg); // 弹出错误的信息
          }

        })
        .catch(() => {});
    },
    async load() {
      // 分页查询
      const res = await utilsApi.selectPage(
        "/orders/selectPage",
        "get",
        this.selectForm
      );
      console.log(res)
      if (res.code === 20000) {
        this.tableData = res.data?.records;
        this.total = res.data?.total;
      } else if (res.code === 20001) {
        this.tableData = [];
      }
    },
    reset() {
      this.selectForm.orderId = "";
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.selectForm.pageNum = pageNum;
      this.load();
    },
    handleAvatarSuccess(response, file, fileList) {
      this.form.img = response.data;
    },
    updateStatus(row, status) {
      this.form = row;
      this.form.status = status;
      console.log(this.form)
      this.save();
    },
  },
};
</script>

<style scoped></style>
