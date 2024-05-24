<template>
  <div class="main-content">
    <Header></Header>
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
          <div style="margin-left: 20px; flex: 1">
            全部商品（{{ goodsData.length }}件）
          </div>
          <div style="flex: 2; text-align: right">
            <el-select
              v-model="addressId"
              placeholder="请选择收货地址"
              style="width: 70%"
            >
              <el-option v-for="item in addressData" :label="item.username + ' - ' + item.useraddress + ' - ' + item.phone" :value="item.id" :key="item.id"></el-option>
            </el-select>
          </div>
          <div
            style="
              flex: 1;
              font-size: 16px;
              text-align: right;
              padding-right: 20px;
            "
          >
            已选商品 ￥ {{ totalPrice }}
            <el-button type="danger" round @click="pay">下单</el-button>
          </div>
        </div>
        <div style="margin: 20px 0; padding: 0 50px">
          <div class="table">
            <el-table
              :data="goodsData"
              strip
              @selection-change="handleSelectionChange"
            >
              <el-table-column
                type="selection"
                width="55"
                align="center"
              ></el-table-column>
              <el-table-column label="商品图片" width="120px">
                <template v-slot="scope">
                  <el-image
                    style="width: 80px; height: 60px; border-radius: 3px"
                    v-if="scope.row.goodsImg"
                    :src="scope.row.goodsImg"
                    :preview-src-list="[scope.row.goodsImg]"
                  ></el-image>
                </template>
              </el-table-column>
              <el-table-column prop="goodsName" label="商品名称" width="240px">
                <template v-slot="scope">
                  <a  @click="goToGoods(scope.row.goodsId)">{{
                    scope.row.goodsName
                  }}</a>
                </template>
              </el-table-column>
              <el-table-column prop="businessName" label="店铺名称">
                <template v-slot="scope">
                  <a  @click="goToBusiness(scope.row.businessId)">{{
                    scope.row.businessName
                  }}</a>
                </template>
              </el-table-column>
              <el-table-column
                prop="goodsPrice"
                label="商品价格"
              ></el-table-column>
              <el-table-column prop="num" label="选择数量">
                <template v-slot="scope">
                  <el-input-number
                  size="large"
                    v-model="scope.row.num"
                    style="width: 120px;"
                    @change="handleChange(scope.row)"
                    :min="1"
                  ></el-input-number>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" width="180">
                <template v-slot="scope">
                  <el-button
                    size="mini"
                    type="danger"
                    plain
                    @click="del(scope.row.id)"
                    >移除购物车</el-button
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
  </div>
</template>

