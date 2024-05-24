<template>
  <div>
    <Header></Header>
    <div class="main-content">
    <div style="display: flex; width: 70%; background-color: white; margin: 30px auto; border-radius: 20px">
      <div style="flex: 1; padding: 0 20px">
        <div style="font-size: 18px; color: #000000FF; line-height: 80px; border-bottom: #cccccc 1px solid">{{typeData.name}}  {{ typeData.children[0].name }}</div>
        <div style="margin: 20px 0">
          <el-row :gutter="20">
            <el-col :span="6" style="margin-bottom: 20px" v-for="item in goodsData" :key="item.id">
              <img @click="navTo(item.id)" :src="item.img" alt="" style="width: 100%; height: 175px; border-radius: 10px; border: #cccccc 1px solid">
              <div style="margin-top: 10px; font-weight: 500; font-size: 16px; width: 180px; color: #000000FF; text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">{{item.name}}</div>
              <div style="margin-top: 5px; font-size: 20px; color: #FF5000FF">￥ {{item.price}} / {{item.unit}}</div>
            </el-col>
          </el-row>
        </div>
      </div>
      <div style="width: 250px; padding: 0 20px; border-left: #cccccc 1px solid">
        <div style="font-size: 18px; color: #000000FF; line-height: 80px; border-bottom: #cccccc 1px solid">猜你喜欢</div>
        <div style="margin: 20px 0; padding: 0 10px">
          <div style="margin-bottom: 20px" v-for="item in recommendData" :key="item.id">
            <img @click="navTo(item.id)" :src="item.img" alt="" style="width: 100%; height: 175px; border-radius: 10px; border: #cccccc 1px solid">
            <div style="margin-top: 10px; font-weight: 500; font-size: 16px; width: 180px; color: #000000FF; text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">{{item.name}}</div>
            <div style="margin-top: 5px; font-size: 20px; color: #FF5000FF">￥ {{ item.price }} / {{item.unit}}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
  </div>

</template>

<script>
import utilsApi from '@/api/utilsApi'
export default {

  data() {
    let typeId = this.$route.query.id
    return {
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      typeId: typeId,
      goodsData: [],
      recommendData: [],
      typeData: {}
    }
  },
  mounted() {
    this.loadGoods()
    this.loadType()
    this.loadRecommend()
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    loadRecommend() {
      // this.$request.get('/goods/recommend').then(res => {
      //   if (res.code === '200') {
      //     this.recommendData = res.data
      //   } else {
      //     this.$message.error(res.msg)
      //   }
      // })
    },
    async loadType() {
      const res = await utilsApi.selectPage("/type/selectById","get",{id:this.typeId})
    console.log(res)
      if (res.code === 20000) {
        this.typeData = res.data
      } else {
        this.$message.error(res.msg)
      }

    },
    async loadGoods() {
      const res =await utilsApi.selectPage("/goods/selectByTypeId","get",{id:this.typeId})
      if (res.code === 20000) {
        this.goodsData = res.data
      } else {
        this.$message.error(res.msg)
      }
    },
    navTo(goodsId) {
      console.log(goodsId)
      this.$router.push({ path: '/detail', query: { id: goodsId } });
    }
  }
}
</script>
