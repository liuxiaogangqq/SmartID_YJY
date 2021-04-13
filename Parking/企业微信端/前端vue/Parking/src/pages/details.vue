<template>
  <div class="appointmentDetails">
    <div class="content">
      <div class="detailStatus yyStatus">
          <!-- <p class="statusTips">预约状态</p> -->

          <div v-if="visitorState==1" class="statusText statusText1">待入场</div>
          <div v-else-if="visitorState==3" class="statusText statusText3">已取消</div>
          <div v-else-if="visitorState==4" class="statusText statusText4">已入场</div>
          <div v-else-if="visitorState==5" class="statusText statusText5">已支付</div>
          <div v-else-if="visitorState==6" class="statusText statusText3">已完成</div>
          <div v-else-if="visitorState==7" class="statusText statusText4">已出场</div>
          <div v-else class="statusText statusText2">已逾期</div>
          <!-- <p class="statusLine"></p> -->
      </div>
      <mt-cell v-if="visType==1" title="单位" v-model="detailsCompany"></mt-cell>
      <mt-cell v-if="visType==1" title="姓名" v-model="detailsVisName"></mt-cell>
      <mt-cell v-if="visType==2" title="姓名" v-model="detailsUserName"></mt-cell>
      <mt-cell title="车牌号码" v-model="detailsCarNumber"></mt-cell>
      <mt-cell title="预约开始时间" v-model="detailsAppointStartTime"></mt-cell>
      <mt-cell title="预约结束时间" v-model="detailsAppointEndTime"></mt-cell>
      <mt-cell v-if="visitorState!=8" title="车辆入场时间" v-model="detailsInTime"></mt-cell>
      <mt-cell v-if="visitorState!=8" title="车辆出场时间" v-model="detailsOutTime"></mt-cell>
      <mt-cell title="缴费金额(元)" v-model="detailsPrice"></mt-cell>
      <mt-cell title="预约提交时间" v-model="detailsSubmitTime"></mt-cell>
      <div class="detailStatus" style="margin-bottom:0;">
        <div v-if="visType==1" class="statusTips" style="font-size:12px;text-align:left;margin-bottom:0;">
          温馨提示：请于<span style="font-weight:bold;">{{tipsTime}}</span>至<span style="font-weight:bold;">{{tipsCompanyTime}}</span>时间段内进入院内，{{detailsAppointStartTime}}前可免费取消预约，超过<span style="font-weight:bold;">{{tipsCompanyTime}}</span>将自动变更为预约逾期状态，车辆将不能入场并自动扣除<span style="font-weight:bold;">2小时</span>的停车费用。<br>预约时间内，车辆可以多次进出。车辆出场后可选择提前结束此预约。
        </div>
        <div v-if="visType==2" class="statusTips" style="font-size:12px;text-align:left;margin-bottom:0;">
          温馨提示：请于<span style="font-weight:bold;">{{tipsTime}}</span>至<span style="font-weight:bold;">{{tipsStaffTime}}</span>时间段内进入院内，{{detailsAppointStartTime}}前可免费取消预约，超过<span style="font-weight:bold;">{{tipsStaffTime}}</span>将自动变更为预约逾期状态，车辆将不能入场且需要支付<span style="font-weight:bold;">30分钟</span>的停车费用。
        </div>
        
      </div>
      <div class="btnGroup flexBetween" v-show="visitorState ==1 || visitorState ==2  || visitorState ==4 || visitorState ==7 || visitorState > 7">
          <mt-button class="cancelBtn cancelAppointBtn" v-if="visitorState==1" @click="staffCancelAppoint">取消预约</mt-button>
          <mt-button class="cancelBtn cancelAppointBtn" v-if="visType==1&&visitorState==7" @click="advanceOver">提前结束</mt-button>
          <mt-button class="cancelBtn cancelAppointBtn" v-if="(visType==2&&visitorState==4) || (visType==2&&visitorState>7)" @click="toPlay">去支付</mt-button>
      </div>
    </div>

    <!-- 选择时间弹窗 -->
    <mt-datetime-picker style="width:100%;" ref="pickerStart" type="datetime" :startDate='startValue' :endDate='endValue' v-model="startValue" @confirm="handleStart"></mt-datetime-picker>
    <mt-datetime-picker style="width:100%;" ref="pickerEnd" type="datetime" :startDate='startValue' v-model="startValue" @confirm="handleEnd"></mt-datetime-picker>
    
    <!-- 预约弹窗 -->
    <mt-popup
      class="popupAppointVisible"
      style="width:80%"
      v-model="appointmentPopup"
      modal="true"
      closeOnClickModal="true"
      popup-transition="popup-fade">
      <div class="popupContent">
          <div class="popupIcon"><img :src="imgUrl" alt=""></div>
          <p class="popupTxt">{{popupTxt}}</p>
          <p class="carPrice" v-if="appointCarPrice==0">确认缴费￥{{carPrice}}元</p>
          <div class="btn-group">
            <mt-button class="cancalBtn" @click="cancalBtn">取消</mt-button>
            <mt-button v-if="appointCarPrice==1" class="stationBtn" @click="sureBtn">确认</mt-button>
            <mt-button v-if="appointCarPrice==0" class="stationBtn" @click="surePayBtn" :disabled="payBtnDis">确认</mt-button>
            <mt-button v-if="appointCarPrice==2" class="stationBtn" @click="sureOverBtn">确认</mt-button>
          </div>
      </div>
      
    </mt-popup>
  </div>
