<template>
  <div>
    <!-- 头部         导航栏菜单 -->
    <div class="front-header">
      <el-row :gutter="20" style="">
        <el-col :span="3"
          ><div class="grid-content bg-purple front-header-left">
            <a href="/"><img src="../../assets/boluo.png" />童装销售系统</a>
          </div></el-col
        >
        <el-col :span="8">
          <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"

          >
            <el-menu-item index="1">
              <a href="/">首页</a>
            </el-menu-item>

            <el-submenu index="2">
              <template slot="title">女装</template>
              <el-submenu
                v-for="item in girlOptions"
                :key="item.id"
                :index="'2'+item.id"
              >
                <template slot="title">{{ item.name }}</template>
                <el-menu-item
                  v-for="child in item.children"
                  :key="child.id"
                  :index="'2'+child.id"
                  @click="handleSubMenuClick(child.id)"
                >
                  {{ child.name }}
                </el-menu-item>
              </el-submenu>
              <!--

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

              -->
              </el-submenu>


            <el-submenu index="3">
              <template slot="title">男装</template>
              <el-submenu
                v-for="item in boyOptions"
                :key="item.id"
                :index="'3'+item.id"
              >
                <template slot="title">{{ item.name }}</template>
                <el-menu-item
                  v-for="child in item.children"
                  :key="child.id"
                  :index="'3'+child.id"
                  @click="handleSubMenuClick(child.id)"
                >
                  {{ child.name }}
                </el-menu-item>
              </el-submenu>
              <!--
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
             -->
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
            <el-input
              style="width: 150px; margin-right: 10px"
              placeholder="请输入商品名称"
              v-model="searchValue"
            ></el-input>
            <el-button type="primary" icon="el-icon-search" @click="handleClick"
              >搜索</el-button
            >
          </div></el-col
        >
        <!-- 登陆注册按钮 -->
        <el-col :span="2" v-if="!user || Object.keys(user).length === 0">
          <el-button
            :loading="loading"
            type="primary"
            style="width: 75%; margin-bottom: 30px"
            @click="navTo('/login')"
            >登 录</el-button
          >
        </el-col>
        <el-col :span="2" v-if="!user || Object.keys(user).length === 0">
          <el-button
            :loading="loading"
            type="primary"
            style="width: 75%; margin-bottom: 30px"
            @click="navTo('/register')"
            >注 册</el-button
          >
        </el-col>
        <!-- 用户头像 -->
        <el-col :span="4" v-else class="navbar">
          <el-dropdown>
            <div class="front-header-dropdown">
              <img
                v-if="user.roles[0] === 'user'"
                @click="navTo('/person')"
                :src="user.avatar"
                alt=""
              />
              <img
                v-else
                @click="navTo('/dashboard')"
                :src="user.avatar"
                alt=""
              />
              <div style="margin-left: 5px">
                <span>{{ user.name }}</span
                ><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
              </div>
            </div>
            <el-dropdown-menu slot="dropdown">
              <!-- <el-dropdown-item>
                <div style="text-decoration: none" @click="navTo('/cart')">我的购物车</div>
              </el-dropdown-item> -->
              <el-dropdown-item>
                <div style="text-decoration: none" @click="navTo('/collect')">
                  我的收藏
                </div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none" @click="navTo('/address')">
                  我的地址
                </div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none" @click="navTo('/orders')">
                  我的订单
                </div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none" @click="logout">退出</div>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-col>

        <el-col :span="3"
          ><div class="grid-content bg-purple">
            <el-button
              type="primary"
              icon="el-icon-s-goods"
              style="width: 120%; margin-bottom: 30px"
              @click="navTo('/cart')"
              >我的购物车</el-button
            >
          </div></el-col
        >
      </el-row>
    </div>
  </div>
</template>

<script>
import utilsApi from "@/api/utilsApi";
import goodsApi from "@/api/goods"
export default {
  name: "Header",
  data() {
    return {
      // 导航栏菜单数据
      activeIndex: "1",
      // activeIndex2: "1",
      user: JSON.parse(localStorage.getItem("user") || "{}"),
      searchValue: "",
      girlOptions: [],
      boyOptions: [],
      list: [],
      loading: false,
      goodsData:[]
    };
  },
  mounted() {
    this.loadGirls();
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
    handleSubMenuClick(id){
      console.log(id);
      // this.$router.push({ path: "/type" ,query:{id:id} });
      this.$router.push({ path: "/type", query: { id: id } });
    },
    // 导航栏方法
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
      // this.$router.push({ path: "/type" ,query:{keyPath:keyPath} });

    },
    handleClick() {
      if(this.$route.name === "Search"){
        this.loadGoods(this.searchValue);
      }else{
        let name = this.searchValue ? this.searchValue : "";
        this.$router.push({ path: "/search", query: { name: name } });
      }
    },
    async loadGoods(name) {
      const res = await goodsApi.selectByName(name)
      console.log(res)
      if (res.code === 20000) {
        this.goodsData = res.data || []
        this.$bus.$emit('goodsData', this.goodsData);
      } else {
        this.$message.error(res.msg)
      }
    },
    async logout() {
      // 将role移除本地缓存
      localStorage.removeItem("user");
      await this.$store.dispatch("user/logout");
      await this.$store.dispatch("tagsView/delAllViews");
      sessionStorage.removeItem("tabViews");
      location.reload();
    },
    navTo(path) {
      this.$router.push(path);
    },
    async loadGirls() {
      const res = await utilsApi.selectAll("/type/getAllType", {
        description: "女",
      });
      this.girlOptions= res.data;
      const res2 = await utilsApi.selectAll("/type/getAllType", {
        description: "男",
      });
      this.boyOptions = res2.data;
    },
  },
};
</script>

<style lang="scss">
@import "../../assets/css/front.css";
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
/** 用户头像样式 */
.front-header-dropdown {
  display: flex;
  align-items: center;
  justify-content: right;
}
.el-dropdown-menu {
  width: 100px !important;
  text-align: center !important;
}
</style>
