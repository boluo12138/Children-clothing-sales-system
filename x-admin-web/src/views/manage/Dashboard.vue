<template>
  <div>
    <div class="card" style="padding: 15px">
      您好，{{ user.username }}！欢迎使用本系统
    </div>

    <div style="display: flex; margin: 10px 0">
      <div style="width: 50%;" class="card">
        <div style="margin-bottom: 30px; font-size: 20px; font-weight: bold">公告列表</div>
        <div >
          <el-timeline  reverse slot="reference">
            <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.time">
              <el-popover
                  placement="right"
                  width="200"
                  trigger="hover"
                  :content="item.content">
                <span slot="reference">{{ item.title }}</span>
              </el-popover>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import noticeApi from '@/api/notice';
export default {
  name: 'Home',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      notices: []
    }
  },
  created() {
    noticeApi.selectAll().then(res =>{
      this.notices = res.data || []
    })
    // this.$request.get('/notice/selectAll').then(res => {
    //   this.notices = res.data || []
    // })
  }
}
</script>