</template>
<script>
  import { Toast } from 'mint-ui'
  import {formatDate} from '../components/date.js'
  import img_url from '../assets/image/iconNo.png'
  import img_url1 from '../assets/image/iconYes.png'
  import img_url2 from '../assets/image/iconGan.png'
  import img_url3 from '../assets/image/iconWen.png'
  import wx from "weixin-js-sdk";
  export default {
    inject: ['reload'],
    filters: {
        formatDate(time) {
            var date = new Date(time).toLocaleString();
            return formatDate(date,'yyyy-MM-dd hh:mm:ss');
        }
    },
    data() {
      return {
        visitorState:1,
        detailsCompany: '',
        detailsVisName: '',
        detailsUserName: '',
        detailsCarNumber: '',
        detailsAppointStartTime: '',
        detailsAppointEndTime: '',
        detailsInTime: '',
        detailsOutTime: '',
        detailsPrice: '',
        detailsSubmitTime: '',
        startDate: formatDate(new Date(),'yyyy-MM-dd hh:mm'),
        endDate: '',
        startValue: new Date(),
        endValue: new Date(),
        msg: 'nikan',
        visType: 2,
        rustList: '',
        carPrice: '',
        appointmentPopup: false,
        payBtnDis: false,
        popupTxt: '',
        imgUrl: img_url,
        appointCarPrice: 1,
        openId: '',

        appId: '', // 公众号名称，由商户传入
        timeStamp: '', // 时间戳，自1970年以来的秒数
        nonceStr: '', // 随机串
        package: '',
        signType: '', // 微信签名方式：
        paySign: '',
        signature: '',
        nonceStrConfig: '',
        timeStampConfig: '',

        tipsTime: '',
        tipsCompanyTime: '',
        tipsStaffTime: '',

        locationUrl: ''
      } 
    },
    created(){
      if(this.$route.query.iswhere=="details"){
        this.$route.meta.title='预约详情'
        window.document.title = '预约详情'
      }
      this.getParams()
      this.getDetails()
    },
    methods: {
      // 取到路由带过来的参数
      getParams() {
        var that = this;

        that.locationUrl = location.href.split('#')[0]
        // alert(that.locationUrl)
        that.visType = that.$route.query.visType
        if( this.visType == 1 ){
          this.rustList = JSON.parse(localStorage.getItem('dataVistorInfo'))
        }else{
          this.rustList = JSON.parse(localStorage.getItem('dataVistorInfo1'))
        }
      },
      // 获取预约详情
      getDetails() {
          var that = this
          that.visitorState = that.rustList.visitorState
          that.detailsCompany = that.rustList.visitorCompany
          that.detailsVisName = that.rustList.visitorName
          that.detailsUserName = that.rustList.userName
          that.detailsCarNumber = that.rustList.visitorCarCode
          that.detailsAppointStartTime = that.rustList.visitorBeginTime
          that.detailsAppointEndTime = that.rustList.visitorEndTime
          that.detailsSubmitTime = that.rustList.recordTime

          var detailsStartTime = that.detailsAppointStartTime.replace(/-/g, '/')

          var time1 = new Date(detailsStartTime).getTime()-7200000 // 预约开始时间前2小时
          var time2 = new Date(detailsStartTime).getTime()+7200000 // 预约开始时间后2小时
          var time3 = new Date(detailsStartTime).getTime()+1800000 // 预约开始时间后半小时
          
          // 以下对时间的处理是因为在ios中时间显示只认“/”，不认“-”、“年月日”
          var tipsTime = formatDate(new Date(time1),'yyyy/MM/dd hh:mm')
          var tipsTime1 = tipsTime.split(" ")
          var tipsTime2 = tipsTime1[0].split("/")
          that.tipsTime = tipsTime2[1] +'月'+ tipsTime2[2] +'日'+ tipsTime1[1]

          var tipsCompanyTime = formatDate(new Date(time2),'yyyy/MM/dd hh:mm')
          var tipsCompanyTime1 = tipsCompanyTime.split(" ")
          var tipsCompanyTime2 = tipsCompanyTime1[0].split("/")
          that.tipsCompanyTime = tipsCompanyTime2[1] +'月'+ tipsCompanyTime2[2] +'日'+ tipsCompanyTime1[1]

          var tipsStaffTime = formatDate(new Date(time3),'yyyy/MM/dd hh:mm')
          var tipsStaffTime1 = tipsStaffTime.split(" ")
          var tipsStaffTime2 = tipsStaffTime1[0].split("/")
          that.tipsStaffTime = tipsStaffTime2[1] +'月'+ tipsStaffTime2[2] +'日'+ tipsStaffTime1[1]

          that.$axios.post("getVistorDetails",{
            VisRecordInnerId: that.$route.query.VisRecordInnerId,
            requestType: 1
          })
          .then(response => {
            if( response.data.status == 200 ){
              if( that.visitorState == 8 ){ //判断状态为已逾期时，入场时间出场时间都为空；否则赋值
                that.detailsInTime = null
                that.detailsOutTime = null
              }else{
                that.detailsInTime = response.data.result.inTime
                that.detailsOutTime = response.data.result.outTime
              }
              that.detailsPrice = response.data.result.money/100
            }else{
              let instance = Toast({
                message: response.data.message,
                position: 'bottom',
                duration: 5000
              });
              setTimeout(() => {
                instance.close();
              }, 2000);
            }
          })
          .catch(error => {
            console.log(error);
          });
      },
      dateClickStart() {
          this.$refs.pickerStart.open();
      },
      dateClickEnd() {
          this.$refs.pickerEnd.open();
      },
      handleStart(value) {
          this.startDate = formatDate(value,'yyyy-MM-dd hh:mm')
          this.startValue = this.startDate
      },
      handleEnd(value) {
          this.endDate = formatDate(value,'yyyy-MM-dd hh:mm')
          this.endValue = this.endDate
      },
      // 取消预约
      staffCancelAppoint(){
        var that = this
        that.imgUrl = img_url3
        that.popupTxt = '是否取消预约'
        that.appointmentPopup = true
      },
      //取消
      cancalBtn(){
        var that = this;
          that.appointmentPopup =false
      },
      // 弹窗中的确定取消按钮
      sureBtn(){
        var that = this
        // 当是取消预约时,无论个人预约还是公司预约,都有两种状态：1.小于预约开始时间不扣费,直接取消。2.大于预约开始时间，状态切换为预期，走扣费流程
        // if( that.visitorState < 1 ){
        //   that.appointmentPopup = false
        //   that.visitorState = 3
        // }else{
        //   that.appointmentPopup = false
        // }
        that.$axios.post("updateVistorDetailsCancel",{
          RecordInnerId: that.$route.query.VisRecordInnerId,
          requestType: 1
        })
        .then(response => {
          if( response.data.status == 200 ){
            let ustate = localStorage.getItem('ustate')
            if( ustate == 3 ){
              that.$router.push('/index')
              let instance = Toast({
                message: response.data.message,
                position: 'bottom',
                duration: 5000
              });
              setTimeout(() => {
                instance.close();
              }, 2000);
            }else{
              that.$router.push('/staff')
              let instance = Toast({
                message: response.data.message,
                position: 'bottom',
                duration: 5000
              });
              setTimeout(() => {
                instance.close();
              }, 2000);
            }
          }else{
            let instance = Toast({
              message: response.data.message,
              position: 'bottom',
              duration: 5000
            });
            setTimeout(() => {
              instance.close();
            }, 2000);
          }
          that.appointmentPopup = false
        })
        .catch(error => {
          console.log(error);
        });
      },
      // 提前结束
      advanceOver(){
        var that = this
        that.imgUrl = img_url3
        that.popupTxt = '确定提前结束此预约吗？'
        that.appointmentPopup = true
        that.appointCarPrice = 2
      },
      // 弹窗中的确定提前结束按钮
      sureOverBtn(){
        var that = this
        that.$axios.post("updateVistorDetailsOver",{
          RecordInnerId: that.$route.query.VisRecordInnerId,
          requestType: 1
        })
        .then(response => {
          if( response.data.status == 200 ){
            let ustate = localStorage.getItem('ustate')
            if( ustate == 3 ){
              that.$router.push('/index')
              let instance = Toast({
                message: response.data.message,
                position: 'bottom',
                duration: 5000
              });
              setTimeout(() => {
                instance.close();
              }, 2000);
            }else{
              that.$router.push('/staff')
              let instance = Toast({
                message: response.data.message,
                position: 'bottom',
                duration: 5000
              });
              setTimeout(() => {
                instance.close();
              }, 2000);
            }
          }else{
            let instance = Toast({
              message: response.data.message,
              position: 'bottom',
              duration: 5000
            });
            setTimeout(() => {
              instance.close();
            }, 2000);
          }
        })
        .catch(error => {
          console.log(error);
        });
      },
      // 个人预约从“已入场”开始显示“去支付”
      toPlay(){
        var that = this
        that.imgUrl = img_url3
        that.popupTxt = '确定结束此预约吗？'
        that.appointmentPopup = true
        that.appointCarPrice = 0
        // that.carPrice = that.detailsPrice
        that.$axios.post("getMoneyFee",{
          RecordInnerId: that.$route.query.VisRecordInnerId,
          requestType: 1
        })
          .then(response => {
            if( response.data.status == 200 ){
              that.detailsPrice = response.data.result.totalFee/100
              that.carPrice = that.detailsPrice
            }else{
              let instance = Toast({
                message: response.data.message,
                position: 'bottom',
                duration: 5000
              });
              setTimeout(() => {
                instance.close();
              }, 2000);
            }
          })
          .catch(error => {
            console.log(error);
          });
      },
      surePayBtn(){
        var that = this
        that.payBtnDis = true
        that.$axios.post("getVistorOpenId",{
          pageUrl: that.locationUrl,
          RecordInnerId: that.$route.query.VisRecordInnerId,
          requestType: 1
        })
          .then(response => {
            if( response.data.status == 200 ){
              that.openId = response.data.result.openid

              that.appId = response.data.result.signInfo.appId
              that.nonceStrConfig = response.data.result.signInfo.nonceStr
              that.signature = response.data.result.signInfo.signature
              that.timeStampConfig = response.data.result.signInfo.timestamp
              that.detailsPrice = response.data.result.totalFee/100
              wx.config({
                  beta: true,// 必须这么写，否则wx.invoke调用形式的jsapi会有问题
                  debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来
                  appId: that.appId, // 必填，企业微信的corpID
                  timestamp: that.timeStampConfig, // 必填，生成签名的时间戳
                  nonceStr: that.nonceStrConfig, // 必填，生成签名的随机串
                  signature: that.signature,// 必填，签名
                  jsApiList: ['getBrandWCPayRequest']
              });
              that.$axios.post("createOrder",{
                tradeType: response.data.result.tradeType,
                totalFee: response.data.result.totalFee,
                outTradeNo: response.data.result.outTradeNo,
                spbillCreateIp: response.data.result.spbillCreateIp,
                notifyUrl: response.data.result.notifyUrl,
                body: response.data.result.body,
                // attach: that.$route.query.VisRecordInnerId,
                openid: that.openId,
                requestType: 1
              })
                .then(response => {
                    that.timeStamp = response.data.timeStamp
                    that.nonceStr = response.data.nonceStr
                    that.package = response.data.packageValue
                    that.signType = response.data.signType
                    that.paySign = response.data.paySign

                    if (typeof WeixinJSBridge == "undefined"){
                      if( document.addEventListener ){
                          document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
                      }else if (document.attachEvent){
                          document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
                          document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
                      }
                    }else{
                      WeixinJSBridge.invoke(
                        'getBrandWCPayRequest', {
                          'appId': that.appId, // 公众号名称，由商户传入
                          'timeStamp': that.timeStamp, // 时间戳，自1970年以来的秒数
                          'nonceStr': that.nonceStr, // 随机串
                          'package': that.package,
                          'signType': that.signType, // 微信签名方式：
                          'paySign': that.paySign, // 微信签名
                          'attach': that.$route.query.VisRecordInnerId
                        },
                        function (res) {
                          if (res.err_msg === 'get_brand_wcpay_request:ok') {
                            //alert(res.err_msg)// 将在用户支付成功后返回ok，但并不保证它绝对可靠。
                            that.appointmentPopup = false
                            that.payBtnDis = false
                            let instance = Toast({
                              message: '支付成功',
                              position: 'bottom',
                              duration: 5000
                            });
                            setTimeout(() => {
                              instance.close();
                              let ustate = localStorage.getItem('ustate')
                              if( ustate == 3 ){
                                that.$router.push('/index')
                              }else{
                                that.$router.push('/staff')
                              }
                            }, 2000);
                            // that.reload()
                          }
                        }
                      )
                    }
                })
                .catch(error => {
                  console.log(error);
                });
            }else{
              let instance = Toast({
                message: response.data.message,
                position: 'bottom',
                duration: 5000
              });
              setTimeout(() => {
                instance.close();
              }, 2000);
            }
          })
          .catch(error => {
            console.log(error);
          });
      },
    }
  }
