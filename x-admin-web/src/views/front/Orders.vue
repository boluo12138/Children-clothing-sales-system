<template>
  <div>
    <Header></Header>
  <div class="main-content">
    <div style="width: 80%; background-color: white; margin: 30px auto; border-radius: 20px">
      <div style="padding-bottom: 10px">
        <div style="font-size: 18px; color: #000000FF; line-height: 80px; border-bottom: #cccccc 1px solid;">
          <div style="margin-left: 20px">我的订单（{{ ordersData.length }} 个）</div>
        </div>
        <div style="margin: 20px 0; padding: 0 50px">
          <div class="table">
            <el-table :data="ordersData" strip>
              <el-table-column label="商品图片" width="120px">
                <template v-slot="scope">
                  <el-image style="width: 80px; height: 60px; border-radius: 3px" v-if="scope.row.goodsImg"
                            :src="scope.row.goodsImg" :preview-src-list="[scope.row.goodsImg]"></el-image>
                </template>
              </el-table-column>
              <el-table-column prop="orderId" label="订单编号"></el-table-column>
              <el-table-column prop="goodsName" label="商品名称" :show-overflow-tooltip="true">
                <template v-slot="scope">
                  <a :href="'/front/detail?id=' + scope.row.goodsId">{{scope.row.goodsName}}</a>
                </template>
              </el-table-column>
              <el-table-column prop="businessName" label="店铺名称">
                <template v-slot="scope">
                  <a :href="'/front/business?id=' + scope.row.businessId">{{scope.row.businessName}}</a>
                </template>
              </el-table-column>
              <el-table-column prop="goodsPrice" label="商品价格">
                <template v-slot="scope">
                  {{scope.row.goodsPrice}} / {{scope.row.goodsUnit}}
                </template>
              </el-table-column>
              <el-table-column prop="num" label="商品数量"></el-table-column>
              <el-table-column prop="price" label="订单总价"></el-table-column>
              <el-table-column prop="username" label="收货人"></el-table-column>
              <el-table-column prop="useraddress" label="收货地址"></el-table-column>
              <el-table-column prop="phone" label="联系电话"></el-table-column>
              <el-table-column prop="status" label="订单状态"></el-table-column>
              <el-table-column label="操作" align="center" width="180">
                <template v-slot="scope">
                  <el-button size="mini" type="primary" v-if="scope.row.status === '未支付'" plain @click="pay(scope.row.orderId)">支付</el-button>
                  <el-button size="mini" type="primary" v-if="scope.row.status === '待收货'" plain @click="updateStatus(scope.row, '已完成')">确认收货</el-button>
                  <el-button size="mini" type="primary" v-if="scope.row.status === '已完成'" plain @click="addComment(scope.row)">评价</el-button>
                  <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
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
                  :total="total">
              </el-pagination>
            </div>
          </div>
        </div>
      </div>
    </div>
    <el-dialog title="请输入评价内容" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" ref="formRef">
        <el-form-item label="评价内容" prop="username">
          <el-input type="textarea" v-model="form.content" placeholder="请输入评价内容"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</div>
</template>

<script>
import utilsApi from '@/api/utilsApi'
export default {

  data() {
    return {
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      ordersData: [],
      selectForm: {
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      userId: JSON.parse(localStorage.getItem('user') || '{}').id,
      orderId:""
    },
      total: 0,
      form: {},
      fromVisible: false
    }
  },
  mounted() {
    this.loadOrders()
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    async loadOrders() {
      console.log(this.selectForm)
     const res = await utilsApi.selectPage('/orders/selectPage',"get", this.selectForm)
     console.log(res)
     if (res.code === 20000) {
      this.ordersData = res.data?.records
      this.total = res.data?.total
     } else {
       this.$message.error(res.message)
     }

    },
    navTo(url) {
      location.href = url
    },
    del(id) {
      // 单个删除
      this.$confirm("您确定删除吗？", "确认删除", { type: "warning" })
        .then(async () => {
          const res = await utilsApi.deleteById("/orders/delete/"+id)
          if(res.code === 20000){
              // 表示操作成功
              this.$message.success("操作成功");
              this.loadOrders();
          }else{
            this.$message.error(res.msg); // 弹出错误的信息
          }
        })
        .catch(() => {});
    },
    handleCurrentChange(pageNum) {
      this.loadOrders(pageNum)
    },
    async updateStatus(row, status) {
      this.form = row
      this.form.status = status
      const res = await utilsApi.addUpdate('/orders/update',"put" ,this.form)
      if (res.code === 20000) {
        this.$message.success('操作成功')
        this.loadOrders()
      } else {
        this.$message.error(res.message)
      }
    },
    addComment(row) {
      this.fromVisible = true
      this.form = row
    },
    async save() {
      let data = {
        userId: this.user.id,
        businessId: this.form.businessId,
        goodsId: this.form.goodsId,
        content: this.form.content,
      }
      const res = await utilsApi.addUpdate('/comment/add',"post", data)
      if (res.code === 20000) {
        this.$message.success('评价成功')
        this.fromVisible = false
        this.updateStatus(this.form, '已评价')
        this.form = {}
      } else {
        this.$message.error(res.message)
      }
    },
    pay(orderId) {
      console.log(orderId)
      window.open('http://127.0.0.1:9999/alipay/pay?oid=' + orderId)
    }

  }
}
</script>
