<template>
  <div>
    <div class="search">
      <el-input
        placeholder="请输入商品名称查询"
        style="width: 200px"
        v-model="selectForm.name"
      ></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load"
        >查询</el-button
      >
      <el-button type="warning" plain style="margin-left: 10px" @click="reset"
        >重置</el-button
      >
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">发布商品</el-button>
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
        <el-table-column label="商品主图">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image
                style="width: 40px; height: 40px"
                v-if="scope.row.img"
                :src="scope.row.img"
                :preview-src-list="[scope.row.img]"
              ></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="name"
          label="商品名称"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column prop="description" label="商品描述">
          <template slot-scope="scope">
            <el-button type="success" @click="viewEditor(scope.row.description)"
              >点击查看</el-button
            >
          </template>
        </el-table-column>
        <el-table-column
          prop="price"
          label="商品价格"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="unit"
          label="计件单位"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="typeName"
          label="商品分类"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="businessName"
          label="所属商家"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="count"
          label="商品销量"
          show-overflow-tooltip
        ></el-table-column>

        <el-table-column label="活动名称">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-tag>{{scope.row.activityName}}</el-tag>
            </div>
          </template>
        </el-table-column>

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
      @close="cancel"
    >
      <el-form
        label-width="100px"
        style="padding-right: 50px"
        :model="form"
        :rules="rules"
        ref="formRef"
      >
      <!-- :action="$baseUrl + '/files/upload'"
      :before-upload="uploadToOss"
      -->
        <el-form-item label="商品主图">
          <el-upload
            class="avatar-uploader"
            :action="$baseUrl + '/aliyun/upload'"
            :headers="{ token: user.token }"
            list-type="picture"
            :on-success="handleAvatarSuccess"
            accept=".jpeg .png .jpg"
          >
            <el-button type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="name" label="商品名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="name" label="商品价格">
          <el-input v-model="form.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="typeId" label="商品分类">
          <el-select
            v-model="form.typeId"
            placeholder="请选择分类"
            style="width: 100%"
          >
            <el-option
              v-for="item in typeData"
              :label="item.name+ ' - ' + item.description"
              :value="item.id"
              :key="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="unit" label="计件单位">
          <el-input v-model="form.unit" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="activityId" label="优惠活动">
          <el-select
            v-model="form.activityId"
            placeholder="请选择活动"
            style="width: 100%"
          >
          <el-option
            v-for="item in activityData"
            :label="item.name + ' - ' + item.description"
            :value="item.id"
            :key="item.id"
            :disabled="item.status !== 1">
          </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="description" label="商品介绍">
          <div id="editor" style="width: 100%"></div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="商品介绍" :visible.sync="editorVisible" width="50%">
      <div v-html="this.viewData" class="w-e-text"></div>
    </el-dialog>
  </div>
</template>

<script>
import E from "wangeditor";
import utilsApi from "@/api/utilsApi";
let editor;
function initWangEditor(content) {
  setTimeout(() => {
    if (!editor) {
      editor = new E("#editor");
      editor.config.placeholder = "请输入内容";
      editor.config.uploadFileName = "file";
      // editor.config.uploadImgServer = "http://localhost:9999/files/wang/upload";
      editor.config.uploadImgServer = "http://localhost:9999/aliyun/upload";
      editor.config.uploadImgMaxSize = 10 * 1024 * 1024;
      editor.create();
    }
    editor.txt.html(content);
  }, 0);
}