</script>
<style scoped>
.appointmentDetails{
  background-image: url('./../assets/image/caryy-bg.png');
  background-position: top center;
  background-repeat: no-repeat;
  background-size: contain;
  padding:80px 20px 100px;
  overflow: auto;
  height: 100vh;
  box-sizing: border-box;
}
  .content {
    padding: 0 10px 20px;
    color: #242323;
    background-color: #fff;
    border-radius: 8px;
    position: relative;
  }
.mint-cell-value{color: #333333;}
.btnGroup{background-color: #fff; padding: 10px 20px; height: 80px; box-sizing: border-box; box-shadow: 0 0 20px rgba(0,0,0,.1);}
.cancelAppointBtn{color: #fff; background-color: #931D36; height: 60px; border-radius: 6px;}
.mint-cell{border-top: 1px solid #EFEFF3; min-height: 44px;}
.yyStatus + .mint-cell{margin-top: -40px; border-top: none;}
.yyStatus{box-sizing:border-box; padding: 0; position: relative; background-color: #fff;padding: 4px; width:90px; height: 90px; top: -40px; border-radius: 100%; margin: 0 auto;  overflow: hidden; }
.yyStatus .statusText{border:1px solid #fff;border-radius: 100%; margin-bottom: 0; box-sizing: border-box; height: 100%; width: 100%; line-height: 100%; line-height: 80px;}
.yyStatus .statusText1{background-color: #FCE5D8; border-color:#E2B49C; color: #C9764D; }
.yyStatus .statusText2{background-color: #FCE0E0; border-color:#FAD4D4; color: #C74747;}
.yyStatus .statusText3{background-color: #F4F4F4; border-color:#E8E8E8; color: #999999;}
.yyStatus .statusText4{background-color: #E3F0FC; border-color:#C0D6EB; color: #2C73B3;}
.yyStatus .statusText5{background-color: #D2F1EB; border-color:#A8DFCB; color: #30BB89;}
.popupAppointVisible{padding: 35px 0 0;}
.popupIcon{ width: 60px; height: 60px; position: relative; margin: 0 auto; left: 0; top:0}
.popupIcon img{max-width: 100%; max-height: 100%;}
.popupTxt{margin-top: 10px; line-height: 2; font-size: 18px; color: #333333;}
.btn-group{display: flex; position: relative; margin-top:15px; padding:10px 20px}
.btn-group::before{position: absolute; left: 0; right: 0; top: 0; height: 1px; background: #EFEFF3; content: '';}
.btn-group button{flex: 1; margin-top: 0;}
.btn-group button.cancalBtn{background-color: transparent; border-color: transparent; color: #808080; box-shadow: none;}
.btn-group button.stationBtn{color: #931D36;}
</style>
