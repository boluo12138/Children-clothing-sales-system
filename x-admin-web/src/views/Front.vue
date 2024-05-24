<template>
  <div>
    <!-- 头部         导航栏菜单 -->
    <div class="front-header">
      <el-row :gutter="20" style="">
        <el-col :span="3"
          ><div class="grid-content bg-purple front-header-left">
            <a href="/"><img src="../assets/boluo.png" />童装销售系统</a>
          </div></el-col
        >
        <el-col :span="8">
          <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
          >
            <el-menu-item index="1">首页</el-menu-item>
            <el-submenu index="2">
              <template slot="title">女装</template>
              <el-submenu index="2-1">
                <template slot="title">外套</template>
                <el-menu-item index="2-1-1">所有外套</el-menu-item>
                <el-menu-item index="2-1-2">运动外套</el-menu-item>
                <el-menu-item index="2-1-3">防晒外套</el-menu-item>
                <el-menu-item index="2-1-4">保暖外套</el-menu-item>
                <el-menu-item index="2-1-5">摇粒绒</el-menu-item>
                <el-menu-item index="2-1-6">棉服</el-menu-item>
              </el-submenu>

              <el-submenu index="2-2">
                <template slot="title">上装</template>
                <el-menu-item index="2-2-1">所有上装</el-menu-item>
                <el-menu-item index="2-2-2">卫衣</el-menu-item>
                <el-menu-item index="2-2-3">UT</el-menu-item>
                <el-menu-item index="2-2-4">T恤</el-menu-item>
                <el-menu-item index="2-2-5">POLO</el-menu-item>
                <el-menu-item index="2-2-6">衫</el-menu-item>
                <el-menu-item index="2-2-7">针织衫</el-menu-item>
              </el-submenu>
              <el-submenu index="2-3">
                <template slot="title">裤装</template>
                <el-menu-item index="2-3-1">裙装</el-menu-item>
                <el-menu-item index="2-3-2">运动裤</el-menu-item>
                <el-menu-item index="2-3-3">牛仔裤</el-menu-item>
              </el-submenu>

              <el-submenu index="2-4">
                <template slot="title">内衣</template>
                <el-menu-item index="2-4-1">内衣</el-menu-item>
                <el-menu-item index="2-4-2">少女文胸</el-menu-item>
                <el-menu-item index="2-4-3">吊带</el-menu-item>
                <el-menu-item index="2-4-4">内裤</el-menu-item>
              </el-submenu>

              <el-submenu index="2-5">
                <template slot="title">起居系列</template>
                <el-menu-item index="2-5-1">所有起居系列</el-menu-item>
                <el-menu-item index="2-5-2">起居裤</el-menu-item>
                <el-menu-item index="2-5-3">起居套装</el-menu-item>
                <el-menu-item index="2-5-4">袜子</el-menu-item>
              </el-submenu>
              <el-submenu index="2-6">
                <template slot="title">配件</template>
                <el-menu-item index="2-6-1">所有配件</el-menu-item>
                <el-menu-item index="2-6-2">帽子·手套</el-menu-item>
              </el-submenu>
            </el-submenu>

            <el-submenu index="3">
              <template slot="title">男装</template>
              <el-submenu index="3-1">
                <template slot="title">外套</template>
                <el-menu-item index="3-1-1">所有外套</el-menu-item>
                <el-menu-item index="3-1-2">运动外套</el-menu-item>
                <el-menu-item index="3-1-3">防晒外套</el-menu-item>
                <el-menu-item index="3-1-4">保暖外套</el-menu-item>
                <el-menu-item index="3-1-5">摇粒绒</el-menu-item>
                <el-menu-item index="3-1-6">棉服</el-menu-item>
              </el-submenu>

              <el-submenu index="3-2">
                <template slot="title">上装</template>
                <el-menu-item index="3-2-1">所有上装</el-menu-item>
                <el-menu-item index="3-2-2">卫衣</el-menu-item>
                <el-menu-item index="3-2-3">UT</el-menu-item>
                <el-menu-item index="3-2-4">T恤</el-menu-item>
                <el-menu-item index="3-2-5">POLO</el-menu-item>
                <el-menu-item index="3-2-6">衬衫</el-menu-item>
                <el-menu-item index="3-2-7">针织衫</el-menu-item>
              </el-submenu>
              <el-submenu index="3-3">
                <template slot="title">裤装</template>
                <el-menu-item index="3-3-1">短裤</el-menu-item>
                <el-menu-item index="3-3-2">运动裤</el-menu-item>
                <el-menu-item index="3-3-3">牛仔裤</el-menu-item>
              </el-submenu>

              <el-submenu index="3-4">
                <template slot="title">起居系列</template>
                <el-menu-item index="3-4-1">所有起居系列</el-menu-item>
                <el-menu-item index="3-4-2">起居裤</el-menu-item>
                <el-menu-item index="3-4-3">起居套装</el-menu-item>
                <el-menu-item index="3-4-1">内裤</el-menu-item>
                <el-menu-item index="3-4-4">袜子</el-menu-item>
              </el-submenu>
              <el-submenu index="3-5">
                <template slot="title">配件</template>
                <el-menu-item index="3-5-1">所有配件</el-menu-item>
                <el-menu-item index="3-5-2">帽子·手套</el-menu-item>
              </el-submenu>
            </el-submenu>
            <el-menu-item index="4"
              ><a href="https://www.ele.me" target="_blank"
                >关于我们</a
              ></el-menu-item
            >
          </el-menu>
        </el-col>
        <el-col :span="6"
          ><div class="grid-content bg-purple">
            <el-select
              v-model="value"
              multiple
              filterable
              remote
              reserve-keyword
              placeholder="请输入关键词"
              :remote-method="remoteMethod"
              :loading="loading"
            >
              <!-- <el-option
                for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option> -->
            </el-select>
            <el-button type="primary" icon="el-icon-search">搜索</el-button>
          </div></el-col
        >
        <el-col :span="2">
          <el-button
            :loading="loading"
            type="primary"
            style="width: 75%; margin-bottom: 30px"
            @click="handleLogin"
            >登 录</el-button
          >
        </el-col>
        <el-col :span="2">
          <el-button
            :loading="loading"
            type="primary"
            style="width: 75%; margin-bottom: 30px"
            @click="handleRegister"
            >注 册</el-button
          >
        </el-col>
        <el-col :span="2"
          ><div class="grid-content bg-purple">
            <el-button
              type="primary"
              icon="el-icon-s-goods"
              style="width: 120%; margin-bottom: 30px"
              @click="handleCart"
              >我的购物车</el-button
            >
          </div></el-col
        >
      </el-row>
    </div>
    <div class="main-body">
      <router-view ref="child" />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 导航栏菜单数据
      activeIndex: "1",
      // activeIndex2: "1",
      urls: [
        "https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg",
        "https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg",
        "https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg",
        "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg",
        "https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg",
        "https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg",
        "https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg",
        "https://img.alicdn.com/imgextra/i1/196993935/O1CN01zy2C781ewHSN9jQba_!!196993935.jpg",
      ],
      user: JSON.parse(localStorage.getItem("user") || "{}"),
      value: [],
      goodsData: [
        {
          id: 5,
          name: "设计感修身短款t恤女夏季新款圆领薄款短袖白色高腰紧身露脐上衣",
          description:
            '<p><br/></p><p><img src="http://localhost:9090/files/1699261664936-d1.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699261669398-d2.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699261673992-d3.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699261678840-d4.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699261682978-d5.png" style="max-width:100%;" contenteditable="false"/></p>',
          img: "http://localhost:9090/files/1699257986682-1.png",
          price: 59.0,
          unit: "件",
          count: 0,
          typeId: 16,
          businessId: 2,
          typeName: null,
          businessName: null,
        },
        {
          id: 6,
          name: "MsShe大码女装新款叠穿松紧腰配饰假两件系扣衬衫下摆屁帘",
          description:
            '<p><img src="http://localhost:9090/files/1699428736624-d1.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699428739895-d2.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699428742752-d3.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699428745711-d4.png" style="max-width:100%;" contenteditable="false"/></p>',
          img: "http://localhost:9090/files/1699428705672-1.png",
          price: 159.0,
          unit: "件",
          count: 0,
          typeId: 16,
          businessId: 2,
          typeName: null,
          businessName: null,
        },
        {
          id: 7,
          name: "加绒加厚MsShe大码女装2023新款秋装胖mm多巴胺V领宽松卫衣上衣冬",
          description:
            '<p><img src="http://localhost:9090/files/1699445276508-d1.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699445283145-d2.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699445286061-d3.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699445289121-d4.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699445291821-d5.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699445294540-d6.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699445297097-d7.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699445299644-d8.png" style="max-width:100%;" contenteditable="false"/></p>',
          img: "http://localhost:9090/files/1699445257509-1.png",
          price: 129.0,
          unit: "件",
          count: 0,
          typeId: 16,
          businessId: 2,
          typeName: null,
          businessName: null,
        },
        {
          id: 8,
          name: "美式复古短袖T恤女纯棉夏ins潮半袖2023年新款设计感小众v领体桖",
          description:
            '<p><img src="http://localhost:9090/files/1699952270468-d1.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699952292732-d2.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699952296929-d3.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699952300817-d4.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699952304309-d5.png" style="max-width:100%;" contenteditable="false"/></p>',
          img: "http://localhost:9090/files/1699952256595-1.png",
          price: 59.0,
          unit: "件",
          count: 0,
          typeId: 16,
          businessId: 2,
          typeName: null,
          businessName: null,
        },
        {
          id: 9,
          name: "【赠送延长扣】MsShe大码外穿无钢圈防震内衣聚拢一体式运动文胸",
          description:
            '<p><img src="http://localhost:9090/files/1699952478199-d1.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699952482285-d2.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699952488092-d4.png" style="max-width:100%;" contenteditable="false"/></p>',
          img: "http://localhost:9090/files/1699952462164-1.png",
          price: 89.0,
          unit: "件",
          count: 0,
          typeId: 16,
          businessId: 2,
          typeName: null,
          businessName: null,
        },
        {
          id: 10,
          name: "MsShe大码女装新款冬季老钱风立领调节收腰菱格夹棉长款外套",
          description:
            '<p><img src="http://localhost:9090/files/1699952526794-1.png" style="max-width:100%;" contenteditable="false"/><img src="http://localhost:9090/files/1699952530411-2.png" style="max-width:100%;" contenteditable="false"/></p>',
          img: "http://localhost:9090/files/1699952515679-z1.png",
          price: 199.0,
          unit: "件",
          count: 0,
          typeId: 16,
          businessId: 2,
          typeName: null,
          businessName: null,
        },
      ],
      options: [
        {
          value: "外套",
          label: "外套",
          children: [
            {
              value: "所有外套",
              label: "所有外套",
            },
            {
              value: "运动外套",
              label: "运动外套",
            },
            {
              value: "防晒外套",
              label: "防晒外套",
            },
            {
              value: "保暖外套",
              label: "保暖外套",
            },
            {
              value: "摇粒绒",
              label: "摇粒绒",
            },
            {
              value: "棉服",
              label: "棉服",
            },
          ],
        },
        {
          value: "上装",
          label: "上装",
          children: [
            {
              value: "所有上装",
              label: "所有上装",
            },
            {
              value: "卫衣",
              label: "卫衣",
            },
            {
              value: "UT",
              label: "UT",
            },
            {
              value: "T恤",
              label: "T恤",
            },
            {
              value: "POLO",
              label: "POLO",
            },
            {
              value: "衬衫",
              label: "衬衫",
            },
            {
              value: "针织衫",
              label: "针织衫",
            },
          ],
        },
        {
          value: "裙装",
          label: "裙装",
          children: [
            {
              value: "运动裤",
              label: "运动裤",
            },
            {
              value: "牛仔裤",
              label: "牛仔裤",
            },
          ],
        },
        {
          value: "内衣",
          label: "内衣",
          children: [
            {
              value: "内衣",
              label: "内衣",
            },
            {
              value: "少女文胸",
              label: "少女文胸",
            },
            {
              value: "吊带",
              label: "吊带",
            },
            {
              value: "内裤",
              label: "内裤",
            },
            {
              value: "袜子",
              label: "袜子",
            },
          ],
        },
        {
          value: "起居系列",
          label: "起居系列",
          children: [
            {
              value: "所有起居系列",
              label: "所有起居系列",
            },
            {
              value: "起居裤",
              label: "起居裤",
            },
            {
              value: "起居套装",
              label: "起居套装",
            },
            {
              value: "内裤",
              label: "内裤",
            },
            {
              value: "袜子",
              label: "袜子",
            },
          ],
        },
        {
          value: "配件",
          label: "配件",
          children: [
            {
              value: "所有配件",
              label: "所有配件",
            },
            {
              value: "帽子·手套",
              label: "帽子·手套",
            },
          ],
        },
      ],
      // options: [],
      // value: [],
      list: [],
      loading: false,
      states: [
        "Alabama",
        "Alaska",
        "Arizona",
        "Arkansas",
        "California",
        "Colorado",
        "Connecticut",
        "Delaware",
        "Florida",
        "Georgia",
        "Hawaii",
        "Idaho",
        "Illinois",
        "Indiana",
        "Iowa",
        "Kansas",
        "Kentucky",
        "Louisiana",
        "Maine",
        "Maryland",
        "Massachusetts",
        "Michigan",
        "Minnesota",
        "Mississippi",
        "Missouri",
        "Montana",
        "Nebraska",
        "Nevada",
        "New Hampshire",
        "New Jersey",
        "New Mexico",
        "New York",
        "North Carolina",
        "North Dakota",
        "Ohio",
        "Oklahoma",
        "Oregon",
        "Pennsylvania",
        "Rhode Island",
        "South Carolina",
        "South Dakota",
        "Tennessee",
        "Texas",
        "Utah",
        "Vermont",
        "Virginia",
        "Washington",
        "West Virginia",
        "Wisconsin",
        "Wyoming",
      ],
    };
  },
  methods: {
    remoteMethod(query) {
      if (query !== "") {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          this.options = this.list.filter((item) => {
            return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1;
          });
        }, 200);
      } else {
        this.options = [];
      }
    },
    // 导航栏方法
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    handleLogin() {
      this.$router.push({ path: "/login" });
    },
    handleRegister() {
      this.$router.push({ path: "/register" });
    },
    handleCart() {
      this.$router.push({ path: "/front/cart" });
    },

  },
};
</script>

<style lang="scss">
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  /* background: #d3dce6; */
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

/* 导航栏菜单 */

/* 轮播图样式 */
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.front-header {
  display: flex;
  height: 60px;
  line-height: 60px;
  border-bottom: 1px solid #eee;
  background: white;
}

.front-header-left {
  width: 350px;
  display: flex;
  align-items: center;
  padding-left: 30px;
}
</style>
