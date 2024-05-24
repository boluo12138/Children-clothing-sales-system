<template>
  <div>
      <el-button type="primary" @click="pay">支付</el-button>
      <el-dialog
              title="收银台"
              :visible.sync="dialogVisible"
              width="30%">
          <div style="text-align: center">
              <p>微信支付{{payResult.price}}元</p>
              <div style="border: 10px solid grey;width:160px;padding:10px;margin:0px auto">
                  <vue-qr
                      :text="payResult.codeUrl"
                       colorLight="#fff"
                      :size="160"
                       margin="0"
                      :logo-src="require('../../assets/bg.jpg')"
                  >
                  </vue-qr>
              </div>
              <el-divider></el-divider>
              <div style="font-size: 13px">
                  提示:<br>
                  支付成功前请勿手动关闭页面<br>
                  二维码两个小时有效，请及时扫码支付<br>
              </div>
          </div>
      </el-dialog>
  </div>

</template>

<script>
  import vueQr from 'vue-qr'

  export default {
      components:{
          vueQr
      },
      name: "WeChatPay",
      data(){
          return{
              orderNo:"e334ce2a6b1c4bc6018",
              payResult:{
                  codeUrl:"",
                  price:0,
                  orderNo:'',
              },
              dialogVisible:false,
              timer:"",
              flag:0,

          }
      },

      methods:{
          queryPayStatus(orderNo){
            this.$http.post("/order/queryPayStatus/"+orderNo).then(result=>{
                if(result.data.code===2000){
                    clearInterval(this.timer);
                    this.timer=null;
                    this.$message.success("支付成功");
                    this.dialogVisible = false;
                }
              })
          },
          pay(){
              this.dialogVisible = true;
              /*创建订单*/
              this.$http.post("order/createQRCode/"+this.orderNo).then(result=>{
                  if(result.data.code===2000){
                      this.payResult = result.data.data;
                      /*每隔三秒查询订单的状态 是否支付*/
                      this.timer = setInterval(()=>{
                          this.queryPayStatus(this.payResult.orderNo)
                      },3000);
                  }
            })
          },
      }
  }
</script>

<style scoped>

</style>
