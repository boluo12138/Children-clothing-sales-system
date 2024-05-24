<template>
  <div>
    <div class="search">
      <el-input
        placeholder="请输入标题查询"
        style="width: 200px"
        v-model="selectForm.title"
      ></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load"
        >查询</el-button
      >
      <el-button type="warning" plain style="margin-left: 10px" @click="reset"
        >重置</el-button
      >
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
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
        <el-table-column
          prop="title"
          label="标题"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="content"
          label="内容"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column prop="time" label="创建时间"></el-table-column>
        <el-table-column prop="userName" label="创建人"></el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button
              plain
              type="primary"
              @click="handleEdit(scope.row)"
              size="mini"
              >编辑</el-button
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

    <el-dialog
      title="信息"
      :visible.sync="fromVisible"
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
        <el-form-item prop="title" label="标题">
          <el-input v-model="form.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="content" label="内容">
          <el-input
            type="textarea"
            :rows="5"
            v-model="form.content"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import utilsApi from "@/api/utilsApi";
import roleManage from "@/api/roleManage";
export default {
  name: "Notice",
  data() {
    return {
      tableData: [], // 所有的数据
      //查询 重置 添加栏参数
      selectForm: {
        title: "",
        pageNum: 1, // 当前的页码
        pageSize: 10, // 每页显示的个数
        userId:JSON.parse(localStorage.getItem("user") || "{}").id
      },
      user: JSON.parse(localStorage.getItem("user") || "{}"),
      total: 0,
      fromVisible: false,
      form: {

      },

      rules: {
        title: [{ required: true, message: "请输入标题", trigger: "blur" }],
        content: [{ required: true, message: "请输入内容", trigger: "blur" }],
      },
      ids: [],
    };
  },
  created() {
    this.load();
  },
  methods: {
    handleAdd() {
      // 新增数据
      this.form = {
        userId:this.user.id
      }; // 新增数据的时候清空数据
      this.fromVisible = true; // 打开弹窗
    },
    handleEdit(row) {
      // 编辑数据
      this.form = JSON.parse(JSON.stringify(row)); // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true; // 打开弹窗
    },
    save() {
      // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate(async (valid) => {
        if (valid) {
          console.log(this.form)
          const res = await utilsApi.addUpdate(this.form.id ? "/notice/update": "/notice/add",
          this.form.id ? "put":"post",this.form);
          console.log(res)
          if (res.code === 20000) {
            // 表示成功保存
            this.$message.success(res.message);
            this.load();
            this.fromVisible = false;
          } else {
            this.$message.error(res.msg);
          }
        }
      });
    },
    del(id) {
      // 单个删除
      this.$confirm("您确定删除吗？", "确认删除", { type: "warning" })
        .then(async () => {
          console.log(id);
          const res = await utilsApi.deleteById("/notice/delete/"+id);
          console.log(res);
          if (res.code === 20000) {
            // 表示操作成功
            this.$message.success(res.message);
            this.load()
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
          const res = await utilsApi.deleteByList("/notice/delete/batch",this.ids)
          if(res.code === 20000){
            // 表示操作成功
            this.$message.success(res.message);
            this.load()
          }else{
            this.$message.error(res.msg); // 弹出错误的信息
          }
        })
        .catch(() => {});
    },
    async load() {
      // 分页查询
      const res = await utilsApi.selectPage("/notice/selectPage","get",this.selectForm);
      if (res.code === 20000) {
        this.tableData = res.data?.records;
        this.total = res.data?.total;
      }else if(res.code === 20001){
        this.tableData = []
      }
    },
    reset() {
      this.selectForm.title = ""
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.selectForm.pageNum = pageNum;
      this.load();
    },
  },
};
</script>

<style scoped></style>