<script>
import utilsApi from "@/api/utilsApi";
export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem("user") || "{}"),
      goodsData: [],
      totalPrice: 0,
      initialTotalPrice: 0,  // 存储初始的总价格
      total: 0,
      addressId: null,
      addressData: [],
      selectedData: [],
      selectForm: {
        pageNum: 1, // 当前的页码
        pageSize: 10, // 每页显示的个数
        userId: JSON.parse(localStorage.getItem("user") || "{}").id,
      },
    };
  },
  mounted() {
    this.loadGoods();
    this.loadAddress();
  },

  methods: {
    goToGoods(goodsId){
      console.log("goToBusiness")
      this.$router.push({ path: '/detail', query: { id: goodsId } });
    },
    goToBusiness(businessId) {
    console.log("goToBusiness")
    this.$router.push({ path: '/business', query: { id: businessId } });
  },

    async loadAddress() {
      const res = await utilsApi.selectAll("/address/selectAll",{
        "userId": this.selectForm.userId
      });
      if (res.code === 20000) {
        this.addressData = res.data;
      } else {
        this.$message.error(res.msg);
      }
    },
    async loadGoods() {
      // 分页查询
      const res = await utilsApi.selectPage(
        "/cart/selectPage",
        "get",
        this.selectForm
      );

      if (res.code === 20000) {
        this.goodsData = res.data?.records;
        this.total = res.data?.total;
        console.log(this.goodsData)
      } else {
        this.goodsData = [];
      }
    },
    navTo(url) {
      location.href = url;
    },
    async del(id) {
      const res = await utilsApi.deleteById("/cart/delete/" + id)
      if(res.code === 20000){
        this.$message.success("移除成功");
      }else{
        this.$message.error(res.message);
      }
      this.$router.go(0);

    },
    handleCurrentChange(pageNum) {
      this.loadGoods(pageNum);
    },
    handleSelectionChange(rows) {
      this.totalPrice = 0;
      this.selectedData = rows;
      // 计计算总价格
      this.selectedData.forEach((item) => {
        this.totalPrice += item.goodsPrice * item.num;
        if(parseInt(item.activityStatus) === 1){
          if(item.activityDiscount < 1 && item.activityDiscount > 0){
            this.totalPrice *= parseFloat(item.activityDiscount);
          }else if (item.activityDiscount > 1){
            if(this.totalPrice >= item.activityDiscount){
              let dicPrice = item.activityPrice * parseInt(this.totalPrice/item.activityDiscount);
              this.totalPrice -= dicPrice;
            }
          }

        }

      });
    },
    handleChange(row) {
      console.log(row)
      this.totalPrice = 0;
      this.selectedData.forEach((item) => {
        this.totalPrice += item.goodsPrice * item.num;
        if(parseInt(item.activityStatus) === 1){
          if(item.activityDiscount < 1 && item.activityDiscount > 0){
            this.totalPrice *= parseFloat(item.activityDiscount);
          }else if (item.activityDiscount > 1){
            if(this.totalPrice >= item.activityDiscount){
              let dicPrice = item.activityPrice * parseInt(this.totalPrice/item.activityDiscount);
              this.totalPrice -= dicPrice;
            }
          }

        }
      });
    },
// handleSelectionChange(rows) {
//   this.totalPrice = 0;
//   selectedData.forEach((item) => {
//     // 计算普通价格
//     let basicPrice = item.goodsPrice * item.num;
//     // 计算折扣价格
//     if (parseInt(item.activityStatus) === 1) {
//       if (item.activityDiscount < 1 && item.activityDiscount > 0) {
//         basicPrice *= parseFloat(item.activityDiscount);
//       } else if (item.activityDiscount > 1) {
//         if (basicPrice >= item.activityDiscount) {
//           let dicPrice = item.activityPrice * parseInt(basicPrice / item.activityDiscount);
//           basicPrice -= dicPrice;
//         }
//       }
//     }
//     this.totalPrice += basicPrice;
//   });
//   this.initialTotalPrice = this.totalPrice;
// },

// handleChange(row) {
//   // this.calculateTotalPrice(row);
//   this.totalPrice = 0;
//   selectedData.forEach((item) => {
//     // 计算普通价格
//     let basicPrice = item.goodsPrice * item.num;
//     // 计算折扣价格
//     if (parseInt(item.activityStatus) === 1) {
//       if (item.activityDiscount < 1 && item.activityDiscount > 0) {
//         basicPrice *= parseFloat(item.activityDiscount);
//       } else if (item.activityDiscount > 1) {
//         if (basicPrice >= item.activityDiscount) {
//           let dicPrice = item.activityPrice * parseInt(basicPrice / item.activityDiscount);
//           basicPrice -= dicPrice;
//         }
//       }
//     }
//     this.totalPrice += basicPrice;
//   });
//   this.initialTotalPrice = this.totalPrice;
// },

calculateTotalPrice(selectedData) {
  this.totalPrice = 0;
  selectedData.forEach((item) => {
    // 计算普通价格
    let basicPrice = item.goodsPrice * item.num;
    // 计算折扣价格
    if (parseInt(item.activityStatus) === 1) {
      if (item.activityDiscount < 1 && item.activityDiscount > 0) {
        basicPrice *= parseFloat(item.activityDiscount);
      } else if (item.activityDiscount > 1) {
        if (basicPrice >= item.activityDiscount) {
          let dicPrice = item.activityPrice * parseInt(basicPrice / item.activityDiscount);
          basicPrice -= dicPrice;
        }
      }
    }
    this.totalPrice += basicPrice;
  });
  this.initialTotalPrice = this.totalPrice;
},

    async pay() {
      if (!this.addressId) {
        this.$message.warning("请选择收货地址");
        return;
      }
      if (!this.selectedData || this.selectedData.length === 0) {
        this.$message.warning("请选择商品");
        return;
      }

      let data = {
        userId: this.user.id,
        addressId: this.addressId,
        status: "未支付",
        cartData: this.selectedData,
      };
      const res =await utilsApi.addUpdate("/orders/add","post",data);
      console.log(res)
      if(res.code === 20000){
        this.$message.success("操作成功");
      }else{
        this.$message.error(res.msg);
      }
      this.$router.push({ path: '/orders' });
    },
  },
};
</script>
