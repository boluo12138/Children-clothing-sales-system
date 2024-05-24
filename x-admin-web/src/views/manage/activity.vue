<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入活动名称查询" style="width: 200px" v-model="selectForm.name"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe  @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="活动名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="discount" label="活动折扣" show-overflow-tooltip></el-table-column>
        <el-table-column prop="price" label="满减金额" show-overflow-tooltip></el-table-column>
        <el-table-column prop="description" label="活动描述" show-overflow-tooltip></el-table-column>

        <el-table-column prop="startTime" label="开始时间" show-overflow-tooltip></el-table-column>
        <el-table-column prop="endTime" label="结束时间" show-overflow-tooltip></el-table-column>
        <el-table-column prop="businessName" label="创建人" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="primary" @click="handleEdit(scope.row)" size="mini">编辑</el-button>
            <el-button plain type="danger" size="mini" @click=del(scope.row.id)>删除</el-button>
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
            :total="total">
        </el-pagination>
      </div>
    </div>


    <el-dialog title="信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="name" label="活动名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="discount" label="活动折扣">
          <el-input v-model="form.discount" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="price" label="活动满减">
          <el-input v-model="form.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="description" label="活动描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="活动时间">
          <el-date-picker
            v-model="timeData"
            type="datetimerange"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :format="'yyyy-MM-dd HH:mm:ss'"
            align="right">
          </el-date-picker>
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
import utilsApi from '@/api/utilsApi'
export default {
  name: "Activity",
  data() {
    return {
      tableData: [],  // 所有的数据
      selectForm:{
        businessId: JSON.parse(localStorage.getItem('user') || '{}').id,
        name:"",
        pageNum: 1,   // 当前的页码
        pageSize: 10,  // 每页显示的个数
      },
      timeData:[],
      total: 0,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      rules: {
        title: [
          {required: true, name: '请输入分类名称', trigger: 'blur'},
        ],
        content: [
          {required: true, img: '请输上传分类图标', trigger: 'blur'},
        ]
      },
      ids: [],
      pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              end.setTime(end.getTime() + 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              end.setTime(end.getTime() + 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              end.setTime(end.getTime() + 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
    }
  },
  created() {
    this.load()
  },
  methods: {
    handleAdd() {   // 新增数据
      this.timeData = []
      this.form = {
        businessId : this.user.id,
      }  // 新增数据的时候清空数据
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.timeData = [row.startTime,row.endTime]
      this.fromVisible = true   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate(async (valid) => {
        if (valid) {
          // 在需要修改格式的地方
          this.form.startTime = this.timeData[0].toISOString().replace('T', ' ').replace(/\.\d{3}Z/, '')
          this.form.endTime = this.timeData[1].toISOString().replace('T', ' ').replace(/\.\d{3}Z/, '')
          console.log("form",this.form)
          const res = await utilsApi.addUpdate(
            this.form.id ? '/activity/update' : '/activity/add',
          this.form.id ? 'PUT' : 'POST',
          this.form
        )
        console.log("res",res)
        if(res.code === 20000){
          this.$message.success('保存成功')
          this.fromVisible = false
          this.load()

        }else{
          this.$message.error(res.message)
        }

        }
      })
    },
    async del(id) {   // 单个删除
      const res = await utilsApi.deleteById('/activity/delete/' + id)
      if(res.code === 20000){
        this.$message.success('删除成功')
        this.load()
      }else{
        this.$message.error(res.message)
      }
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(async () => {
        const res = await utilsApi.deleteByList('/activity/delete/batch',  this.ids)
        if(res.code === 20000){
          this.$message.success('删除成功')
          this.load()
        }else{
          this.$message.error(res.message)
        }
      }).catch(() => {
      })
    },
    async load() {  // 分页查询
      const res = await utilsApi.selectPage("/activity/selectPage","get",this.selectForm)
      if(res.code === 20000){
        this.tableData = res.data?.records
        this.total = res.data?.total
      }else{
        this.$message.error(res.message)
      }
    },
    reset() {
      this.selectForm.name = ""
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.selectForm.pageNum = pageNum
      this.load()
    },
    handleAvatarSuccess(response, file, fileList) {
      this.form.img = response.data
    },
  }
}
</script>

<style scoped>

</style>