export default {
  name: "Goods",
  data() {
    return {
      tableData: [], // 所有的数据
      total: 0,
      fromVisible: false,
      editorVisible: false,
      selectForm: {
        name: "",
        pageNum: 1, // 当前的页码
        pageSize: 10, // 每页显示的个数
        businessId:JSON.parse(localStorage.getItem("user") || "{}").id
      },
      user: JSON.parse(localStorage.getItem("user") || "{}"),
      form: {
        img:""
      },

      rules: {
        title: [{ required: true, name: "请输入商品名称", trigger: "blur" }],
        content: [
          { required: true, img: "请输上传商品图主图", trigger: "blur" },
        ],
      },
      ids: [],
      typeData: [],
      viewData: null,
      activityData:[]
    };
  },
  created() {
    this.load();
    this.loadType();
    this.loadActivity();
  },
  methods: {
    async loadType() {
      const res = await utilsApi.selectAll("/type/selectAll")
      if(res.code === 20000){
        this.typeData = res.data;
      }else{
        this.$message.error(res.message);
      }
    },
    async loadActivity(){
      const res = await utilsApi.selectAll("/activity/selectAll",{
        businessId:this.user.id
      })
      // console.log(res)
      if(res.code === 20000){
        this.activityData = res.data;
      }else{
        this.$message.error(res.message);
      }
    },
  // 在Vue组件中的某个方法中使用ali-oss实例上传文件示例
    async uploadToOss(file) {
      const isJPG = file.type === "image/jpeg" || file.type === "image/png";
      const isLt2M = file.size / 1024 / 1024 < 2;
      const filePath = 'ChildrenClothingSalesSystem/goods/' + file.name;  // 更改图片上传的路径，比如存放在OSS的images目录下
      console.log(filePath)
      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      try {
        const result = await this.$oss.put(filePath, file);
        if (result.res.status === 200) {
            this.form.img = result.url;
          console.log("图片上传成功:", result);
          }

      } catch (error) {
        console.error("图片上传失败:", error);
      }
    },
    handleAdd() {
      // 新增数据
      if(this.user.roles[0] !== 'bussiness'){
        this.$message.warning('您的店铺信息还未审核通过，暂时不允许发布商品')
        return
      }
      this.form = {
        businessId : this.user.id
      };
      // 新增数据的时候清空数据
      initWangEditor("");
      this.fromVisible = true; // 打开弹窗
    },
    handleEdit(row) {
      // 编辑数据
      this.form = JSON.parse(JSON.stringify(row)); // 给form对象赋值  注意要深拷贝数据
      initWangEditor(this.form.description || "");
      // initWangEditor(this.form.description || "");
      this.fromVisible = true; // 打开弹窗
    },
    viewEditor(content) {
      this.viewData = content
      this.editorVisible = true;
    },
    cancel() {
      this.fromVisible = false;
      this.editorVisible = false;
      editor = null;  // 重置编辑器内容
    },
    save() {
      // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate(async (valid) => {
        if (valid) {
          this.form.description = editor.txt.html();
          console.log(this.form);
          const res = await utilsApi.addUpdate(
              this.form.id ? "/goods/update" : "/goods/add",
               this.form.id ? "PUT" : "POST",
              this.form,
          )
          if (res.code === 20000) {
            // 表示成功保存
            this.$message.success(res.message);
            this.fromVisible = false;

          } else {
            this.$message.error(res.message);
          }
          this.$router.go(0)
        }
      })
    },
    del(id) {
      // 单个删除
      this.$confirm("您确定删除吗？", "确认删除", { type: "warning" })
        .then(async () => {
          const res =await utilsApi.deleteById("/goods/delete/" + id)
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
          const res = await utilsApi.deleteByList("/goods/delete/batch",this.ids)
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
        "/goods/selectPage",
        "get",
        this.selectForm
      );
      if (res.code === 20000) {
        this.tableData = res.data?.records;
        this.total = res.data?.total;
      } else {
        this.tableData = [];
      }

    },
    reset() {
      this.selectForm.name = ""
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.selectForm.pageNum = pageNum;
      this.load();
    },
    handleAvatarSuccess(response, file, fileList) {
      this.form.img = response.data[0].url;
      //上传成功后返回的结果message是图片的路径
      // this.form.img = response.message;
    },
  },
};
</script>

<style scoped></style>
